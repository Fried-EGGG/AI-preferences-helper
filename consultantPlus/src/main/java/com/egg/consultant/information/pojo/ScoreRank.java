package com.egg.consultant.information.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:EGG
 * @Description:
 * @Data Created in 2026-10-27-16:39
 * @Modified By:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
/** 用于记录一分一段表信息 */
public class ScoreRank
{
    private Long id;
    private String province;
    private Integer year;
    private String subject;
    private Integer score;
    private Integer rankNo;
}
