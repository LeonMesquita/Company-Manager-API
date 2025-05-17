package com.company_manager.api.controllers;

import com.company_manager.api.dtos.CompanyDTO;
import com.company_manager.api.models.CompanyModel;
import com.company_manager.api.services.CompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    CompanyService companyService;



    @GetMapping
    public ResponseEntity<List<CompanyModel>> getAllCompanies() {
        List<CompanyModel> companies = companyService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(companies);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CompanyModel> createCompany(@RequestBody @Valid CompanyDTO body) {
        CompanyModel company = companyService.save(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(company);
    }


    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/search")
    public ResponseEntity<List<CompanyModel>> searchCompanyByPartialCnpj(@RequestParam("fantasy_name") String fantasyName) {
        List<CompanyModel> companies = companyService.findByPartialFantasyName(fantasyName);
        return ResponseEntity.status(HttpStatus.OK).body(companies);
    }

    @GetMapping("/cnpj/{cnpj}")
    public ResponseEntity<CompanyModel> getCompanyByCnpj(@PathVariable String cnpj) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.findByCnpj(cnpj));
    }
}
