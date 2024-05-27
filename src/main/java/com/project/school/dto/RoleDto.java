package com.project.school.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDto {

    private Integer idRole;

    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "[A-Za-z]+")
    private String name;

    @NotNull
    private Boolean enabled;

}
