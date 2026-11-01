create database consultant;

use consultant;

create table if not exists reservation
(
    id                 bigint  primary key auto_increment   not null comment '主键ID',
    name               varchar(50) not null comment '考生姓名',
    gender             varchar(2)  not null comment '考生性别',
    phone              varchar(20) not null comment '考生手机号',
    communication_time datetime    not null comment '沟通时间',
    province           varchar(32) not null comment '考生所处的省份',
    estimated_score    int         not null comment '考生预估分数'
    );

#学校信息
CREATE TABLE school
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(64) NOT NULL,
    city   VARCHAR(32) NULL,
    level      VARCHAR(32) NULL,
    created_at DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_school_name (name)
);

#每个学校的每个专业录取情况
CREATE TABLE admission
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    major       varchar(64) NULL,
    school      varchar(64) NULL,
    school_id   BIGINT      NULL,
    province    VARCHAR(16) NULL,
    year        SMALLINT    NULL,
    subject     VARCHAR(16) NULL,
    batch       VARCHAR(32) NOT NULL DEFAULT '本科一批',
    min_score   SMALLINT    NULL,
    min_rank    INT         NULL,
    avg_score   SMALLINT    NULL,
    plan_count  INT         NULL,
    source_doc  VARCHAR(255) NULL,
    created_at  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,

    source_page INT         NULL,
    original_text text NULL,

    UNIQUE KEY uk_admission (school_id, major, province, year, subject, batch),
    KEY idx_query (province, year, subject),
    KEY idx_school (school_id)
);

# 文档解析记录表
CREATE TABLE my_document
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY ,
    name VARCHAR(255) NOT NULL unique ,
    status varchar(10) ,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

