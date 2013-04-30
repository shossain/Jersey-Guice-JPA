CREATE DATABASE tasqsdb;
USE tasqsdb;
CREATE TABLE users (user_id VARCHAR(255) NOT NULL, api_key VARCHAR(255) NOT NULL UNIQUE, PRIMARY KEY (user_id));
CREATE TABLE queues (queue_id BIGINT AUTO_INCREMENT NOT NULL, expiration_timeout_sec BIGINT NOT NULL, queue_name VARCHAR(255) NOT NULL, visibility_timeout_sec BIGINT NOT NULL, user_id VARCHAR(255) NOT NULL, PRIMARY KEY (queue_id));
CREATE TABLE messages (message_id VARCHAR(255) NOT NULL, creation_time_sec BIGINT NOT NULL, message_text LONGTEXT, visibility_timeout_sec BIGINT NOT NULL, queue_id BIGINT NOT NULL, PRIMARY KEY (message_id));
ALTER TABLE queues ADD CONSTRAINT UNQ_queues_0 UNIQUE (queue_name, user_id);
ALTER TABLE queues ADD CONSTRAINT FK_queues_user_id FOREIGN KEY (user_id) REFERENCES users (user_id);
ALTER TABLE messages ADD CONSTRAINT FK_messages_queue_id FOREIGN KEY (queue_id) REFERENCES queues (queue_id);
