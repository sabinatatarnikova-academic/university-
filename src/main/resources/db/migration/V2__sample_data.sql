INSERT INTO groups (group_id, group_name)
VALUES ('d15b0018-47e9-4281-9dc8-a5aaf2bdf951', 'Group A'),
       ('99e56438-c880-41ba-87aa-080b7413ad34', 'Group B');

INSERT INTO users (user_id, first_name, last_name, user_type, group_id, username, password)
VALUES ('9f6cfb74-e71e-4d00-86e8-3307e8b6865e', 'John', 'Doe', 'TEACHER', null, 'john.doe',
        '$2a$12$auvXRvsX0KCioaLjD7TYSO5TRtDZSj179qOkuj6vmK/2OobS8B9k2'),
       ('f7d448d4-0423-4a65-8576-9846a1b5e694', 'Jane', 'Smith', 'TEACHER', null, 'jane.smith',
        '$2a$12$auvXRvsX0KCioaLjD7TYSO5TRtDZSj179qOkuj6vmK/2OobS8B9k2'),
       ('1f2c9f36-eae1-4d3b-8a1a-5ea47c5c7a97', 'Alice', 'Brown', 'STUDENT', 'd15b0018-47e9-4281-9dc8-a5aaf2bdf951',
        'alice.brown', '$2a$12$auvXRvsX0KCioaLjD7TYSO5TRtDZSj179qOkuj6vmK/2OobS8B9k2'),
       ('b4a1f4e9-f24e-488e-8ffa-3fb3e64fa004', 'Bob', 'Johnson', 'STUDENT', '99e56438-c880-41ba-87aa-080b7413ad34',
        'bob.johnson', '$2a$12$auvXRvsX0KCioaLjD7TYSO5TRtDZSj179qOkuj6vmK/2OobS8B9k2'),
       ('admin-id', 'Admin', 'Admin', 'ADMIN', null, 'admin',
        '$2a$12$auvXRvsX0KCioaLjD7TYSO5TRtDZSj179qOkuj6vmK/2OobS8B9k2');


INSERT INTO courses (course_id, course_name)
VALUES ('fd17d1ab-2c1f-46f7-8501-1a2129f0c933', 'Mathematics'),
       ('bc5b7198-af0d-42d5-8b5b-83427cf9ba1d', 'History');


INSERT INTO locations (location_id, department, classroom)
VALUES ('34af61d9-5a22-4636-bef3-c0a5e73036c6', 'ICS', '101'),
       ('abccddee-1234-5678-90ab-cdef12345678', 'HPF', '202');


INSERT INTO classes (class_id, start_time, end_time, course_id, user_id, group_id, class_type, url, location_id)
VALUES ('4f88890b-cf58-4ae4-aada-08f12881740f', '2023-09-01T09:00:00', '2023-09-01T10:00:00',
        'fd17d1ab-2c1f-46f7-8501-1a2129f0c933', '9f6cfb74-e71e-4d00-86e8-3307e8b6865e',
        'd15b0018-47e9-4281-9dc8-a5aaf2bdf951', 'online', 'http://example.com', NULL),
       ('5b71186d-36d1-4b57-ac5b-3f1f7aa78542', '2023-09-01T11:00:00', '2023-09-01T12:00:00',
        'bc5b7198-af0d-42d5-8b5b-83427cf9ba1d', 'f7d448d4-0423-4a65-8576-9846a1b5e694',
        '99e56438-c880-41ba-87aa-080b7413ad34', 'offline', NULL, '34af61d9-5a22-4636-bef3-c0a5e73036c6');
