package com.people.service;

import com.people.exception.ResourceNotFoundException;
import com.people.model.Person;
import com.people.repository.AddressRepository;
import com.people.repository.PeopleRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class PeopleService {

    private final PeopleRepository peopleRepository;
    private final AddressRepository addressRepository;

    public PeopleService(PeopleRepository peopleRepository,
                         AddressRepository addressRepository) {
        this.peopleRepository = peopleRepository;
        this.addressRepository = addressRepository;
    }

    public Person getPerson(UUID id) {
        return peopleRepository.findById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException(String.format("Person %s not found", id));
        });
    }

    @Transactional
    public Person create(Person person) {
        return peopleRepository.save(person);
    }

    @Transactional
    public Person update(Person person) {
        addressRepository.deleteAllByPersonId(person.getId());
        return peopleRepository.save(person);
    }

    public void delete(UUID id) {
        peopleRepository.deleteById(id);
    }
}
