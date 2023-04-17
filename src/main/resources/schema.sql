
DROP TABLE IF EXISTS MEMBER;
DROP TABLE IF EXISTS NOTICE;

-- auto-generated definition
create table MEMBER
(
    ID          BIGINT auto_increment primary key,
    EMAIL       VARCHAR(255),
    USER_NAME   VARCHAR(255),
    PASSWORD    VARCHAR(255),
    PHONE       VARCHAR(255),
    REG_DATE    TIMESTAMP,
    UPDATE_DATE TIMESTAMP,
    STATUS      INTEGER
);

-- auto-generated definition
create table NOTICE
(
    ID          BIGINT auto_increment primary key,
    TITLE       VARCHAR(255),
    CONTENTS    VARCHAR(255),

    HITS        INTEGER,
    LIKES       INTEGER,

    REG_DATE       TIMESTAMP,
    UPDATE_DATE    TIMESTAMP,
    DELETED_DATE   TIMESTAMP,
    DELETED        BOOLEAN,

    MEMBER_ID         BIGINT,
    constraint FK_NOTICE_MEMBER_ID foreign key(MEMBER_ID) references MEMBER(ID)
);

-- auto-generated definition
create table NOTICE_LIKE
(
    ID          BIGINT auto_increment primary key,
    NOTICE_ID   BIGINT,
    MEMBER_ID     BIGINT not null,
    constraint  FK_NOTICE_LIKE_NOTICE_ID foreign key (NOTICE_ID) references NOTICE (ID),
    constraint  FK_NOTICE_LIKE_MEMBER_ID foreign key (MEMBER_ID) references MEMBER (ID)
);