package com.salfri.salesperson_api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesPerson {
    private final int id;
    private final String name;
    private final String location;
    private final String role;
    private final String email;


    public SalesPerson(int id, String name, String location, String role, String email) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.role = role;
        this.email = email;
    }

}
