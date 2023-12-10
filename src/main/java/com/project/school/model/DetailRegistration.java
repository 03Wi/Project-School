package com.project.school.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
public class DetailRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private Integer idDetailRegistration;

    @ManyToOne
    @JoinColumn(name = "idRegistration", nullable = false, foreignKey = @ForeignKey(name = "FK_DETAIL-REGISTRATION_REGISTRATION"))
    private Registration registration;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCourse", foreignKey = @ForeignKey(name = "FK_DETAILREGISTRATION_COURSE"))
    private Course course;

    @Column(nullable = false, length = 10)
    private String classroom;

}
