package com.egg.consultant.config;

import com.egg.consultant.ai.service.ExtractService;
import com.egg.consultant.information.mapper.AdmissionMapper;
import com.egg.consultant.information.pojo.*;
import com.egg.consultant.information.service.AdmissionService;
import com.egg.consultant.information.service.MyDocumentService;
import com.egg.consultant.information.service.SchoolService;
import com.egg.consultant.information.utils.AdmissionGenerator;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.loader.ClassPathDocumentLoader;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:EGG
 * @Description:
 * @Data Created in 2026-10-28-19:23
 * @Modified By:
 */
@Slf4j
@Configuration
public class AdmissionExtractConfig
{
    @Autowired
    private MyDocumentService myDocumentService;
    @Autowired
    private ExtractService extractService;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private AdmissionService admissionService;
    @Autowired
    private AdmissionTransactionalService admissionTransactionalService;

    private static final DocumentParser PDF_PARSER = new ApachePdfBoxDocumentParser();
    private static final DocumentParser TIKA_PARSER = new ApacheTikaDocumentParser();

    //admissionExtract的返回值为void无法注册为Bean，需要包装
    @Bean
    public ApplicationRunner autoStartExtract()
    {
        return args ->
        {
            try
            {
                admissionExtract();
            } catch (Exception e)
            {
                log.error("录取数据自动建模功能启动失败", e);
            }
        };
    }

    public void admissionExtract()
    {
        //获取类路径 file:/G:/files/works/LangChain4j/consultantPlus/target/classes/content
        URL url = getClass().getClassLoader().getResource("content/admissionDoc");
        if (url == null)
        {
            log.error("找不到 admissionDoc 目录，跳过自动建模");
            return;
        }
        //转成字符串并截取 G:/files/works/LangChain4j/consultantPlus/target/classes/content
        String path = url.toString();
        path = path.substring(6);
        //获取指定目录下的所有文件
        File[] files = new File(path).listFiles();
        if (files == null || files.length == 0)
        {
            log.error("目录不存在或为空:{}", path);
            return;
        }
        //遍历一份文件
        for (File file : files)
        {
            String schoolName = StringUtils.stripFilenameExtension(file.getName());
            try
            {
                //检查这份文档是否已经抽取过
                MyDocument myDocument = myDocumentService.selectByName(file.getName());
                if (myDocument == null || !"SUCCESS".equals(myDocument.getStatus()))
                {
                    try
                    {
                        //调用方法抽取内容，得到一份文档中所有的admission
                        List<Admission> admissions = singleFileExtract(file.getName());
                        if (admissions.isEmpty())
                        {
                            log.warn("文档 {} 未抽取到任何录取记录，请人工检查提示词或文档内容", file.getName());
                            continue;
                        }
                        admissionTransactionalService.admissionPersistent(file.getName(), admissions);
                    } catch (Exception e)
                    {
                        log.error("文档 {} 建模失败，已跳过", file.getName(), e);
                    }
                } else
                {
                    continue;
                }
            } catch (Exception e)
            {
                log.error("文档 {} 建模失败，已跳过；下次启动会再次尝试", file.getName(), e);
            }
        }
    }


    //抽取单条文档中的录取信息
    private List<Admission> singleFileExtract(String fileName)
    {
        //拼出单份文档的类路径
        String docPath = "content/admissionDoc/" + fileName;
        //获取文档对象
        Document document;
        //用对应的解析器解析文档对象
        if (fileName.toLowerCase().endsWith(".pdf"))
        {
            document = ClassPathDocumentLoader.loadDocument(docPath, PDF_PARSER);
        } else
        {
            document = ClassPathDocumentLoader.loadDocument(docPath, TIKA_PARSER);
        }
        //将文档分块，得到文档字块链表
        List<TextSegment> segments = DocumentSplitters.recursive(2000, 0).split(document);

        //遍历字块链表，逐块发送大模型请求，整理admissionList的信息，加入到链表中
        //获取去除后缀名的文件名，作为填充学校名的备用选择
        String lastSchool = StringUtils.stripFilenameExtension(fileName);
        Integer lastYear = null;
        String lastProvince = null;
        List<AdmissionList> allAdmissions = new ArrayList<>();
        for (TextSegment segment : segments)
        {
            AdmissionList admissionList = extractService.admissionExtract(segment.text());
            //如果这段文本中没有录取信息，则admissionList为空，continue
            if (admissionList == null)
            {
                continue;
            }

            //如果这段文本中没有学校名，则从使用文档名中的学校名来填充
            if (admissionList.getSchool() == null)
            {
                admissionList.setSchool(lastSchool);
            }
            //如果这段文本中没有年份，省份信息，则使用上一个字块的信息
            if (admissionList.getYear() == null)
            {
                admissionList.setYear(lastYear);
            } else
            {
                lastYear = admissionList.getYear();
            }
            if (admissionList.getProvince() == null)
            {
                admissionList.setProvince(lastProvince);
            } else
            {
                lastProvince = admissionList.getProvince();
            }

            //把admissionList加入链表
            allAdmissions.add(admissionList);
        }

        //遍历AdmissionList链表，调用工具类，将单个admissionList转化成List<Admission>，将所有admission全部集合到单个链表中
        List<Admission> admissions = new ArrayList<>();
        for (AdmissionList admissionList : allAdmissions)
        {
            List<Admission> admission = AdmissionGenerator.generateList(admissionList, fileName);
            admissions.addAll(admission);
        }
        return admissions;
    }
}
