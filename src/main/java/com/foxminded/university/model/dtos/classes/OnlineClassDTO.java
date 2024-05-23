package com.foxminded.university.model.dtos.classes;

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
    private String courseId;
    private String teacherId;
    private String groupId;
    private String url;
}
