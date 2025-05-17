package com.company_manager.api.repositories;

import com.company_manager.api.models.AddressModel;
import com.company_manager.api.models.CompanyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CompanyRepository extends JpaRepository<CompanyModel, Long> {
    boolean existsByCnpj(String cnpj);
    boolean existsByCompanyName(String cnpj);
    boolean existsByFantasyName(String cnpj);
    boolean existsByAddress(AddressModel addressModel);

    CompanyModel findByCnpj(String cnpj);
    CompanyModel findByCompanyName(String companyName);
    CompanyModel findByFantasyName(String fantasyName);

    List<CompanyModel> findByCnpjContaining(String partialCnpj);


}
