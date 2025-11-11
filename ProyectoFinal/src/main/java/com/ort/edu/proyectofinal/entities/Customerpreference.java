package com.ort.edu.proyectofinal.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import jakarta.persistence.Table;

@Entity
@Table(name = "customerpreference", schema = "proyectofinal")
public class Customerpreference {
    @EmbeddedId
    private CustomerpreferenceId id;

    @MapsId("customerId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "CustomerId", nullable = false)
    private Customer customer;

    @MapsId("preferenceId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PreferenceId", nullable = false)
    private Preference preference;

    public CustomerpreferenceId getId() {
        return id;
    }

    public void setId(CustomerpreferenceId id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Preference getPreference() {
        return preference;
    }

    public void setPreference(Preference preference) {
        this.preference = preference;
    }

}