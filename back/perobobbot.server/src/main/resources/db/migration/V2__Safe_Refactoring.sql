alter table PEROBOBBOT.SAFE drop column CREDIT;
alter table PEROBOBBOT.SAFE drop column POINT_TYPE;

create table PEROBOBBOT.SAFE_CREDIT (
                                        ID bigint not null,
                                        CREDIT bigint not null,
                                        POINT_TYPE varchar(255) not null,
                                        primary key (ID, POINT_TYPE)
);

alter table PEROBOBBOT.TRANSACTION ADD COLUMN IF NOT EXISTS TYPE varchar(255) not null default 'credit';

alter table PEROBOBBOT.SAFE_CREDIT
    add constraint FKlwshea43pxk96dcefspxwmnyk
        foreign key (ID)
            references PEROBOBBOT.SAFE;


