
create sequence PUBLIC.hibernate_sequence start with 1 increment by 1;

create table PUBLIC.BOT
(
    ID          bigint       not null,
    VERSION     integer      not null,
    EXTERNAL_ID binary       not null,
    NAME        varchar(255) not null,
    USER_ID     bigint       not null,
    primary key (ID)
);

create table PUBLIC.BOT_CREDENTIAL
(
    ID       bigint       not null,
    VERSION  integer      not null,
    NICK     varchar(255) not null,
    PASS     varchar(255) not null,
    PLATFORM varchar(255) not null,
    BOT_ID   bigint       not null,
    primary key (ID)
);

create table PUBLIC.EXTENSION
(
    ID          bigint       not null,
    VERSION     integer      not null,
    EXTERNAL_ID binary       not null,
    AVAILABLE   boolean      not null,
    ENABLED     boolean      not null,
    NAME        varchar(255) not null,
    primary key (ID)
);

create table PUBLIC.OPERATION
(
    ID      bigint  not null,
    VERSION integer not null,
    NAME    varchar(255),
    primary key (ID)
);

create table PUBLIC.ROLE
(
    ID      bigint  not null,
    VERSION integer not null,
    NAME    varchar(255),
    primary key (ID)
);

create table PUBLIC.ROLE_OPERATION
(
    ROLE_ID      bigint not null,
    OPERATION_ID bigint not null,
    primary key (ROLE_ID, OPERATION_ID)
);

create table PUBLIC.USER
(
    ID        bigint       not null,
    VERSION   integer      not null,
    JWT_CLAIM varchar(255) not null,
    LOGIN     varchar(255),
    PASSWORD  varchar(255) not null,
    primary key (ID)
);

create table PUBLIC.USER_ROLE
(
    USER_ID bigint not null,
    ROLE_ID bigint not null,
    primary key (USER_ID, ROLE_ID)
);

alter table PUBLIC.BOT
    add constraint UK_kvynkrm7ffxo3d0t0i00biyfh unique (EXTERNAL_ID);

alter table PUBLIC.BOT_CREDENTIAL
    add constraint UK_BOT_CREDENTIAL__BOT_ID__PLATFORM unique (BOT_ID, PLATFORM);

alter table PUBLIC.EXTENSION
    add constraint UK_EXTENSION__NAME unique (NAME);

alter table PUBLIC.EXTENSION
    add constraint UK_s1g88q6fj23rfbfi9njbw61ti unique (EXTERNAL_ID);

alter table PUBLIC.OPERATION
    add constraint UK_OPERATION__NAME unique (NAME);

alter table PUBLIC.ROLE
    add constraint UK_ROLE__NAME unique (NAME);

alter table PUBLIC.USER
    add constraint UK_USER__LOGIN unique (LOGIN);

alter table PUBLIC.BOT
    add constraint FK_BOT__USER
        foreign key (USER_ID)
            references PUBLIC.USER;

alter table PUBLIC.BOT_CREDENTIAL
    add constraint FK_BOT_CREDENTIAL__BOT
        foreign key (BOT_ID)
            references PUBLIC.BOT;

alter table PUBLIC.ROLE_OPERATION
    add constraint FK_ROLE_OPERATION__OPERATION
        foreign key (OPERATION_ID)
            references PUBLIC.OPERATION;

alter table PUBLIC.ROLE_OPERATION
    add constraint FK_ROLE_OPERATION__ROLE
        foreign key (ROLE_ID)
            references PUBLIC.ROLE;

alter table PUBLIC.USER_ROLE
    add constraint FK_USER_ROLE__ROLE
        foreign key (ROLE_ID)
            references PUBLIC.ROLE;

alter table PUBLIC.USER_ROLE
    add constraint FK_USER_ROLE__USER
        foreign key (USER_ID)
            references PUBLIC.USER;
