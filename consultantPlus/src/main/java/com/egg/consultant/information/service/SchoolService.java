package com.egg.consultant.information.service;

import com.egg.consultant.information.mapper.SchoolMapper;
import com.egg.consultant.information.pojo.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:EGG
 * @Description:
 * @Data Created in 2026-10-27-19:16
 * @Modified By:
 */
@Service
public class SchoolService
{
    @Autowired
    private SchoolMapper schoolMapper;

    public void insertSchool(School school)
    {
        schoolMapper.insertSchool(school);
    }

    public School selectBySchoolName(String schoolName)
    {
        return schoolMapper.selectBySchoolName(schoolName);
    }

    public List<School> listSchools()
    {
        return schoolMapper.listSchools();
    }
}
