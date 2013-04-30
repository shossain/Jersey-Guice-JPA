ALTER TABLE queues DROP FOREIGN KEY FK_queues_user_id;
ALTER TABLE queues DROP FOREIGN KEY UNQ_queues_0;
ALTER TABLE messages DROP FOREIGN KEY FK_messages_queue_id;
DROP TABLE users;
DROP TABLE queues;
DROP TABLE messages;
