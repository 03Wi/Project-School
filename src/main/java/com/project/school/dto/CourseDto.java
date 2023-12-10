package com.project.school.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    private String name;

    @NotNull
    @NotEmpty
    private String acronyms;

    @NotNull
    private boolean enabled;
}
