package com.salfri.salesperson_api;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
public class SalesPerson {
    private final int id;
    private final String name;
    private final String location;
    private final String role;
    private final String email;
    private final String mobileNumber ;
    private final int totalSalesCount;
    private final LocalDate joiningDate;
    private final Status status;
    private final BigDecimal totalRevenue;
    private final String departmentName;
    private final String designation;
    private final Integer performanceRating;
    private final Gender gender;
    private final String address;
    private final String photoUrl;


    public SalesPerson(int id, String name, String location, String role, String email, String mobileNumber, int totalSalesCount, LocalDate joiningDate, Status status, BigDecimal totalRevenue, String departmentName, String designation, Integer performanceRating, Gender gender, String address, String photoUrl) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.role = role;
        this.email = email;
        this.mobileNumber= mobileNumber;
        this.totalSalesCount = totalSalesCount;
        this.joiningDate = joiningDate;
        this.status = status;
        this.totalRevenue = totalRevenue;
        this.departmentName = departmentName;
        this.designation = designation;
        this.performanceRating = performanceRating;
        this.gender = gender;
        this.address = address;
        this.photoUrl = photoUrl;
    }

}


