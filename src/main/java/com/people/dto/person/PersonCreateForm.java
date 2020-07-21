package com.people.dto.person;

import com.people.dto.address.AddressCreateForm;
import com.people.model.Address;
import com.people.model.Person;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class PersonCreateForm {

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Pattern(regexp = "[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}\\-?[0-9]{2}")
    private String cpf;

    @NotNull
    private LocalDate birth;

    @Valid
    @NotEmpty
    private Collection<AddressCreateForm> addresses;

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

    public Collection<AddressCreateForm> getAddresses() {
        return addresses;
    }

    public void setAddresses(Collection<AddressCreateForm> addresses) {
        this.addresses = addresses;
    }

    public Person toModel() {
        final Person person = new Person.Builder()
                .setName(this.name)
                .setCpf(this.cpf)
                .setBirth(this.birth)
                .build();

        final Set<Address> addresses = this.addresses.stream()
                .map(addressCreateForm -> addressCreateForm.toModel(person))
                .collect(Collectors.toSet());

        person.setAddresses(addresses);

        return person;
    }
}
