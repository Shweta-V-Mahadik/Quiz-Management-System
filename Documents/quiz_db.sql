-- Tables : 
-- 1. users 
-- 2. quizzes 
-- 3. questions 
-- 4. quiz_attempts 

--1. users table : 
CREATE TABLE users ( 
user_id INT AUTO_INCREMENT PRIMARY KEY, 
name VARCHAR(150) NOT NULL, 
email VARCHAR(150) NOT NULL UNIQUE, 
password_hash VARCHAR(255) NOT NULL, 
role ENUM('ADMIN', 'STUDENT') DEFAULT 'STUDENT', 
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
); 

--2. quizzes table : 
CREATE TABLE quizzes( 
quiz_id INT AUTO_INCREMENT PRIMARY KEY, 
title VARCHAR(255) NOT NULL, 
creator_id INT NOT NULL, 
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
FOREIGN KEY (creator_id) REFERENCES users (user_id) 
); 

--3. questions 
CREATE TABLE questions( 
question_id INT AUTO_INCREMENT PRIMARY KEY, 
quiz_id INT, 
question_text TEXT NOT NULL, 
option_a VARCHAR(500) NOT NULL, 
option_b VARCHAR(500) NOT NULL, 
option_c VARCHAR(500) NOT NULL, 
option_d VARCHAR(500) NOT NULL, 
correct_option CHAR(1) NOT NULL, 
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
FOREIGN KEY (quiz_id) REFERENCES quizzes (quiz_id) 
); 

--4. quiz_attempts table 
CREATE TABLE quiz_attempts( 
attempt_id INT AUTO_INCREMENT PRIMARY KEY, 
quiz_id INT NOT NULL, 
student_id INT NOT NULL, 
final_score INT DEFAULT  NULL,  
total_question INT, 
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
FOREIGN KEY (quiz_id) REFERENCES quizzes(quiz_id), 
FOREIGN KEY(student_id) REFERENCES users(user_id) 
);