INSERT INTO PEROBOBBOT.ROLE (ID, VERSION, ROLE) VALUES (1, 0, 'ADMIN');
INSERT INTO PEROBOBBOT.ROLE (ID, VERSION, ROLE) VALUES (2, 0, 'USER');

-- initial administrator user with admin as password.
INSERT INTO PEROBOBBOT.USER (ID, VERSION, LOGIN,  LOCALE, DEACTIVATED, JWT_CLAIM, PASSWORD) VALUES (4, 0, 'admin', 'en',false, 'D@I0ccyN1x62uxSC', '$2a$10$.leTdbci87PDIEl6Y9pgc.FY2KL4Avg./7DE9f0skycs.73U3WHim');
INSERT INTO PEROBOBBOT.USER (ID, VERSION, LOGIN,  LOCALE, DEACTIVATED, JWT_CLAIM, PASSWORD) VALUES (5, 0, 'perococco', 'en',false, 'D@I0ccyN1x62uxSC', '$2a$10$.leTdbci87PDIEl6Y9pgc.FY2KL4Avg./7DE9f0skycs.73U3WHim');

INSERT INTO PEROBOBBOT.USER_ROLE (ROLE_ID, USER_ID) VALUES (1, 4);
INSERT INTO PEROBOBBOT.USER_ROLE (ROLE_ID, USER_ID) VALUES (2, 5);
