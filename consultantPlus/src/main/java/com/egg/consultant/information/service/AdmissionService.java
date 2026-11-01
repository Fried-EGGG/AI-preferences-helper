package com.egg.consultant.information.service;

import com.egg.consultant.information.mapper.AdmissionMapper;
import com.egg.consultant.information.pojo.Admission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:EGG
 * @Description:
 * @Data Created in 2026-10-27-19:17
 * @Modified By:
 */
@Service
public class AdmissionService
{
    @Autowired
    AdmissionMapper admissionMapper;

    public void insertAdmission(Admission admission)
    {
        admissionMapper.insertAdmission(admission);
    }

    public void deleteByDoc(String sourceDoc)
    {
        admissionMapper.deleteByDoc(sourceDoc);
    }

    public boolean exists(Admission admission)
    {
        return admissionMapper.countByUniqueKey(admission) > 0;
    }

    public List<Admission> queryAdmission(String schoolName,String majorKeyWord,Integer year)
    {
        return admissionMapper.queryAdmission(schoolName,majorKeyWord,year);
    }

    public List<Admission> queryMajorAdmissions(String majorKeyWord,String province)
    {
        return admissionMapper.queryMajorAdmissions(majorKeyWord,province);
    }

    public List<Admission> recommendByScore(String province,Integer score,Integer range,String majorKeyWord)
    {
        return admissionMapper.recommendByScore(province,score,range,majorKeyWord);
    }
}
