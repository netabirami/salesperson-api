package com.salfri.salesperson_api;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
public class SalesPersonDto {
    private final int id;

    @NotBlank(message = "Name cannot be Null")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must contain only letters and spaces")
    private final String name;

    @NotBlank(message = "Location cannot be Null")
    @Size(min = 2, max = 50, message = "Location must be between 2 and 50 characters")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\- ]+$",
            message = "Location must contain only letters, spaces, or hyphens"
    )
    private final String location;

    @NotBlank(message = "Role cannot be null or empty")
    @Size(min = 3, max = 50, message = "Role must be between 3 and 50 characters")
    @Pattern(regexp = "^[A-Za-z][A-Za-z\\s\\-']{2,49}$",
            message = "Role must start with a letter and contain only letters, spaces, hyphens, or apostrophes")
    private final String role;

    @NotBlank(message = "Email not be null")
    @Size (min = 10,max = 100, message = "Not longer than 100 chars and minimum 10" )
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Should match")
    private final String email;

    @NotBlank(message = "Mobile number cannot be null or empty")
    @Pattern(regexp = "^(\\+?\\d{10,15})$", message =
            "Mobile number must contain only digits and can optionally " +
                    "start with a '+'; length must be between 10 and 15 digits")
    private final String mobileNumber;

    @Min(value = 0, message = "Total sales count cannot be negative")
    private final int totalSalesCount;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private final LocalDate joiningDate;
    private final Status status;
    protected final BigDecimal totalRevenue;
    private final String departmentName;
    private final String designation;
    private final Integer performanceRating;

    @Builder.Default
    @NotNull(message = "Gender cannot be null")
    private final Gender gender;

    @NotNull(message = "Address must not be null.")
    @Size(min = 10, max = 255, message = "Address must be between 10 and 255 characters.")
    private final  String address;
    private final String photoUrl;

    public SalesPersonDto(int id, String name, String location, String role, String email, String mobileNumber, int totalSalesCount, LocalDate joiningDate, Status status, BigDecimal totalRevenue, String departmentName, String designation, Integer performanceRating, Gender gender, String address, String photoUrl) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.role = role;
        this.email = email;
        this.mobileNumber = mobileNumber;
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
