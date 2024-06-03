package com.project.school.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeacherDto {


    private Integer idTeacher;

    @NotNull
    private UserDto user;

    @NotNull
    @Size(min = 2)
    private String name;

    @NotNull
    @Size(min = 2)
    private String lastName;

    @NotNull
    @Pattern(regexp = "\\d{10}", message = "El dni, debe de ser de 10 digitos")
    private String dni;

    @NotNull
    @Min(1)
    @Max(99)
    @NumberFormat
    private Integer age;

}
