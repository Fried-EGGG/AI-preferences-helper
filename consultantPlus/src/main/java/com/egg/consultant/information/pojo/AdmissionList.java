package com.egg.consultant.information.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author:EGG
 * @Description:
 * @Data Created in 2026-10-28-20:47
 * @Modified By:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
//提示词模式下，List<Admission>无法生成List<Pojo>的输出解析器，需要在AdmissionList内部封装List
//List<String>和Admission可以生成输出解析器，但是List<Admission>无法生成解析器
public class AdmissionList
{
    private String school;
    private Integer year;
    private String province;
    private List<Details> details;
}
