package com.project.school.dto;


import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class CourseScheduleDto {

    @Id
    @NotNull
    private CourseDto course;

    @Id
    @NotNull
    private ScheduleDto schedule;

    @NotNull
    @Pattern(regexp = "[A-Za-z]+")
    private String dayOfWeek;

    @NotNull
    private boolean classroomAvailable;

}
