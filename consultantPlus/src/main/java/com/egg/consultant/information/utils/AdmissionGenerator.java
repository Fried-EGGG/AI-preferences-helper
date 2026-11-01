package com.egg.consultant.information.utils;

/**
 * @Author:EGG
 * @Description:
 * @Data Created in 2026-10-28-22:26
 * @Modified By:
 */

import com.egg.consultant.information.pojo.Admission;
import com.egg.consultant.information.pojo.AdmissionList;
import com.egg.consultant.information.pojo.Details;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** 工具类，用来把AdmissionList转化成List<Admission> */
public final class AdmissionGenerator
{
    private AdmissionGenerator()
    {

    }

    /**
     *     private Long id;
     *     private Long schoolId;
     *     private String school;
     *     private Integer year;
     *     private String province;
     *     private String major;
     *     private String subject;
     *     private String batch;//本科一批，本科二批等
     *     private Integer minScore;
     *     private Integer minRank;
     *     private Integer avgScore;
     *     private Integer planCount;//计划录取人数
     *     private String sourceDoc;
     *     private LocalDateTime createdAt;
     * */
    //将单条admissionList中的内容转化为List<Admission>
    public static List<Admission> generateList(AdmissionList admissionList,String filename)
    {
        //获取details链表，创建admission链表用于接收数据
        List<Admission> admissions=new ArrayList<>();
        //如果这篇文章不是录取信息文档，此处的admissionList和其中的details为空，后续会报错
        if(admissionList==null||admissionList.getDetails()==null)
        {
            return admissions;
        }
        List<Details> detailsList=admissionList.getDetails();
        //遍历details链表，逐条封装信息，获取admission链表
        for(Details details:detailsList)
        {
            Admission admission=new Admission();

            //此处没有设置schoolId，schoolId字段在传入数据库表前设置
            //admission.setSchoolId();
            admission.setSchool(admissionList.getSchool());
            admission.setYear(admissionList.getYear());
            admission.setProvince(admissionList.getProvince());
            admission.setSubject(details.getSubject());
            admission.setBatch(details.getBatch());
            admission.setMajor(details.getMajor());
            admission.setMinScore(details.getMinScore());
            admission.setMinRank(details.getMinRank());
            admission.setAvgScore(details.getAvgScore());
            admission.setPlanCount(details.getPlanCount());
            admission.setSourceDoc(filename);
            admission.setCreatedAt(LocalDateTime.now());
            admissions.add(admission);
        }
        return admissions;
    }
}
