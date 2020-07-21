package com.people.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.people.dto.address.AddressView;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Address {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    private String street;

    private Integer number;

    private String complement;

    private String city;

    private String district;

    private String state;

    private String country;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public Address() {}

    public Address(UUID id,
                   String street,
                   Integer number,
                   String complement,
                   String city,
                   String district,
                   String state,
                   String country,
                   Person person) {
        this.id = id;
        this.street = street;
        this.number = number;
        this.complement = complement;
        this.city = city;
        this.district = district;
        this.state = state;
        this.country = country;
        this.person = person;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public static class Builder {
        private UUID id;
        private String street;
        private Integer number;
        private String complement;
        private String district;
        private String city;
        private String state;
        private String country;
        private Person person;

        public Builder setId(UUID id) {
            this.id = id;
            return this;
        }

        public Builder setStreet(String street) {
            this.street = street;
            return this;
        }

        public Builder setNumber(Integer number) {
            this.number = number;
            return this;
        }

        public Builder setComplement(String complement) {
            this.complement = complement;
            return this;
        }

        public Builder setDistrict(String district) {
            this.district = district;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Builder setState(String state) {
            this.state = state;
            return this;
        }

        public Builder setCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder setPerson(Person person) {
            this.person = person;
            return this;
        }

        public Address build() {
            return new Address(id, street, number, complement, city, district, state, country, person);
        }
    }

    public AddressView toView() {
        return new AddressView.Builder()
                .setId(this.id)
                .setStreet(this.street)
                .setNumber(this.number)
                .setComplement(this.complement)
                .setDistrict(this.district)
                .setCity(this.city)
                .setState(this.state)
                .setCountry(this.country)
                .build();
    }
}
