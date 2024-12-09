-- V2__insert_test_data.sql

INSERT INTO candidate (name, email, gender, expected_salary)
VALUES ('John Doe', 'john.doe@example.com', 'Male', 50000),
       ('Jane Smith', 'jane.smith@example.com', 'Female', 60000),
       ('Alice Johnson', 'alice.johnson@example.com', 'Female', 70000),
       ('Bob Brown', 'bob.brown@example.com', 'Male', 80000);

INSERT INTO user (username, password, enabled, name)
VALUES ('admin', '$2a$10$XW9TKZKQqrsmVKokfMmAZek2yQlsi6YQugRopGo9/5Z2kFLlS5atq', 1, 'admin name'),
       ('user', '$2a$10$XW9TKZKQqrsmVKokfMmAZek2yQlsi6YQugRopGo9/5Z2kFLlS5atq', 1, 'user name');
