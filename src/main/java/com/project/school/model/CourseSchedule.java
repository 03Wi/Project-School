package com.project.school.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@IdClass(CourseSchedulePK.class)
public class CourseSchedule {

    @Id
    @EqualsAndHashCode.Include
    private Course course;

    @Id
    @EqualsAndHashCode.Include
    private Schedule schedule;

    @Column(nullable = false)
    private String dayOfWeek;

    @Column(nullable = false)
    private boolean classroomAvailable;
}
