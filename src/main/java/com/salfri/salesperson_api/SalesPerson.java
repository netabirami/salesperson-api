package com.salfri.salesperson_api;

public class SalesPerson {
    private final int id;
    private final String name;
    private final String location;
    private final String role;
    private final String email;


    public String getRole() {
        return role;
    }

    public SalesPerson(int id, String name, String location, String role, String email) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.role = role;
        this.email = email;
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

    public String getEmail() {return email;
    }
}
