package com.laohuo.company.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 汇报实体类
 */
@Data
@Builder
public class Report {

    private Long id;

    private Long userId;

    private String title;

    private String content;

    private Date createTime;
}
