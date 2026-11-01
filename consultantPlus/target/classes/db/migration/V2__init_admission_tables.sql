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

#专业信息
CREATE TABLE major
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    school_id   BIGINT      NOT NULL,
    name        VARCHAR(64) NOT NULL,
    subject_req VARCHAR(64) NULL,
    created_at  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_major (school_id, name),
    KEY idx_major_name (name)
);

#每个学校的每个专业录取情况
CREATE TABLE admission
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    major       varchar(32) NULL,
    school      varchar(32) NULL,
    school_id   BIGINT      NULL,
    province    VARCHAR(16) NULL,
    year        SMALLINT    NULL,
    subject     VARCHAR(16) NULL,
    batch       VARCHAR(32) DEFAULT '本科一批',
    min_score   SMALLINT    NULL,
    min_rank    INT         NULL,
    avg_score   SMALLINT    NULL,
    plan_count  INT         NULL,
    source_doc  VARCHAR(128) NULL,
    created_at  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,

    source_page INT         NULL,
    original_text varchar(1000) NULL,

    UNIQUE KEY uk_admission (school_id, major, province, year, subject, batch),
    KEY idx_query (province, year, subject),
    KEY idx_school (school_id)
);

# 一分一段表
CREATE TABLE score_rank
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    province VARCHAR(16) NOT NULL,
    year     SMALLINT    NOT NULL,
    subject  VARCHAR(16) NOT NULL,
    score    SMALLINT    NOT NULL,
    rank_no  INT         NOT NULL,
    UNIQUE KEY uk_score_rank (province, year, subject, score)
);

# 文档解析记录表
CREATE TABLE my_document
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY ,
    name VARCHAR(255) NOT NULL unique ,
    status varchar(10) ,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);