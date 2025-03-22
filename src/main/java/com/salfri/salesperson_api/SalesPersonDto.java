package com.salfri.salesperson_api;

import lombok.Getter;

@Getter
public class SalesPersonDto {
    private final int id;
    private final String name;
    private final String location;
    private final String role;
    private final String email;
    private final String mobileNumber;

    public SalesPersonDto(int id, String name, String location, String role, String email, String mobileNumber) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.role = role;
        this.email = email;
        this.mobileNumber = mobileNumber;
    }

}
