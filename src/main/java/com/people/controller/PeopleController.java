package com.people.controller;

import com.people.dto.person.PersonCreateForm;
import com.people.dto.person.PersonUpdateForm;
import com.people.dto.person.PersonView;
import com.people.model.Person;
import com.people.service.PeopleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Api(tags = "People",
        description = "APIs responsible for managing people registration")
@RestController
@RequestMapping("/v1/people")
public class PeopleController {

    private final PeopleService peopleService;

    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @ApiOperation("Get a specific person registration")
    @GetMapping("/{id}")
    public PersonView getPerson(@PathVariable UUID id) {
        return peopleService.getPerson(id).toView();
    }

    @ApiOperation("Create a new person registration")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonView createParticipation(@Valid @RequestBody PersonCreateForm personCreateForm) {
        return peopleService.create(personCreateForm.toModel()).toView();
    }

    @ApiOperation("Update a specific person registration")
    @PutMapping("/{id}")
    public PersonView updateParticipation(@PathVariable UUID id,
                                          @Valid @RequestBody PersonUpdateForm personUpdateForm) {
        final Person person = peopleService.getPerson(id);
        return peopleService.update(personUpdateForm.toModel(person)).toView();
    }

    @ApiOperation("Remove a specific person registration")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteParticipation(@PathVariable UUID id) {
        peopleService.delete(id);
    }
}
