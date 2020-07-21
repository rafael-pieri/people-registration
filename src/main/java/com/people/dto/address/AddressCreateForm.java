package com.people.dto.address;

import com.people.model.Address;
import com.people.model.Person;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddressCreateForm {

    @NotNull
    @Size(max = 255)
    private String street;

    @NotNull
    private Integer number;

    @Size(max = 60)
    private String complement;

    @NotNull
    @Size(max = 60)
    private String district;

    @NotNull
    @Size(max = 100)
    private String city;

    @NotNull
    @Size(max = 60)
    private String state;

    @NotNull
    @Size(max = 60)
    private String country;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public Address toModel(Person person) {
        return new Address.Builder()
                .setStreet(this.street)
                .setNumber(this.number)
                .setComplement(this.complement)
                .setDistrict(this.district)
                .setCity(this.city)
                .setState(this.state)
                .setCountry(this.country)
                .setPerson(person)
                .build();
    }
}
