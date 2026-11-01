package com.egg.consultant.information.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author:EGG
 * @Description:
 * @Data Created in 2026-10-29-00:07
 * @Modified By:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyDocument
{
    private Long id;
    private String name;
    private String status;
    private LocalDateTime createdAt;
}
