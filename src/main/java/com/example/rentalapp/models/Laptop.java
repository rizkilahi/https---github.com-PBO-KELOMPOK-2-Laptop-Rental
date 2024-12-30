package com.example.rentalapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(name = "laptops", indexes = {
    @Index(name = "idx_brand_id", columnList = "brand_id"),
    @Index(name = "idx_model", columnList = "model")
})
public class Laptop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "brand_id", nullable = false)
    @NotNull(message = "Brand is required")
    private Brand brand;

    @NotBlank(message = "Model is required")
    @Column(nullable = false)
    private String model;

    @Size(min = 10, max = 500, message = "Specifications should be between 10 and 500 characters")
    @NotBlank(message = "Specifications are required")
    @Column(nullable = false)
    private String specifications;

    @NotNull(message = "Laptop status is required")
    @Enumerated(EnumType.STRING)
    private LaptopStatus status = LaptopStatus.AVAILABLE;

    public enum LaptopStatus {
        AVAILABLE("Available"),
        RENTED("Currently Rented"),
        MAINTENANCE("Under Maintenance"),
        UNAVAILABLE("Unavailable");

        private final String description;

        LaptopStatus(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    @NotNull(message = "Rental price is required")
    @Positive(message = "Rental price must be positive")
    @Column(name = "rental_price_per_day", nullable = false)
    private BigDecimal rentalPricePerDay;

    public Laptop() {
    }

    public Laptop(Brand brand, String model, String specifications, BigDecimal rentalPricePerDay) {
        this.brand = brand;
        this.model = model;
        this.specifications = specifications;
        this.status = LaptopStatus.AVAILABLE;
        this.rentalPricePerDay = rentalPricePerDay;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public LaptopStatus getStatus() {
        return status;
    }

    public void setStatus(LaptopStatus status) {
        this.status = status;
    }

    public BigDecimal getRentalPricePerDay() {
        return rentalPricePerDay;
    }

    public void setRentalPricePerDay(BigDecimal rentalPricePerDay) {
        this.rentalPricePerDay = rentalPricePerDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Laptop laptop = (Laptop) o;
        return id != null && id.equals(laptop.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Laptop{" +
                "id=" + id +
                ", brand=" + (brand != null ? brand.getName() : "null") +
                ", model='" + model + '\'' +
                ", specifications='" + specifications + '\'' +
                ", status=" + status +
                ", rentalPricePerDay=" + rentalPricePerDay +
                '}';
    }
}
