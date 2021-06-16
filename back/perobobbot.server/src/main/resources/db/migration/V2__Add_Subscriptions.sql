create table PEROBOBBOT.SUBSCRIPTION
(
    ID              bigint       not null,
    VERSION         integer      not null,
    EXTERNAL_ID     binary       not null,
    CONDITION       varchar(255) not null,
    SUBSCRIPTION_ID varchar(255) not null,
    TYPE            varchar(255) not null,
    primary key (ID)
);


create table PEROBOBBOT.USER_SUBSCRIPTION
(
    ID              bigint  not null,
    VERSION         integer not null,
    OWNER_ID        bigint  not null,
    SUBSCRIPTION_ID bigint  not null,
    primary key (ID)
);


alter table PEROBOBBOT.SUBSCRIPTION
    add constraint UK_43e6xf2pp8f3bv9lk0jjj866b unique (SUBSCRIPTION_ID);

alter table PEROBOBBOT.USER_SUBSCRIPTION
    add constraint FK72ee1pms1am8o9g8dpm3mi3s5
        foreign key (OWNER_ID)
            references PEROBOBBOT.USER;

alter table PEROBOBBOT.USER_SUBSCRIPTION
    add constraint FKlbgrt8y5kai2dkm9gmbtp61ym
        foreign key (SUBSCRIPTION_ID)
            references PEROBOBBOT.SUBSCRIPTION;
