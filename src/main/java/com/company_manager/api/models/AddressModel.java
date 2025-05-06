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
@Table(name = "addresses")
public class AddressModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, length = 80)
    private String street;

    @Column(nullable = false, length = 50)
    private String complement;

    @Column(nullable = false, length = 40)
    private String district;

    @Column(nullable = false, length = 10)
    private String number;

    @Column(nullable = false, length = 2)
    private String uf;

}
