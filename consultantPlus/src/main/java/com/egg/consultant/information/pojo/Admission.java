package com.egg.consultant.information.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author:EGG
 * @Description:
 * @Data Created in 2026-10-27-16:39
 * @Modified By:
 */

/**
 * 录取信息类
 * 记录每个学校每个专业的录取信息内容
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admission
{
    private Long id;
    private String major;
    private String school;
    private Long schoolId;
    private String province;
    private Integer year;
    private String subject;
    private String batch;//本科一批，本科二批等
    private Integer minScore;
    private Integer minRank;
    private Integer avgScore;
    private Integer planCount;//计划录取人数
    private String sourceDoc;
    private LocalDateTime createdAt;

    //暂时未空，预留字段
    private Integer sourcePage;
    private String originalText;

}
