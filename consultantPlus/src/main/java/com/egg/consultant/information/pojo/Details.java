package com.egg.consultant.information.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author:EGG
 * @Description:
 * @Data Created in 2026-10-28-22:36
 * @Modified By:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Details
{
    private String subject;
    private String batch;
    private String major;
    private Integer minScore;
    private Integer minRank;
    private Integer avgScore;
    private Integer planCount;
}
