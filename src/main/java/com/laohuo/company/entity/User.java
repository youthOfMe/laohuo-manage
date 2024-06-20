package com.laohuo.company.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 用户实体类
 */
@Data
@ToString
public class User {

    private Long id;
    private String username;
    private String password;
    private String nickname;
    private Date createTime;
    private Date updateTime;
    private Integer isAdmin;
    private Integer isDelete;
    private Double salary;
}
