package com.ort.edu.proyectofinal.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import jakarta.persistence.Table;

@Entity
@Table(name = "customerrestriction", schema = "proyectofinal")
public class Customerrestriction {
    @EmbeddedId
    private CustomerrestrictionId id;

    @MapsId("customerId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "CustomerId", nullable = false)
    private Customer customer;

    @MapsId("restrictionId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "RestrictionId", nullable = false)
    private Restriction restriction;

    public CustomerrestrictionId getId() {
        return id;
    }

    public void setId(CustomerrestrictionId id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Restriction getRestriction() {
        return restriction;
    }

    public void setRestriction(Restriction restriction) {
        this.restriction = restriction;
    }

}