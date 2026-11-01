package com.egg.consultant;

import com.egg.consultant.ai.service.ExtractService;
import com.egg.consultant.information.pojo.Admission;
import com.egg.consultant.information.pojo.AdmissionList;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.loader.ClassPathDocumentLoader;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import org.bouncycastle.oer.its.etsi102941.Url;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.text.Segment;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:EGG
 * @Description:
 * @Data Created in 2026-10-28-20:08
 * @Modified By:
 */
@SpringBootTest
public class ExtractTest
{
    @Autowired
    ExtractService extractService;

    @Test
    void PDFExtractTest()
    {
        //List<Document> documents = ClassPathDocumentLoader.loadDocuments("content", new ApachePdfBoxDocumentParser());

        List<Document> documents = ClassPathDocumentLoader.loadDocuments("content", new ApachePdfBoxDocumentParser());
        List<TextSegment> segments = DocumentSplitters.recursive(2000,0).splitAll(documents);

//        List<Admission> allAdmissions=new ArrayList<>();
        List<AdmissionList> allAdmissions=new ArrayList<>();
        for (TextSegment segment : segments)
        {
//            List<Admission> partAdmissions = extractService.admissionExtract(segment.text());
            AdmissionList partAdmissions=extractService.admissionExtract(segment.text());
            allAdmissions.add(partAdmissions);
        }

//        for(Admission admission:allAdmissions)
//        {
//            System.out.println(admission);
//        }
        for(AdmissionList partAdmission:allAdmissions)
        {
            System.out.println(partAdmission);
        }
    }

    @Test
    void singlePDFExtractTest()
    {
        Document document = ClassPathDocumentLoader.loadDocument("content/西北大学.pdf", new ApachePdfBoxDocumentParser());

        List<TextSegment> segments = DocumentSplitters.recursive(2000,0).split(document);

//        List<Admission> allAdmissions=new ArrayList<>();
        List<AdmissionList> allAdmissions=new ArrayList<>();
        for (TextSegment segment : segments)
        {
//            List<Admission> partAdmissions = extractService.admissionExtract(segment.text());
            AdmissionList partAdmissions=extractService.admissionExtract(segment.text());
            allAdmissions.add(partAdmissions);
        }

//        for(Admission admission:allAdmissions)
//        {
//            System.out.println(admission);
//        }
        for(AdmissionList partAdmission:allAdmissions)
        {
            System.out.println(partAdmission);
        }
    }

    @Test
    void fileNamePrint()
    {
        URL url=getClass().getClassLoader().getResource("content");
        System.out.println(url);
        String s=url.toString();
        s=s.substring(6);
        System.out.println("AAA"+s);
        //File file = new File("G:\\files\\works\\LangChain4j\\consultant plus\\src\\main\\resources\\content");
        File file = new File(s);
        File[] files = file.listFiles();
        for (File file2 : files)
        {
            System.out.println("路径：" + file2.getPath());
            System.out.println("文件夹/文件名：" + file2.getName());
        }
    }

}
