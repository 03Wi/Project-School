package com.project.school.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDto {

    private Integer idCourse;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "[A-Za-z]+")
    private String name;

    @NotNull
    @NotEmpty
    private String acronyms;

    @NotNull
    @NotEmpty
    @Size(min = 4, max = 250)
    @Pattern(regexp = "[A-Za-z]+")
    private String description;

    @NotNull
    @NotEmpty
    @Size(min = 2)
    @Pattern(regexp = "[A-Za-z]+")
    private String teacher;

    @NotNull
    @NotEmpty
    private String classroom;

    @NotNull
    private boolean enabled;
}
