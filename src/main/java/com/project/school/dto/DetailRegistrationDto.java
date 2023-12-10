package com.project.school.dto;

import com.fasterxml.jackson.annotation.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetailRegistrationDto {

    private Integer idDetailRegistration;

    @NotNull
    @JsonBackReference
    private RegistrationDto registration;

    @NotNull
    private CourseDto course;

    @NotNull
    private String classroom;

}
