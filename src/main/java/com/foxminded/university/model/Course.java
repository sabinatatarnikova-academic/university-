package com.foxminded.university.model;

import com.foxminded.university.model.classes.StudyClass;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {
    @Id
    @Column (name = "course_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "course_name")
    private String name;

    @OneToMany (mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudyClass> studyStudyClasses;

    public Course(String courseName, List<StudyClass> studyClass) {
        this.name = courseName;
        this.studyStudyClasses = studyClass;
    }
}
