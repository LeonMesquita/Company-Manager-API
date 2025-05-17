package com.company_manager.api.repositories;

import com.company_manager.api.models.CompanyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CompanyRepository extends JpaRepository<CompanyModel, Long> {
    boolean existsByCnpj(String cnpj);
    boolean existsByCompanyName(String cnpj);
    boolean existsByFantasyName(String cnpj);

    CompanyModel findByCnpj(String cnpj);
    CompanyModel findByCompanyName(String companyName);
    CompanyModel findByFantasyName(String fantasyName);


}
