CREATE TYPE user_type AS ENUM ('TEACHER', 'STUDENT');

CREATE TABLE users
(
    user_id    VARCHAR(50) PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL,
    user_type  user_type,
    group_id   VARCHAR(50)
);

CREATE TABLE groups
(
    group_id   VARCHAR(50) PRIMARY KEY,
    group_name VARCHAR(50),
    student_id VARCHAR(50),
    FOREIGN KEY (group_id) REFERENCES users (user_id) ON DELETE CASCADE
);

CREATE TABLE locations
(
    location_id   VARCHAR(50) PRIMARY KEY,
    department    VARCHAR(50)  NOT NULL,
    classroom     VARCHAR(50) NOT NULL
);

CREATE TABLE classes
(
    class_id    VARCHAR(50) PRIMARY KEY,
    course_name VARCHAR(50) NOT NULL,
    start_time  TIME,
    end_time    TIME,
    location_id VARCHAR(50) NOT NULL,
    FOREIGN KEY (location_id) REFERENCES locations (location_id) ON DELETE CASCADE,
    teacher_id  VARCHAR(50) NOT NULL,
    FOREIGN KEY (teacher_id) REFERENCES users (user_id) ON DELETE CASCADE,
    group_id    VARCHAR(50) NOT NULL,
    FOREIGN KEY (group_id) REFERENCES groups (group_id) ON DELETE CASCADE
);

CREATE TABLE courses
(
    course_id   VARCHAR(50) PRIMARY KEY,
    course_name VARCHAR(50) NOT NULL
);

ALTER TABLE courses ADD class_id VARCHAR(50);
ALTER TABLE classes ADD course_id VARCHAR(50);

ALTER TABLE courses ADD CONSTRAINT fk_class_id FOREIGN KEY (class_id) REFERENCES classes (class_id) ON DELETE CASCADE;
ALTER TABLE classes ADD CONSTRAINT fk_course_id FOREIGN KEY (course_id) REFERENCES courses (course_id) ON DELETE CASCADE;
