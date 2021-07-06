alter table PEROBOBBOT.JOINED_CHANNEL
    add constraint UK80k9rdha3crr2fk8xac6miu1e unique (BOT_ID, VIEWER_IDENTITY_ID, CHANNEL_NAME);

