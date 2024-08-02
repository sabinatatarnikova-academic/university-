CREATE TABLE schedule_times
(
    schedule_time_id   VARCHAR(255) PRIMARY KEY,
    schedule_time_name VARCHAR(50),
    start_time         TIME,
    end_time           TIME
);

INSERT INTO schedule_times (schedule_time_id, schedule_time_name, start_time, end_time)
VALUES ('FIRST_LECTURE', 'First Lecture', '08:00:00', '09:30:00'),
       ('SECOND_LECTURE', 'Second Lecture', '09:50:00', '11:20:00'),
       ('THIRD_LECTURE', 'Third Lecture', '11:40:00', '13:10:00'),
       ('FOURTH_LECTURE', 'Fourth Lecture', '13:30:00', '15:00:00'),
       ('FIFTH_LECTURE', 'Fifth Lecture', '15:20:00', '16:50:00');
