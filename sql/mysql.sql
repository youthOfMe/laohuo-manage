create table report
(
    id         bigint auto_increment comment '主键'
        primary key,
    userId     bigint                             not null comment '汇报者',
    title      varchar(128)                       null comment '汇报标题',
    content    varchar(512)                       null comment '汇报内容',
    createTime datetime default CURRENT_TIMESTAMP null comment '汇报时间',
    status     tinyint  default 0                 not null comment '状态 0 = 未回复 1 = 已回复',
    replyId    bigint   default 0                 not null comment '回复编号'
);

create table user
(
    id         bigint auto_increment comment '用户ID'
        primary key,
    username   varchar(54)                        not null comment '用户名',
    password   varchar(54)                        not null comment '密码',
    nickname   varchar(128)                       null comment '用户昵称',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建事件',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isAdmin    tinyint  default 0                 not null comment '是否为管理员 0 = 不是 1 = 是',
    isDelete   tinyint  default 0                 not null comment '逻辑删除 0 = 不删除 1 = 删除',
    salary     double   default 0                 not null comment '薪水'
)
    comment '用户表';

