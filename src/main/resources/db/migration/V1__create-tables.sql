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
                       group_id     VARCHAR(255) REFERENCES groups (group_id), -- Only relevant for students
                       username     VARCHAR(255),
                       password     VARCHAR(255)
);

-- Groups table
CREATE TABLE courses (
                         course_id VARCHAR(255) PRIMARY KEY,
                         course_name VARCHAR(255)
);

-- Study Classes table for both OnlineClass and OfflineClass
CREATE TABLE classes (
                         class_id VARCHAR(255) PRIMARY KEY,
                         start_time TIMESTAMP,
                         end_time TIMESTAMP,
                         course_id VARCHAR(255) REFERENCES courses (course_id),
                         user_id VARCHAR(255) REFERENCES users (user_id),
                         group_id VARCHAR(255) REFERENCES groups (group_id),
                         class_type VARCHAR(255),
                         url VARCHAR(255), -- Only relevant for online classes
                         location_id VARCHAR(255) -- Only relevant for offline classes
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
            REFERENCES courses (course_id);

ALTER TABLE classes
    ADD CONSTRAINT fk_user
        FOREIGN KEY (user_id)
            REFERENCES users (user_id);

ALTER TABLE classes
    ADD CONSTRAINT fk_group
        FOREIGN KEY (group_id)
            REFERENCES groups (group_id);

ALTER TABLE classes
    ADD CONSTRAINT fk_location
        FOREIGN KEY (location_id)
            REFERENCES locations (location_id);
