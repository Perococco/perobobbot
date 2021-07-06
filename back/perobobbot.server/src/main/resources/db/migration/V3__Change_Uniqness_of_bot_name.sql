
alter table PEROBOBBOT.BOT
    drop constraint UK_ievgedbgan6tu2f1ge01g5bit;

alter table PEROBOBBOT.BOT
    add constraint UKtb35df8l26y2awkllnmjp6mfa unique (USER_ID, NAME);
