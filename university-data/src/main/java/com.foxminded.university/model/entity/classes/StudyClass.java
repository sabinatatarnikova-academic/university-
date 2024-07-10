package com.foxminded.university.model.entity.classes;

import com.foxminded.university.model.entity.Course;
import com.foxminded.university.model.entity.Group;
import com.foxminded.university.model.entity.users.Teacher;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@Entity
@Table(name = "classes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "class_type", discriminatorType = DiscriminatorType.STRING)
public class StudyClass {

    @Id
    @Column (name = "class_id", insertable=false, updatable=false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column (name = "start_time")
    private ZonedDateTime startTime;

    @Column (name = "end_time")
    private ZonedDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "class_type", insertable = false, updatable = false)
    private String classType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

/*    public ZonedDateTime getStartTime() {
        return startTime != null ? startTime.withZoneSameInstant(ZoneId.of("Europe/Kiev")) : null;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime != null ? startTime.withZoneSameInstant(ZoneId.of("Europe/Kiev")) : null;
    }

    public ZonedDateTime getEndTime() {
        return endTime != null ? endTime.withZoneSameInstant(ZoneId.of("Europe/Kiev")) : null;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime != null ? endTime.withZoneSameInstant(ZoneId.of("Europe/Kiev")) : null;
    }*/
}
