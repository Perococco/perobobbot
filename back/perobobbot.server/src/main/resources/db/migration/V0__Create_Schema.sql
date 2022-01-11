drop schema if exists PEROBOBBOT;
create schema if not exists PEROBOBBOT;

create sequence PEROBOBBOT.hibernate_sequence start with 1 increment by 1;

create table PEROBOBBOT.BOT (
                                ID bigint not null,
                                VERSION integer not null,
                                EXTERNAL_ID binary not null,
                                NAME varchar(255) not null,
                                USER_ID bigint not null,
                                primary key (ID)
);

create table PEROBOBBOT.BOT_CREDENTIAL (
                                           ID bigint not null,
                                           VERSION integer not null,
                                           BOT_ID bigint not null,
                                           CREDENTIAL_ID bigint not null,
                                           primary key (ID)
);

create table PEROBOBBOT.BOT_EXTENSION (
                                          ID bigint not null,
                                          VERSION integer not null,
                                          ENABLED boolean not null,
                                          BOT_ID bigint not null,
                                          EXTENSION_ID bigint not null,
                                          primary key (ID)
);

create table PEROBOBBOT.CLIENT (
                                   ID bigint not null,
                                   VERSION integer not null,
                                   EXTERNAL_ID binary not null,
                                   CLIENT_ID varchar(255),
                                   CLIENT_SECRET varchar(255),
                                   PLATFORM varchar(255),
                                   primary key (ID)
);

create table PEROBOBBOT.CLIENT_TOKEN (
                                         ID bigint not null,
                                         VERSION integer not null,
                                         EXTERNAL_ID binary not null,
                                         ACCESS_TOKEN varchar(255) not null,
                                         DURATION bigint not null,
                                         EXPIRATION_INSTANT timestamp not null,
                                         CLIENT_ID bigint,
                                         primary key (ID)
);

create table PEROBOBBOT.CONDITION (
                                      ID bigint not null,
                                      VALUE varchar(255) not null,
                                      CRITERIA varchar(255) not null,
                                      primary key (ID, CRITERIA)
);

create table PEROBOBBOT.EXTENSION (
                                      ID bigint not null,
                                      VERSION integer not null,
                                      EXTERNAL_ID binary not null,
                                      ACTIVATED boolean not null,
                                      AVAILABLE boolean not null,
                                      NAME varchar(255) not null,
                                      primary key (ID)
);

create table PEROBOBBOT.JOINED_TWITCH_CHANNEL (
                                                  ID bigint not null,
                                                  VERSION integer not null,
                                                  EXTERNAL_ID binary not null,
                                                  CHANNEL_NAME varchar(255) not null,
                                                  BOT_ID bigint not null,
                                                  TWITCH_USER_ID bigint not null,
                                                  primary key (ID)
);

create table PEROBOBBOT.PLATFORM_USER (
                                          PLATFORM varchar(31) not null,
                                          ID bigint not null,
                                          VERSION integer not null,
                                          EXTERNAL_ID binary not null,
                                          USER_ID varchar(255) not null,
                                          DISCORD_DISCRIMINATOR varchar(255),
                                          DISCORD_LOGIN varchar(255),
                                          TWITCH_LOGIN varchar(255),
                                          TWITCH_PSEUDO varchar(255),
                                          primary key (ID)
);

create table PEROBOBBOT.ROLE (
                                 ID bigint not null,
                                 VERSION integer not null,
                                 ROLE varchar(255),
                                 primary key (ID)
);

create table PEROBOBBOT.ROLE_OPERATION (
                                           ROLE_ID bigint not null,
                                           OPERATION varchar(255)
);

create table PEROBOBBOT.SAFE (
                                 ID bigint not null,
                                 VERSION integer not null,
                                 EXTERNAL_ID binary not null,
                                 CHANNEL_ID varchar(255) not null,
                                 PLATFORM_USER_ID bigint not null,
                                 primary key (ID)
);

create table PEROBOBBOT.SAFE_CREDIT (
                                        ID bigint not null,
                                        CREDIT bigint not null,
                                        POINT_TYPE varchar(255) not null,
                                        primary key (ID, POINT_TYPE)
);

