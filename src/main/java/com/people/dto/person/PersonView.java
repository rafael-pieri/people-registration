package com.people.dto.person;

import com.people.dto.address.AddressView;
import com.people.model.Address;
import com.people.model.Person;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

public class PersonView {

    private UUID id;
    private String name;
    private String cpf;
    private LocalDate birth;
    private Collection<AddressView> addresses;

    public PersonView() {}

    public PersonView(UUID id, String name, String cpf, LocalDate birth, Collection<AddressView> addresses) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.birth = birth;
        this.addresses = addresses;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public Collection<AddressView> getAddresses() {
        return addresses;
    }

    public void setAddresses(Collection<AddressView> addresses) {
        this.addresses = addresses;
    }

    public static class Builder {
        private UUID id;
        private String name;
        private String cpf;
        private LocalDate birth;
        private Collection<AddressView> addresses;

        public Builder setId(UUID id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setCpf(String cpf) {
            this.cpf = cpf;
            return this;
        }

        public Builder setBirth(LocalDate birth) {
            this.birth = birth;
            return this;
        }

        public Builder setAddresses(Collection<AddressView> addresses) {
            this.addresses = addresses;
            return this;
        }

        public PersonView build() {
            return new PersonView(id, name, cpf, birth, addresses);
        }
    }
}
