package com.people.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.people.dto.address.AddressView;
import com.people.dto.person.PersonView;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
public class Person {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    private String name;

    private String cpf;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birth;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person", fetch = FetchType.LAZY)
    private Collection<Address> addresses;

    public Person() {}

    public Person(UUID id, String name, String cpf, LocalDate birth, Collection<Address> addresses) {
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

    public Collection<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Collection<Address> addresses) {
        this.addresses = addresses;
    }

    public static class Builder {
        private UUID id;
        private String name;
        private String cpf;
        private LocalDate birth;
        private Collection<Address> addresses;

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

        public Builder setAddresses(Collection<Address> addresses) {
            this.addresses = addresses;
            return this;
        }

        public Person build() {
            return new Person(id, name, cpf, birth, addresses);
        }
    }

    public PersonView toView() {
        final PersonView person = new PersonView.Builder()
                .setId(this.id)
                .setName(this.name)
                .setCpf(this.cpf)
                .setBirth(this.birth)
                .build();

        Set<AddressView> addressViews = this.addresses.stream()
                .map(Address::toView)
                .collect(Collectors.toSet());

        person.setAddresses(addressViews);

        return person;
    }
}