create table PEROBOBBOT.TRANSACTION (
                                        ID bigint not null,
                                        VERSION integer not null,
                                        EXTERNAL_ID binary not null,
                                        AMOUNT bigint,
                                        EXPIRATION_DATE timestamp not null,
                                        TYPE varchar(255) not null,
                                        STATE varchar(255) not null,
                                        TARGET_ID bigint not null,
                                        primary key (ID)
);

create table PEROBOBBOT.TWITCH_SUBSCRIPTION (
                                                ID bigint not null,
                                                VERSION integer not null,
                                                EXTERNAL_ID binary not null,
                                                CALLBACK_URL varchar(255) not null,
                                                PLATFORM varchar(255) not null,
                                                SUBSCRIPTION_ID varchar(255) not null,
                                                TYPE varchar(255) not null,
                                                primary key (ID)
);

create table PEROBOBBOT.USER (
                                 ID bigint not null,
                                 VERSION integer not null,
                                 IDENTIFICATION_MODE varchar(255) not null,
                                 OPENID_PLATFORM varchar(255),
                                 PASSWORD varchar(255),
                                 DEACTIVATED boolean not null,
                                 JWT_CLAIM varchar(255) not null,
                                 LOCALE varchar(255),
                                 LOGIN varchar(255),
                                 primary key (ID)
);

create table PEROBOBBOT.USER_ROLE (
                                      USER_ID bigint not null,
                                      ROLE_ID bigint not null,
                                      primary key (USER_ID, ROLE_ID)
);

create table PEROBOBBOT.USER_TOKEN (
                                       ID bigint not null,
                                       VERSION integer not null,
                                       EXTERNAL_ID binary not null,
                                       ACCESS_TOKEN varchar(255) not null,
                                       DURATION bigint not null,
                                       EXPIRATION_INSTANT timestamp not null,
                                       MAIN boolean,
                                       REFRESH_TOKEN varchar(255) not null,
                                       SCOPES varchar(2048) not null,
                                       USER_ID bigint not null,
                                       PLATFORM_USER_ID bigint not null,
                                       primary key (ID)
);

create table PEROBOBBOT.USER_TWITCH_SUBSCRIPTION (
                                                     ID bigint not null,
                                                     VERSION integer not null,
                                                     EXTERNAL_ID binary not null,
                                                     OWNER_ID bigint not null,
                                                     SUBSCRIPTION_ID bigint not null,
                                                     primary key (ID)
);

alter table PEROBOBBOT.BOT
    add constraint UKtb35df8l26y2awkllnmjp6mfa unique (USER_ID, NAME);

alter table PEROBOBBOT.BOT_CREDENTIAL
    add constraint UK9nvc80q1ypfsewimmwnggsm98 unique (BOT_ID, CREDENTIAL_ID);

alter table PEROBOBBOT.BOT_EXTENSION
    add constraint UK9wsjqtkhhoqwugwia2jcnc4mm unique (BOT_ID, EXTENSION_ID);

alter table PEROBOBBOT.CLIENT
    add constraint UK_5vcmducx1a00wdxf6gu7fk6us unique (PLATFORM);

alter table PEROBOBBOT.EXTENSION
    add constraint UK_dbsdvpyjxwg2lb4b7ohdtun42 unique (NAME);

alter table PEROBOBBOT.JOINED_TWITCH_CHANNEL
    add constraint UKshhkq6o9ktqd166kosp5ivplq unique (BOT_ID, TWITCH_USER_ID, CHANNEL_NAME);

alter table PEROBOBBOT.PLATFORM_USER
    add constraint UKdiuduwgx1c5vrieuleuy7ubwg unique (PLATFORM, USER_ID);

alter table PEROBOBBOT.ROLE
    add constraint UK_3lhyjfk8dr6wyuurwws7wxtdv unique (ROLE);

alter table PEROBOBBOT.USER
    add constraint UK_m6gjtxi6t4thhq8x960qif5cy unique (LOGIN);

alter table PEROBOBBOT.BOT
    add constraint FKa7dtiy852v0u37blbl5x09mfs
        foreign key (USER_ID)
            references PEROBOBBOT.USER;

