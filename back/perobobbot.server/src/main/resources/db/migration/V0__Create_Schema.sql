drop schema if exists PEROBOT;
create schema if not exists PEROBOT;

create sequence PEROBOT.hibernate_sequence start with 1 increment by 1;

create table PEROBOT.BOT
(
    ID          bigint       not null,
    VERSION     integer      not null,
    EXTERNAL_ID binary       not null,
    NAME        varchar(255) not null,
    USER_ID     bigint       not null,
    primary key (ID)
);

create table PEROBOT.BOT_CREDENTIAL
(
    ID            bigint  not null,
    VERSION       integer not null,
    BOT_ID        bigint  not null,
    CREDENTIAL_ID bigint  not null,
    primary key (ID)
);

create table PEROBOT.BOT_EXTENSION
(
    ID           bigint  not null,
    VERSION      integer not null,
    ENABLED      boolean not null,
    BOT_ID       bigint  not null,
    EXTENSION_ID bigint  not null,
    primary key (ID)
);

create table PEROBOT.CREDENTIAL
(
    ID          bigint       not null,
    VERSION     integer      not null,
    EXTERNAL_ID binary       not null,
    NICK        varchar(255) not null,
    PLATFORM    varchar(255),
    SECRET      varchar(255) not null,
    USER_ID     bigint       not null,
    primary key (ID)
);

create table PEROBOT.EXTENSION
(
    ID          bigint       not null,
    VERSION     integer      not null,
    EXTERNAL_ID binary       not null,
    ACTIVATED   boolean      not null,
    AVAILABLE   boolean      not null,
    NAME        varchar(255) not null,
    primary key (ID)
);

create table PEROBOT.SAFE
(
    ID           bigint       not null,
    VERSION      integer      not null,
    EXTERNAL_ID  binary       not null,
    AMOUNT       bigint,
    CHANNEL_NAME varchar(255) not null,
    PLATFORM     varchar(255) not null,
    POINT_TYPE   varchar(255) not null,
    USER_CHAT_ID varchar(255) not null,
    primary key (ID)
);

create table PEROBOT.ROLE
(
    ID      bigint  not null,
    VERSION integer not null,
    ROLE    varchar(255),
    primary key (ID)
);

create table PEROBOT.ROLE_OPERATION
(
    ROLE_ID   bigint not null,
    OPERATION varchar(255)
);

create table PEROBOT.USER
(
    ID          bigint       not null,
    VERSION     integer      not null,
    DEACTIVATED boolean      not null,
    JWT_CLAIM   varchar(255) not null,
    LOCALE      varchar(255),
    LOGIN       varchar(255),
    PASSWORD    varchar(255) not null,
    primary key (ID)
);

create table PEROBOT.USER_ROLE
(
    USER_ID bigint not null,
    ROLE_ID bigint not null,
    primary key (USER_ID, ROLE_ID)
);

create table PEROBOT.WITHDRAW
(
    ID             bigint  not null,
    VERSION        integer not null,
    EXTERNAL_ID    binary  not null,
    amount         bigint  not null,
    expirationTime timestamp,
    TARGET_ID      bigint  not null,
    primary key (ID)
);

alter table PEROBOT.BOT
    add constraint UK_ievgedbgan6tu2f1ge01g5bit unique (NAME);

alter table PEROBOT.BOT_CREDENTIAL
    add constraint UK9nvc80q1ypfsewimmwnggsm98 unique (BOT_ID, CREDENTIAL_ID);

alter table PEROBOT.BOT_EXTENSION
    add constraint UK9wsjqtkhhoqwugwia2jcnc4mm unique (BOT_ID, EXTENSION_ID);

alter table PEROBOT.EXTENSION
    add constraint UK_dbsdvpyjxwg2lb4b7ohdtun42 unique (NAME);

alter table PEROBOT.ROLE
    add constraint UK_3lhyjfk8dr6wyuurwws7wxtdv unique (ROLE);

alter table PEROBOT.USER
    add constraint UK_m6gjtxi6t4thhq8x960qif5cy unique (LOGIN);

alter table PEROBOT.BOT
    add constraint FKa7dtiy852v0u37blbl5x09mfs
        foreign key (USER_ID)
            references PEROBOT.USER;

alter table PEROBOT.BOT_CREDENTIAL
    add constraint FKgndm8ekog3rtpnxpnb0w2de15
        foreign key (BOT_ID)
            references PEROBOT.BOT;

alter table PEROBOT.BOT_CREDENTIAL
    add constraint FKsu5xbpb1uy08ihyahmp3w6jxc
        foreign key (CREDENTIAL_ID)
            references PEROBOT.CREDENTIAL;

alter table PEROBOT.BOT_EXTENSION
    add constraint FKn88ochxnkla791syu1tkw9ol8
        foreign key (BOT_ID)
            references PEROBOT.BOT;

alter table PEROBOT.BOT_EXTENSION
    add constraint FKc8cdjb355qlirj9deecfuk0bf
        foreign key (EXTENSION_ID)
            references PEROBOT.EXTENSION;

alter table PEROBOT.CREDENTIAL
    add constraint FKs256vl1hl22509qp947gh12pt
        foreign key (USER_ID)
            references PEROBOT.USER;

alter table PEROBOT.ROLE_OPERATION
    add constraint FKj526ueay6dsf8uj48g6gygul0
        foreign key (ROLE_ID)
            references PEROBOT.ROLE;

alter table PEROBOT.USER_ROLE
    add constraint FKn1rn9qodd3u4le8uf3kl33qe3
        foreign key (ROLE_ID)
            references PEROBOT.ROLE;

alter table PEROBOT.USER_ROLE
    add constraint FKa8x5mvctia7u43u2mm3hyy5bm
        foreign key (USER_ID)
            references PEROBOT.USER;

alter table PEROBOT.WITHDRAW
    add constraint FKoorvbnw5q6wrloptb8ov3yf90
        foreign key (TARGET_ID)
            references PEROBOT.SAFE;
