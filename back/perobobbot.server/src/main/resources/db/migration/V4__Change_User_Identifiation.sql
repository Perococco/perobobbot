alter table PEROBOBBOT.USER add column OPENID_PLATFORM varchar(255) null;
alter table PEROBOBBOT.USER add column IDENTIFICATION_MODE varchar(255) not null default 'password';
alter table PEROBOBBOT.USER alter column PASSWORD varchar(255) null;