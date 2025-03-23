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
    private final String mobileNumber ;



    public String getRole() {
        return role;
    }

    public SalesPerson(int id, String name, String location, String role, String email, String mobileNumber) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.role = role;
        this.email = email;
        this.mobileNumber= mobileNumber;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getEmail()
    {return email;
    }

    public String getMobileNumber()
    {return mobileNumber;}
}
