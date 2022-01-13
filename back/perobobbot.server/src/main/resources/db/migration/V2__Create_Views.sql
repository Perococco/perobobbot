drop view if exists PEROBOBBOT.PLATFORM_USER_VIEW;
drop view if exists PEROBOBBOT.USER_TOKEN_VIEW;
drop view if exists PEROBOBBOT.SAFE_VIEW;


create view PEROBOBBOT.PLATFORM_USER_VIEW (ID,PLATFORM,USER_ID,LOGIN) as
select pu.ID, pu.PLATFORM, pu.USER_ID, (case
                                            when pu.PLATFORM = 'Discord' then pu.DISCORD_LOGIN
                                            when pu.PLATFORM = 'Twitch' then pu.TWITCH_LOGIN
                                            else 'Unknown'
    end
    ) from PEROBOBBOT.PLATFORM_USER as pu;



create view PEROBOBBOT.USER_TOKEN_VIEW (PLATFORM, LOGIN, PLATFORM_USER_ID, PLATFORM_LOGIN, SCOPES)
as select puv.PLATFORM,
          u.LOGIN,
          puv.USER_ID,
          puv.LOGIN,
          ut.SCOPES
   from PEROBOBBOT.PLATFORM_USER_VIEW as puv
            left join PEROBOBBOT.USER_TOKEN as ut on puv.ID = ut.PLATFORM_USER_ID
            left join PEROBOBBOT.USER U on U.ID = ut.USER_ID;

create view PEROBOBBOT.SAFE_VIEW
as select s.ID, puv.PLATFORM, puv.LOGIN, sc.POINT_TYPE, sc.CREDIT
   from PEROBOBBOT.SAFE as s
            join PEROBOBBOT.SAFE_CREDIT as sc on s.ID = sc.ID
            join PEROBOBBOT.PLATFORM_USER_VIEW as puv on s.PLATFORM_USER_ID = puv.ID;