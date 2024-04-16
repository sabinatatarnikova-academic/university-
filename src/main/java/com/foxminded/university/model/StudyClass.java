package com.foxminded.university.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "classes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyClass {
    @Id
    @Column (name = "class_id", insertable=false, updatable=false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column (name = "start_time")
    private LocalDateTime startTime;

    @Column (name = "end_time")
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
}
