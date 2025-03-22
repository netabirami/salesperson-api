package com.salfri.salesperson_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/sales")
public class SalesPersonController {
    @Autowired
    private SalesPersonRepository salesPersonRepository;

    public SalesPersonController() {}

    // GET all salespeople
    @GetMapping
    public Collection<SalesPerson> getAllSalespeople() {
        List<SalesPersonEntity> salesPersonEntities = salesPersonRepository.findAll();
        List<SalesPerson> salesPersons = new ArrayList<>();
        for (SalesPersonEntity salesPersonEntity : salesPersonEntities) {
            SalesPerson salesPerson = new SalesPerson(
                    salesPersonEntity.getId(),
                    salesPersonEntity.getName(),
                    salesPersonEntity.getLocation(),
                    salesPersonEntity.getRole(),
                    salesPersonEntity.getEmail(),
                    salesPersonEntity.getMobileNumber());
            salesPersons.add(salesPerson);
        }
        return salesPersons;
    }

    // GET salesperson by ID
    @GetMapping("/{id}")
    public SalesPerson getSalespersonById(@PathVariable int id) {
        SalesPersonEntity salesPersonEntity = salesPersonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SalesPerson not found with id: " + id));

        return new SalesPerson(
                salesPersonEntity.getId(),
                salesPersonEntity.getName(),
                salesPersonEntity.getLocation(),
                salesPersonEntity.getRole(),
                salesPersonEntity.getEmail(),
                salesPersonEntity.getMobileNumber());
    }

    @PostMapping("")
    public ResponseEntity <SalesPersonCreateResponse> createSalesPerson(@RequestBody SalesPersonDto salesPersonDto) {
        SalesPersonEntity salesPersonEntity = new SalesPersonEntity();
        salesPersonEntity.setName(salesPersonDto.getName());
        salesPersonEntity.setLocation(salesPersonDto.getLocation());
        salesPersonEntity.setRole(salesPersonDto.getRole());
        salesPersonEntity.setEmail(salesPersonDto.getEmail());
        salesPersonEntity.setMobileNumber(salesPersonDto.getMobileNumber());
        Integer id = salesPersonRepository.save(salesPersonEntity).getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(new SalesPersonCreateResponse(id));
    }

    @PutMapping("/{id}")
    public SalesPerson updateSalesPerson(@PathVariable int id, @RequestBody SalesPersonDto salesPersonDto) {
        // Find existing salesperson by ID
        SalesPersonEntity salesPersonEntity = salesPersonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SalesPerson not found with id: " + id));

        // Update fields
        salesPersonEntity.setName(salesPersonDto.getName());
        salesPersonEntity.setLocation(salesPersonDto.getLocation());
        salesPersonEntity.setRole(salesPersonDto.getRole());
        salesPersonEntity.setEmail(salesPersonDto.getEmail());
        salesPersonEntity.setMobileNumber(salesPersonDto.getMobileNumber());

        // Save updated entity
        salesPersonEntity = salesPersonRepository.save(salesPersonEntity);

        // Return updated response
        return new SalesPerson(
                salesPersonEntity.getId(),
                salesPersonEntity.getName(),
                salesPersonEntity.getLocation(),
                salesPersonEntity.getRole(),
                salesPersonEntity.getEmail(),
                salesPersonEntity.getMobileNumber());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSalesPerson(@PathVariable int id) {
        // Check if the salesperson exists
        SalesPersonEntity salesPersonEntity = salesPersonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SalesPerson not found with id: " + id));

        // Delete the salesperson
        salesPersonRepository.delete(salesPersonEntity);

        // Return a response with a success message
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("SalesPerson with ID " + id + " has been deleted successfully.");
    }
}



