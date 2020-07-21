package com.people.dto.person;

import com.people.dto.address.AddressUpdateForm;
import com.people.model.Address;
import com.people.model.Person;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class PersonUpdateForm {

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    private LocalDate birth;

    @Valid
    @NotEmpty
    private Collection<AddressUpdateForm> addresses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public Collection<AddressUpdateForm> getAddresses() {
        return addresses;
    }

    public void setAddresses(Collection<AddressUpdateForm> addresses) {
        this.addresses = addresses;
    }

    public Person toModel(Person currentPerson) {
        final Person person = new Person.Builder()
                .setId(currentPerson.getId())
                .setName(this.name)
                .setCpf(currentPerson.getCpf())
                .setBirth(this.birth)
                .build();

        final Set<Address> addresses = this.addresses.stream()
                .map(addressUpdateForm -> addressUpdateForm.toModel(person))
                .collect(Collectors.toSet());

        person.setAddresses(addresses);

        return person;
    }
}
