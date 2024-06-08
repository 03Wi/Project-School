-- Insertar en la tabla Role
INSERT INTO role (enabled, name, id_role) values (true, 'ADMIN' , 1);
INSERT INTO role (enabled, name, id_role) values (true, 'USER' , 2);

INSERT INTO user_type (enabled, id_role, user_name, password) values (true, 1, 'ADMIN', '$2a$12$xC3vWCBhDdF9MkRWaocUjOgKyRr5L418xsQqgLE8WAjfSsTded.yu');
INSERT INTO user_type (enabled, id_role, user_name, password) values (true, 2, 'USER', '$2a$12$QnwkKaD2y3Tsf.mntOdSYO6UzZEL0lUTWJEGHdMFTI.WVHc.Uhdoq') ;
