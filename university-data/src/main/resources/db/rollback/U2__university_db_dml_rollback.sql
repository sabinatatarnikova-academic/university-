DELETE
FROM groups
WHERE group_id IN ('d15b0018-47e9-4281-9dc8-a5aaf2bdf951', '99e56438-c880-41ba-87aa-080b7413ad34');

DELETE
FROM courses
WHERE course_id IN ('fd17d1ab-2c1f-46f7-8501-1a2129f0c933', 'bc5b7198-af0d-42d5-8b5b-83427cf9ba1d');

DELETE
FROM locations
WHERE location_id IN ('34af61d9-5a22-4636-bef3-c0a5e73036c6', 'abccddee-1234-5678-90ab-cdef12345678');

DELETE
FROM classes
WHERE class_id IN ('4f88890b-cf58-4ae4-aada-08f12881740f', '5b71186d-36d1-4b57-ac5b-3f1f7aa78542');

DELETE
FROM users
WHERE student_id IN ('9f6cfb74-e71e-4d00-86e8-3307e8b6865e', 'f7d448d4-0423-4a65-8576-9846a1b5e694',
                     '1f2c9f36-eae1-4d3b-8a1a-5ea47c5c7a97', 'b4a1f4e9-f24e-488e-8ffa-3fb3e64fa004', 'admin-id');

DELETE
FROM schedule_times
WHERE schedule_time_id IN ('FIRST_LECTURE', 'SECOND_LECTURE', 'THIRD_LECTURE', 'FOURTH_LECTURE', 'FIFTH_LECTURE');


