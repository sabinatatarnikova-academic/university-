package com.foxminded.university.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Table(name = "schedule_times")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleTimes {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "schedule_time_id")
    private String id;

    @Column(name = "schedule_time_name")
    private String name;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;
}
