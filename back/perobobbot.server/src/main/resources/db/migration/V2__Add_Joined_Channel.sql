create table PEROBOBBOT.JOINED_CHANNEL
(
    ID           bigint       not null,
    VERSION      integer      not null,
    EXTERNAL_ID  binary       not null,
    CHANNEL_NAME varchar(255) not null,
    PLATFORM     varchar(255) not null,
    BOT_ID       bigint       not null,
    primary key (ID)
);


alter table PEROBOBBOT.JOINED_CHANNEL
    add constraint FKyon5ufvo0eaiac2m6b6lv89c
        foreign key (BOT_ID)
            references PEROBOBBOT.BOT;
