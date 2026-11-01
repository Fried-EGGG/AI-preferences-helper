package com.egg.consultant.ai.tools;

import com.egg.consultant.information.pojo.Admission;
import com.egg.consultant.information.pojo.Reservation;
import com.egg.consultant.information.pojo.School;
import com.egg.consultant.information.service.AdmissionService;
import com.egg.consultant.information.service.SchoolService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.data.document.loader.ClassPathDocumentLoader;
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser;
import dev.langchain4j.service.SystemMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author:EGG
 * @Description:
 * @Data Created in 2026-10-27-20:00
 * @Modified By:
 */
@Component
public class InformationTools
{
    @Autowired
    SchoolService schoolService;
    @Autowired
    AdmissionService admissionService;

    @Tool("查询某所学校的录取信息。用户问某学校（可再限定专业、年份）的录取分数、位次、招生计划时使用。schoolName 传学校全称；不传年份返回历年数据。")
    public List<Admission> queryAdmission(
            @P("学校全称，例如：西安交通大学") String schoolName,
            @P("专业关键词，可为空，例如：计算机") String majorKeyword,
            @P("年份，可为空，例如：2024；不传返回历年") Integer year)
    {
        if (schoolName == null || schoolName.isBlank())
        {
            return List.of();
        }
        majorKeyword=normalizeMajorName(majorKeyword);
        return admissionService.queryAdmission(schoolName.trim(), majorKeyword, year);
    }

    @Tool("按专业关键词查询哪些学校有该专业的录取信息。用户问“计算机专业哪些学校能报/各校计算机多少分”时使用。")
    public List<Admission> queryMajorAdmissions(
            @P("专业关键词，例如：计算机") String majorKeyword,
            @P("省份，可为空，例如：陕西") String province)
    {
        if (majorKeyword == null || majorKeyword.isBlank())
        {
            return List.of();
        }
        majorKeyword=normalizeMajorName(majorKeyword);
        return admissionService.queryMajorAdmissions(majorKeyword, province);
    }

    @Tool("根据考生省份和预估分数，查询最近一年该分数上下一定范围内的录取记录，供你按冲/稳/保推荐。用户要求推荐学校或专业时使用。range 默认20；结果太少可以增大到30或40再查一次。")
    public List<Admission> recommendByScore(
            @P("考生生源省份，例如：陕西") String province,
            @P("考生预估分数，纯数字") Integer score,
            @P("分数上下浮动范围，默认20，最大50") Integer range,
            @P("专业关键词，可为空，例如：计算机") String majorKeyword)
    {
        if (province == null || province.isBlank() || score == null)
        {
            return List.of();
        }
        int realRange = range == null ? 20 : Math.min(Math.max(range, 5), 50);
        majorKeyword=normalizeMajorName(majorKeyword);
        return admissionService.recommendByScore(province.trim(), score, realRange, majorKeyword);
    }

    @Tool("列出数据库中有录取数据的学校名称。用户问能查哪些学校，或你不确定学校全称时使用。")
    public List<School> listSchools()
    {
        return schoolService.listSchools();
    }

    /** 把“计算机专业/计算机类专业”统一成“计算机”；空则返回 null，交给 MyBatis 的动态 SQL 跳过该条件 */
    private String normalizeMajorName(String majorKeyWord)
    {
        if(majorKeyWord==null||majorKeyWord.isBlank()||"专业".equals(majorKeyWord.trim()))
        {
            return null;
        }
        return majorKeyWord.trim().replaceAll("专业$", "").trim();
    }
}
