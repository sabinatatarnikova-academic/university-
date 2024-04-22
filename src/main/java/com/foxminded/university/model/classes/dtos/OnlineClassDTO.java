package com.foxminded.university.model.classes.dtos;

import com.foxminded.university.model.Course;
import com.foxminded.university.model.Group;
import com.foxminded.university.model.users.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OnlineClassDTO {
    private String id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Course course;
    private User teacher;
    private Group group;
    private String url;
}
