package com.egg.consultant.information.mapper;

import com.egg.consultant.information.pojo.Reservation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Author:EGG
 * @Description:
 * @Data Created in 2026-10-22-16:37
 * @Modified By:
 */
@Mapper
public interface ReservationMapper
{
    @Insert("insert into reservation(name,gender,phone,communication_time,province,estimated_score) values(#{name},#{gender},#{phone},#{communicationTime},#{province},#{estimatedScore})")
    void addReservation(Reservation reservation);

    @Select("select * from reservation where phone=#{phone}")
    Reservation findByPhone(String phone);
}
