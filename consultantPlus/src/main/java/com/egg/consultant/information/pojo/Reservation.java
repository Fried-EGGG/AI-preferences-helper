package com.egg.consultant.information.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.james.mime4j.dom.datetime.DateTime;

import java.time.LocalDateTime;

/**
 * @Author:EGG
 * @Description:
 * @Data Created in 2026-10-22-16:36
 * @Modified By:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
/** 预约记录表 */
public class Reservation
{
    private Long id;
    private String name;
    private String gender;
    private String phone;
    private LocalDateTime communicationTime;
    private String province;
    private Integer estimatedScore;
}
