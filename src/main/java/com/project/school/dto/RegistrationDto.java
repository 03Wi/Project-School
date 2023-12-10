package com.project.school.dto;

import com.fasterxml.jackson.annotation.*;
import com.project.school.model.Student;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class RegistrationDto {

    private Integer idRegistration;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dateRegistration = LocalDateTime.now();

    @NotNull
    private StudentDto student;

    @NotNull
    @JsonManagedReference
    private List<DetailRegistrationDto> detailRegistration;

    @NotNull
    private boolean enabled;
}
