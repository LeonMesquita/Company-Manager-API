package com.company_manager.api.repositories;

import com.company_manager.api.models.CompanyModel;
import com.company_manager.api.models.VehicleModel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleModel, Long> {

    boolean existsByPlate(String plate);

    Optional<VehicleModel> findByPlate(String plate);

    Optional<VehicleModel> findByCompany(CompanyModel company);


}
