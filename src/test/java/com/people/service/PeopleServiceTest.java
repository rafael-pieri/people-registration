package com.people.service;

import com.people.exception.ResourceNotFoundException;
import com.people.model.Address;
import com.people.model.Person;
import com.people.repository.AddressRepository;
import com.people.repository.PeopleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PeopleServiceTest {

    @InjectMocks
    private PeopleService peopleService;

    @Mock
    private PeopleRepository peopleRepository;

    @Mock
    private AddressRepository addressRepository;

    @Test
    public void shouldGetPerson() {
        final Person person = getPerson();

        when(this.peopleRepository.findById(person.getId())).thenReturn(Optional.of(person));

        final Person personReturned = this.peopleService.getPerson(person.getId());

        assertEquals(person, personReturned);

        verify(this.peopleRepository, times(1)).findById(person.getId());
    }

    @Test
    public void shouldThrowAnExceptionWhenPersonIsNotFound() {
        final Person person = getPerson();

        when(this.peopleRepository.findById(person.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> this.peopleService.getPerson(person.getId()));

        verify(this.peopleRepository, times(1)).findById(person.getId());
    }

    @Test
    public void shouldCreateANewPerson() {
        final Person person = getPerson();

        when(this.peopleRepository.save(person)).thenReturn(person);

        final Person personReturned = this.peopleService.create(person);

        assertEquals(person, personReturned);

        verify(this.peopleRepository, times(1)).save(person);
    }

    @Test
    public void shouldUpdateSpecificPerson() {
        final Person person = getPerson();

        doNothing().when(this.addressRepository).deleteAllByPersonId(person.getId());
        when(this.peopleRepository.save(person)).thenReturn(person);

        final Person personReturned = this.peopleService.update(person);

        assertEquals(person, personReturned);

        verify(this.addressRepository, times(1)).deleteAllByPersonId(person.getId());
        verify(this.peopleRepository, times(1)).save(person);
    }

    private Person getPerson() {
        return new Person.Builder()
                .setId(UUID.randomUUID())
                .setName("Julius")
                .setCpf("410.350.468-25")
                .setBirth(LocalDate.of(1967, 1, 4))
                .setAddresses(Collections.singleton(
                        new Address.Builder()
                                .setStreet("Street One")
                                .setNumber(200)
                                .setComplement("Apartment 112")
                                .setDistrict("Downtown")
                                .setCity("Campinas")
                                .setState("SP")
                                .setCountry("Brazil")
                                .build()
                ))
                .build();
    }
}
