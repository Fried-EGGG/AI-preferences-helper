package com.egg.consultant.information.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

/**
 * @Author:EGG
 * @Description:
 * @Data Created in 2026-10-27-16:39
 * @Modified By:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
/** 学校信息类 */
public class School
{
    private Long id;
    private String name;
    private String city;
    private String level;
    private LocalDateTime createdAt;
}
