package com.laohuo.company.entity;

import lombok.Data;

import java.util.Date;

/**
 * 汇报实体类
 */
@Data
public class Report {

    private Long id;

    private Long userId;

    private String title;

    private String content;

    private Date createTime;
}
