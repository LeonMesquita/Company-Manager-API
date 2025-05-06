package com.company_manager.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vehicles")
public class VehicleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "companyId", nullable = false)
    private CompanyModel company;

    @Column(nullable = false, length = 35)
    private String vehicleDescription;

    @Column(nullable = false, length = 7)
    private String plate;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer companyCode;

}
