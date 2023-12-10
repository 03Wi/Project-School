package com.project.school.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private Integer idRegistration;

    private LocalDateTime dateRegistration = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "idStudent", foreignKey = @ForeignKey(name = "FK_REGISTRATION_STUDENT"))
    private Student student;

    @OneToMany(mappedBy = "registration", cascade = CascadeType.ALL)
    private List<DetailRegistration> detailRegistration;

    @Column(nullable = false)
    private boolean enabled;

}
