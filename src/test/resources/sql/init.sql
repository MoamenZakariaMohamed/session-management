INSERT INTO users (id,name, mobile_number, email, password)
VALUES (99,'userName', '01001237892', 'user@gmail.com', 'testuser'),
       (100,'dummy','01001237891','dummyUser@hotmail.com','$2a$10$5hIi5nlUkGe/i/CgHIo.KeWv4pJPEPSPL6luDrnubQClDR85orJze');
INSERT INTO tracker (id,user_id, date, login_time, logout_time)
VALUES
    (100,99, '2024-08-01', '08:00:00', '17:00:00');
