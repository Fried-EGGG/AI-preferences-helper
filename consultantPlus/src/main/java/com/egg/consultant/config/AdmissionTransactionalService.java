package com.egg.consultant.config;

import com.egg.consultant.information.pojo.Admission;
import com.egg.consultant.information.pojo.MyDocument;
import com.egg.consultant.information.pojo.School;
import com.egg.consultant.information.service.AdmissionService;
import com.egg.consultant.information.service.MyDocumentService;
import com.egg.consultant.information.service.SchoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author:EGG
 * @Description:
 * @Data Created in 2026-10-30-16:04
 * @Modified By:
 */
@Slf4j
@Service
public class AdmissionTransactionalService
{
    @Autowired
    AdmissionService admissionService;
    @Autowired
    SchoolService schoolService;
    @Autowired
    MyDocumentService myDocumentService;

    @Transactional
    public void admissionPersistent(String fileName, List<Admission> admissions)
    {
        //开始插入admission前先将从旧文档中抽取的数据删除，以防先前操作中断产生了脏数据
        admissionService.deleteByDoc(fileName);
        int skipped=0;
        for (Admission admission : admissions)
        {
            School school = schoolService.selectBySchoolName(admission.getSchool());
            //如果数据库表中没有该学校，则设置学校信息并插入
            if (school == null)
            {
                //school为空，空指针，重新new一个
                school = new School();
                school.setName(admission.getSchool());
                school.setCreatedAt(LocalDateTime.now());
                schoolService.insertSchool(school);
            }
            //重新查表得到学校对象（含schoolId）
            school = schoolService.selectBySchoolName(admission.getSchool());
            //设置schoolId
            admission.setSchoolId(school.getId());

            //如果当前admission在数据库中已存在，就跳过这一条
            if (admissionService.exists(admission))
            {
                skipped++;
                log.warn("跳过重复录取行: 学校={}, 专业={}, 省份={}, 年份={}, 科类={}, 批次={}",
                        admission.getSchool(), admission.getMajor(), admission.getProvince(),
                        admission.getYear(), admission.getSubject(), admission.getBatch());
                continue;
            }

            admissionService.insertAdmission(admission);
        }
        //记录有多少条数据被跳过了
        log.info("文档 {} 写入 {} 条，跳过重复 {} 条", fileName, admissions.size() - skipped, skipped);

        //数据库中没有此文档，创建对象存入数据库
        MyDocument newDocument = new MyDocument();
        newDocument.setName(fileName);
        newDocument.setStatus("SUCCESS");
        newDocument.setCreatedAt(LocalDateTime.now());
        myDocumentService.insertMyDocument(newDocument);
    }
}
