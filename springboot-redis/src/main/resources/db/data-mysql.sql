DELETE FROM user;

INSERT INTO user (id, name, age, email, create_time, modify_time, is_deleted) VALUES
(1, 'Jone', 18, 'test1@baomidou.com' , now(), now() , 0),
(2, 'Jack', 20, 'test2@baomidou.com' , now(), now() , 0),
(3, 'Tom', 28, 'test3@baomidou.com' , now(), now() , 0),
(4, 'Sandy', 21, 'test4@baomidou.com' , now(), now() , 0),
(5, 'Billie', 24, 'test5@baomidou.com' , now(), now() , 0);
