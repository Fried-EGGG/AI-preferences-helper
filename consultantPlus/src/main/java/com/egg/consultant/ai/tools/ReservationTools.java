package com.egg.consultant.ai.tools;

import com.egg.consultant.information.pojo.Reservation;
import com.egg.consultant.information.service.InformationService;
import com.egg.consultant.information.service.ReservationService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Author:EGG
 * @Description:
 * @Data Created in 2026-10-22-21:13
 * @Modified By:
 */
@Component
public class ReservationTools
{
    @Autowired
    ReservationService reservationService;

    @Autowired
    InformationService informationService;

    @Tool("预约志愿填报服务")
    public void addReservation(@P("考生姓名")String name,
                               @P("考生性别") String gender,
                               @P("考生手机号")String phone,
                               @P("预约沟通时间，格式为：yyyy-MM-dd'T'HH:mm")String communicationTime,
                               @P("考生所在省份")String province,
                               @P("考生预估分数")Integer estimatedScore)
    {
        Reservation reservation=new Reservation(null,name,gender,phone, LocalDateTime.parse(communicationTime),province,estimatedScore);
        reservationService.insert(reservation);
    }

    @Tool("根据考生手机号查询预约单")
    public Reservation findReservation(@P("考生手机号") String phone)
    {
        return reservationService.findByPhone(phone);
    }

}
