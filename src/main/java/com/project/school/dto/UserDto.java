package com.project.school.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class UserDto {

    private Integer idUser;

    @NotNull
    @JsonIncludeProperties("idRole")
    private RoleDto role;

    @NotNull
    @Size(min = 3)
    @Pattern(regexp = "[A-Za-z]+")
    private String userName;

    @NotNull
    @Size(min = 40, max = 60)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull
    private Boolean enabled;

}
