package com.salfri.salesperson_api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesPersonDto {
    private final int id;
    private final String name;
    private final String location;
    private final String role;
    private final String email;
    private final String mobileNumber;
    private final int totalSalesCount;

    public SalesPersonDto(int id, String name, String location, String role, String email, String mobileNumber, int totalSalesCount) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.role = role;
        this.email = email;
        this.mobileNumber = mobileNumber;
       this.totalSalesCount = totalSalesCount;
    }

}
