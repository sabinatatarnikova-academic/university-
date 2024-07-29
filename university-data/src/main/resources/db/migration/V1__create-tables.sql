-- Users table for both Teacher and Student
CREATE TABLE groups (
                        group_id VARCHAR(255) PRIMARY KEY,
                        group_name VARCHAR(255)
);

-- Courses table
CREATE TABLE users (
                       user_id VARCHAR(255) PRIMARY KEY,
                       first_name VARCHAR(50),
                       last_name VARCHAR(50),
                       user_type VARCHAR(255),
                       group_id VARCHAR(255) REFERENCES groups (group_id) ON DELETE SET NULL, -- Only relevant for students
                       username VARCHAR(255),
                       password VARCHAR(255)
);

-- Groups table
CREATE TABLE courses (
                         course_id VARCHAR(255) PRIMARY KEY,
                         course_name VARCHAR(255)
);

-- Global Classes table
CREATE TABLE global_classes
(
    global_class_id VARCHAR(255) PRIMARY KEY,
    group_id        VARCHAR(255) REFERENCES groups (group_id) ON DELETE SET NULL,
    schedule_day    VARCHAR(50),
    schedule_time   VARCHAR(50),
    regularity      VARCHAR(50),
    start_date      DATE,
    end_date        DATE
);

-- Study Classes table for both OnlineClass and OfflineClass
CREATE TABLE classes (
                         class_id VARCHAR(255) PRIMARY KEY,
                         start_time TIMESTAMP,
                         end_time TIMESTAMP,
                         course_id VARCHAR(255) REFERENCES courses (course_id) ON DELETE SET NULL,
                         user_id         VARCHAR(255) REFERENCES users (user_id) ON DELETE SET NULL,
                         group_id        VARCHAR(255) REFERENCES groups (group_id) ON DELETE SET NULL,
                         global_class_id VARCHAR(255) REFERENCES global_classes (global_class_id) ON DELETE SET NULL, -- New foreign key
                         class_type VARCHAR(255),
                         url             VARCHAR(255),
                         location_id VARCHAR(255) -- Only relevant for offline classes
);

CREATE TABLE schedule
(
    schedule_id VARCHAR(255) PRIMARY KEY,
    group_id    VARCHAR(255) REFERENCES groups (group_id) ON DELETE SET NULL,
    start_date  DATE,
    end_date    DATE
);

-- Location table for OfflineClass
CREATE TABLE locations (
                           location_id VARCHAR(255) PRIMARY KEY,
                           department VARCHAR(50),
                           classroom VARCHAR(50)
);

-- Foreign key constraints for classes table
ALTER TABLE classes
    ADD CONSTRAINT fk_course
        FOREIGN KEY (course_id)
            REFERENCES courses (course_id) ON DELETE SET NULL;

ALTER TABLE classes
    ADD CONSTRAINT fk_user
        FOREIGN KEY (user_id)
            REFERENCES users (user_id) ON DELETE SET NULL;

ALTER TABLE classes
    ADD CONSTRAINT fk_group
        FOREIGN KEY (group_id)
            REFERENCES groups (group_id) ON DELETE SET NULL;

ALTER TABLE classes
    ADD CONSTRAINT fk_location
        FOREIGN KEY (location_id)
            REFERENCES locations (location_id) ON DELETE SET NULL;

ALTER TABLE classes
    ADD CONSTRAINT fk_global_class
        FOREIGN KEY (global_class_id)
            REFERENCES global_classes (global_class_id) ON DELETE SET NULL;

ALTER TABLE global_classes
    ADD schedule_id VARCHAR(255),
    ADD CONSTRAINT fk_schedule
        FOREIGN KEY (schedule_id)
            REFERENCES schedule (schedule_id) ON DELETE SET NULL;

