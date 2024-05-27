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
@Table(name = "userType")
@Entity
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idUser;

    @ManyToOne
    @JoinColumn(name = "idRole", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_ROLE"))
    private Role role;

    @Column(nullable = false, length = 20, unique = true)
    private String userName;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(nullable = false)
    private Boolean enabled;

}
