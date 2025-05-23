package com.company_manager.api.controllers;

import com.company_manager.api.dtos.CompanyDTO;
import com.company_manager.api.dtos.CompanyWithVehiclesResponseDTO;
import com.company_manager.api.models.CompanyModel;
import com.company_manager.api.services.CompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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


    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    public ResponseEntity<Page<CompanyModel>> getAllCompanies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Page<CompanyModel> companies = companyService.findAll(PageRequest.of(page, size, Sort.by(sortBy)));
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
    public ResponseEntity<Page<CompanyModel>> searchCompanyByPartialCnpj(
            @RequestParam(value = "fantasy_name", required = false, defaultValue = "") String fantasyName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy

    ) {

        Page<CompanyModel> companies = companyService.findByPartialFantasyName(fantasyName, PageRequest.of(page, size, Sort.by(sortBy)));
        return ResponseEntity.status(HttpStatus.OK).body(companies);
    }

    @GetMapping("/cnpj/{cnpj}")
    public ResponseEntity<CompanyModel> getCompanyByCnpj(@PathVariable String cnpj) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.findByCnpj(cnpj));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}/vehicles")
    public ResponseEntity<CompanyWithVehiclesResponseDTO> getCompanyWithVehicles(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.getCompanyWithVehicles(id));
    }
}
