package com.project.school.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentDto {

    private Integer idStudent;

    @NotNull
    @NotEmpty
    @Size(min = 2)
    @Pattern(regexp = "[A-Za-z]+")
    private String name;

    @NotNull
    private UserDto user;

    @NotNull
    @NotEmpty
    @Size(min = 2)
    @Pattern(regexp = "[A-Za-z]+")
    private String lastName;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "\\d{10}")
    private String dni;

    @NotNull
    @Min(value = 0)
    @PositiveOrZero
    private Integer age;
}
