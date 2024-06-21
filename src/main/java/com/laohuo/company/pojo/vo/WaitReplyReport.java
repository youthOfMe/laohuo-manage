package com.laohuo.company.pojo.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class WaitReplyReport {

    private Long id;

    private String title;

    private String content;

    private String nickname;

    private Date createTime;
}
