package com.company_manager.api.repositories;

import com.company_manager.api.models.AddressModel;
import com.company_manager.api.models.CompanyModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CompanyRepository extends JpaRepository<CompanyModel, Long> {
    boolean existsByCnpj(String cnpj);
    boolean existsByCompanyName(String cnpj);
    boolean existsByFantasyName(String cnpj);
    boolean existsByAddress(AddressModel addressModel);

    Optional<CompanyModel> findByCnpj(String cnpj);
    CompanyModel findByCompanyName(String companyName);
    CompanyModel findByFantasyName(String fantasyName);

    Page<CompanyModel> findByFantasyNameContainingIgnoreCase(String fantasyName, Pageable pageable);

    @EntityGraph(attributePaths = {"vehicles"})
    Optional<CompanyModel> findWithVehiclesById(Long id);


}
