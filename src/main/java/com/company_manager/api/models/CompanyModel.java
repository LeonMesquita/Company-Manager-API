package com.company_manager.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "companies")
public class CompanyModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    @JoinColumn(name = "addressId", nullable = false, referencedColumnName = "id")
    private AddressModel address;

    @Column(nullable = false, length = 80)
    private String fantasyName;

    @Column(nullable = false, length = 80)
    private String companyName;

    @Column(nullable = false, length = 14)
    private String cnpj;

    @Column(nullable = false, length = 40)
    private String email;

    @Column(length = 200)
    private String observation;

    @OneToMany(mappedBy = "company")
    @JsonIgnore
    private List<VehicleModel> vehicles;

}
