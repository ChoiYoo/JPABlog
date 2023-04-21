
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
    STATUS      INTEGER,

    LOCK_YN     BOOLEAN
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

create table MEMBER_LOGIN_HISTORY
(
    ID          BIGINT auto_increment primary key,
    MEMBER_ID     BIGINT,
    EMAIL       VARCHAR(255),
    USER_NAME   VARCHAR(255),
    LOGIN_DATE  TIMESTAMP,
    IP_ADDR   VARCHAR(255)
);

create table BOARD_TYPE
(
    ID          BIGINT auto_increment primary key,
    BOARD_NAME  VARCHAR(255),
    REG_DATE    TIMESTAMP,
    UPDATE_DATE TIMESTAMP,

    USING_YN    BOOLEAN
);


-- auto-generated definition
create table BOARD
(
    ID            BIGINT auto_increment primary key,
    CONTENTS      VARCHAR(255),
    REG_DATE      TIMESTAMP,
    TITLE         VARCHAR(255),
    BOARD_TYPE_ID BIGINT,
    MEMBER_ID       BIGINT,
    TOP_YN        BOOLEAN,

    START_DATE  DATE,
    END_DATE    DATE,

    constraint FK_BOARD_BOARD_TYPE_ID foreign key (BOARD_TYPE_ID) references BOARD_TYPE (ID),
    constraint FK_BOARD_MEMBER_ID foreign key (MEMBER_ID) references MEMBER (ID)
);

create table BOARD_HITS
(
    ID          BIGINT auto_increment primary key,
    REG_DATE    TIMESTAMP,
    BOARD_ID    BIGINT,
    MEMBER_ID     BIGINT,
    constraint FK_BOARD_HITS_BOARD_ID foreign key (BOARD_ID) references BOARD (ID),
    constraint FK_BOARD_HITS_MEMBER_ID foreign key (MEMBER_ID) references MEMBER (ID)
);

create table BOARD_LIKES
(
    ID          BIGINT auto_increment primary key,
    REG_DATE    TIMESTAMP,
    BOARD_ID    BIGINT,
    MEMBER_ID     BIGINT,
    constraint FK_BOARD_LIKE_BOARD_ID foreign key (BOARD_ID) references BOARD (ID),
    constraint FK_BOARD_LIKE_MEMBER_ID foreign key (MEMBER_ID) references MEMBER (ID)
);

create table BOARD_BAD_REPORT
(
    ID             BIGINT auto_increment primary key,
    BOARD_CONTENTS VARCHAR(255),
    BOARD_ID       BIGINT,
    BOARD_REG_DATE TIMESTAMP,
    BOARD_TITLE    VARCHAR(255),
    BOARD_MEMBER_ID  BIGINT,
    COMMENTS       VARCHAR(255),
    REG_DATE       TIMESTAMP,
    USER_EMAIL     VARCHAR(255),
    MEMBER_ID      BIGINT,
    USER_NAME      VARCHAR(255)
);

create table BOARD_SCRAP
(
    ID             BIGINT auto_increment primary key,
    BOARD_CONTENTS VARCHAR(255),
    BOARD_ID       BIGINT,
    BOARD_REG_DATE TIMESTAMP,
    BOARD_TITLE    VARCHAR(255),
    BOARD_TYPE_ID  BIGINT,
    BOARD_MEMBER_ID  BIGINT,
    REG_DATE       TIMESTAMP,
    MEMBER_ID        BIGINT,
    constraint FK_BOARD_SCRAP_MEMBER_ID foreign key (MEMBER_ID) references MEMBER (ID)
);

create table BOARD_BOOKMARK
(
    ID             BIGINT auto_increment primary key,
    MEMBER_ID        BIGINT,

    BOARD_ID       BIGINT,
    BOARD_TYPE_ID  BIGINT,
    BOARD_TITLE    VARCHAR(255),
    BOARD_URL      VARCHAR(255),

    REG_DATE       TIMESTAMP,

    constraint FK_BOARD_BOOKMARK_MEMBER_ID foreign key (MEMBER_ID) references MEMBER (ID)
);

create table MEMBER_INTEREST
(
    ID                  BIGINT auto_increment primary key,

    MEMBER_ID             BIGINT,
    INTEREST_MEMBER_ID    BIGINT,

    REG_DATE            TIMESTAMP,

    constraint FK_MEMBER_INTEREST_MEMBER_ID foreign key (MEMBER_ID) references MEMBER (ID),
    constraint FK_MEMBER_INTEREST_INTEREST_MEMBER_ID foreign key (INTEREST_MEMBER_ID) references MEMBER (ID)
);

create table BOARD_COMMENT
(
    ID                  BIGINT auto_increment primary key,
    COMMENTS            VARCHAR(255),
    REG_DATE            TIMESTAMP,
    BOARD_ID            BIGINT,
    MEMBER_ID           BIGINT,
    constraint          FK_BOARD_COMMENT_MEMBER_ID foreign key (MEMBER_ID) references MEMBER (ID),
    constraint          FK_BOARD_COMMENT_BOARD_ID foreign key (BOARD_ID) references BOARD (ID)
);