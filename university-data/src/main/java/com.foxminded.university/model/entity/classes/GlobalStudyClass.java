package com.foxminded.university.model.entity.classes;

import com.foxminded.university.model.entity.ScheduleTimes;
import com.foxminded.university.model.entity.classes.plainclasses.StudyClass;
import com.foxminded.university.model.enums.Regularity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.DayOfWeek;
import java.util.List;

@Entity
@Table(name = "global_classes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GlobalStudyClass {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "global_class_id", nullable = false)
    private String id;

    @OneToMany(mappedBy = "globalStudyClass", cascade = {CascadeType.ALL})
    private List<StudyClass> studyClasses;

    @Enumerated(EnumType.STRING)
    @Column(name = "schedule_day")
    private DayOfWeek scheduleDay;

    @ManyToOne
    @JoinColumn(name = "schedule_time")
    private ScheduleTimes scheduleTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "regularity")
    private Regularity regularity;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    @ToString.Exclude
    private Schedule schedule;

    public void setStudyClasses(List<StudyClass> studyClasses) {
        this.studyClasses = studyClasses;
        for (StudyClass studyClass : studyClasses) {
            studyClass.setGlobalStudyClass(this);
        }
    }
}