alter table PEROBOBBOT.BOT_CREDENTIAL
    add constraint FKgndm8ekog3rtpnxpnb0w2de15
        foreign key (BOT_ID)
            references PEROBOBBOT.BOT;

alter table PEROBOBBOT.BOT_CREDENTIAL
    add constraint FKbewbpgr2a6g35medjpntx2toc
        foreign key (CREDENTIAL_ID)
            references PEROBOBBOT.USER_TOKEN;

alter table PEROBOBBOT.BOT_EXTENSION
    add constraint FKn88ochxnkla791syu1tkw9ol8
        foreign key (BOT_ID)
            references PEROBOBBOT.BOT;

alter table PEROBOBBOT.BOT_EXTENSION
    add constraint FKc8cdjb355qlirj9deecfuk0bf
        foreign key (EXTENSION_ID)
            references PEROBOBBOT.EXTENSION;

alter table PEROBOBBOT.CLIENT_TOKEN
    add constraint FK3327mvhyfxct1q12keni9suyc
        foreign key (CLIENT_ID)
            references PEROBOBBOT.CLIENT;

alter table PEROBOBBOT.CONDITION
    add constraint FKatyo441p5ot6vn447ak54p15s
        foreign key (ID)
            references PEROBOBBOT.TWITCH_SUBSCRIPTION;

alter table PEROBOBBOT.JOINED_TWITCH_CHANNEL
    add constraint FKke6fr4w18y3cq9bq89jbgt0ou
        foreign key (BOT_ID)
            references PEROBOBBOT.BOT;

alter table PEROBOBBOT.JOINED_TWITCH_CHANNEL
    add constraint FKj9qurhfn4rsu4nsssjup8hb5u
        foreign key (TWITCH_USER_ID)
            references PEROBOBBOT.PLATFORM_USER;

alter table PEROBOBBOT.ROLE_OPERATION
    add constraint FKj526ueay6dsf8uj48g6gygul0
        foreign key (ROLE_ID)
            references PEROBOBBOT.ROLE;

alter table PEROBOBBOT.SAFE
    add constraint FK6fkxmu4a9fbd1jmsx8v6l6p06
        foreign key (PLATFORM_USER_ID)
            references PEROBOBBOT.PLATFORM_USER;

alter table PEROBOBBOT.SAFE_CREDIT
    add constraint FKlwshea43pxk96dcefspxwmnyk
        foreign key (ID)
            references PEROBOBBOT.SAFE;

alter table PEROBOBBOT.TRANSACTION
    add constraint FK4yfgkx6vo4ce9nx0nyrntd28v
        foreign key (TARGET_ID)
            references PEROBOBBOT.SAFE;

alter table PEROBOBBOT.USER_ROLE
    add constraint FKn1rn9qodd3u4le8uf3kl33qe3
        foreign key (ROLE_ID)
            references PEROBOBBOT.ROLE;

alter table PEROBOBBOT.USER_ROLE
    add constraint FKa8x5mvctia7u43u2mm3hyy5bm
        foreign key (USER_ID)
            references PEROBOBBOT.USER;

alter table PEROBOBBOT.USER_TOKEN
    add constraint FKd8qhbvike9vpnvnnnr7amr7l6
        foreign key (USER_ID)
            references PEROBOBBOT.USER;

alter table PEROBOBBOT.USER_TOKEN
    add constraint FKdc7k4dpvbyj7l0rfyfbrhs6og
        foreign key (PLATFORM_USER_ID)
            references PEROBOBBOT.PLATFORM_USER;

alter table PEROBOBBOT.USER_TWITCH_SUBSCRIPTION
    add constraint FKifhy3rthilqhpbi619k9rdcn7
        foreign key (OWNER_ID)
            references PEROBOBBOT.USER;

alter table PEROBOBBOT.USER_TWITCH_SUBSCRIPTION
    add constraint FKjy3hrdqq2mn5ep0osgf8r2lae
        foreign key (SUBSCRIPTION_ID)
            references PEROBOBBOT.TWITCH_SUBSCRIPTION;
