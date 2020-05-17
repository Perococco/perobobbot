
create sequence PUBLIC.hibernate_sequence start with 1 increment by 1;

    create table PUBLIC.ROLE (
       ID bigint not null,
        VERSION integer not null,
        NAME varchar(255),
        primary key (ID)
    );

    create table PUBLIC.USER (
       ID bigint not null,
        VERSION integer not null,
        JWT_CLAIM varchar(255) not null,
        LOGIN varchar(255),
        PASSWORD varchar(255) not null,
        primary key (ID)
    );

    create table PUBLIC.USER_ROLE (
       ID bigint not null,
        VERSION integer not null,
        ROLE_ID bigint,
        USER_ID bigint,
        primary key (ID)
    );

    alter table PUBLIC.ROLE
       add constraint UK_ROLE__NAME unique (NAME);

    alter table PUBLIC.USER
       add constraint UK__USER__LOGIN unique (LOGIN);

    alter table PUBLIC.USER_ROLE
       add constraint FK_USER_ROLE__ROLE
       foreign key (ROLE_ID)
       references PUBLIC.ROLE;

    alter table PUBLIC.USER_ROLE
       add constraint FK_USER_ROLE__USER
       foreign key (USER_ID)
       references PUBLIC.USER;
