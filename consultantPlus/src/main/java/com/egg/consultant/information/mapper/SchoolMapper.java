package com.egg.consultant.information.mapper;

import com.egg.consultant.information.pojo.School;
import com.egg.consultant.information.pojo.ScoreRank;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author:EGG
 * @Description:
 * @Data Created in 2026-10-27-17:06
 * @Modified By:
 */
@Mapper
public interface SchoolMapper
{
    @Insert("insert into school(name,city,level,created_at) values (#{name},#{city},#{level},#{createdAt})")
    void insertSchool(School school);

    @Select("select * from school where name=#{schoolName}")
    School selectBySchoolName(@Param("schoolName") String schoolName);

    @Select("Select * from school where city=#{city}")
    School selectByCity(String city);

    @Select("""
            select s.id, s.name, s.city, s.level from school s 
            where exists (select * from admission a where a.school_id = s.id) 
            order by s.id
            """)
    List<School> listSchools();
}
