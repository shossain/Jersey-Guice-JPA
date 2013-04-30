/*
For Predefined users
*/

Insert into  users (user_id, api_key) values ('ua', 'key');

/*
For queues
*/
INSERT INTO queues (expiration_timeout_sec, queue_name, visibility_timeout_sec, user_id) VALUES ( 345600, 'testq', 3600, 'ua');
INSERT INTO queues (expiration_timeout_sec, queue_name, visibility_timeout_sec, user_id) VALUES ( 345600, 'TAS_Task_Queue_Alpha', 3600, 'ua');
INSERT INTO queues (expiration_timeout_sec, queue_name, visibility_timeout_sec, user_id) VALUES ( 345600, 'TAS_Transcode_Queue_Alpha', 3600, 'ua');