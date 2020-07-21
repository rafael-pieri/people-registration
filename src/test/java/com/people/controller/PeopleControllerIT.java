package com.people.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.people.PeopleRegistrationApplication;
import com.people.dto.address.AddressCreateForm;
import com.people.dto.address.AddressUpdateForm;
import com.people.dto.person.PersonCreateForm;
import com.people.dto.person.PersonUpdateForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:insert-data.sql"}),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {"classpath:delete-data.sql"})
})
@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
@ContextConfiguration(initializers = {PeopleControllerIT.Initializer.class}, classes = {PeopleRegistrationApplication.class})
@AutoConfigureMockMvc
public class PeopleControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Container
    public static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>()
            .withUsername("root")
            .withPassword("root")
            .withDatabaseName("people_registration_db")
            .withExposedPorts(5432);

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgresContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgresContainer.getUsername(),
                    "spring.datasource.password=" + postgresContainer.getPassword())
                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @BeforeEach
    public void setup() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void shouldGetPerson() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/people/e538828c-cb07-11ea-a597-6b5986558a35")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value("e538828c-cb07-11ea-a597-6b5986558a35"))
                .andExpect(jsonPath("$.name").value("Julius"))
                .andExpect(jsonPath("$.cpf").value("320.870.268-10"))
                .andExpect(jsonPath("$.birth").value("1988-04-07"))
                .andExpect(jsonPath("$.addresses", hasSize(1)))
                .andExpect(jsonPath("$.addresses.[0].id").value("41f9161c-cb08-11ea-97ff-33e5abd18a9b"))
                .andExpect(jsonPath("$.addresses.[0].street").value("Paulista Street"))
                .andExpect(jsonPath("$.addresses.[0].number").value("458"))
                .andExpect(jsonPath("$.addresses.[0].complement").value("Apartment 25"))
                .andExpect(jsonPath("$.addresses.[0].district").value("Jardim Paulista"))
                .andExpect(jsonPath("$.addresses.[0].city").value("São Paulo"))
                .andExpect(jsonPath("$.addresses.[0].state").value("SP"))
                .andExpect(jsonPath("$.addresses.[0].country").value("Brazil"));
    }

    @Test
    public void shouldCreateANewPerson() throws Exception {
        final PersonCreateForm personCreateForm = getPersonCreateForm();

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/people")
                .content(objectMapper.writeValueAsString(personCreateForm))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name").value("Rafael"))
                .andExpect(jsonPath("$.cpf").value("410.350.468-25"))
                .andExpect(jsonPath("$.birth").value("1967-01-04"))
                .andExpect(jsonPath("$.addresses", hasSize(1)))
                .andExpect(jsonPath("$.addresses.[0].id", notNullValue()))
                .andExpect(jsonPath("$.addresses.[0].street").value("Street One"))
                .andExpect(jsonPath("$.addresses.[0].number").value("200"))
                .andExpect(jsonPath("$.addresses.[0].complement").value("Apartment 112"))
                .andExpect(jsonPath("$.addresses.[0].district").value("Downtown"))
                .andExpect(jsonPath("$.addresses.[0].city").value("Campinas"))
                .andExpect(jsonPath("$.addresses.[0].state").value("SP"))
                .andExpect(jsonPath("$.addresses.[0].country").value("Brazil"));
    }

    @Test
    public void shouldUpdatePerson() throws Exception {
        final PersonUpdateForm personUpdateForm = getPersonUpdateForm();

        mockMvc.perform(MockMvcRequestBuilders.put("/v1/people/e538828c-cb07-11ea-a597-6b5986558a35")
                .content(objectMapper.writeValueAsString(personUpdateForm))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value("e538828c-cb07-11ea-a597-6b5986558a35"))
                .andExpect(jsonPath("$.name").value("Rafael"))
                .andExpect(jsonPath("$.cpf").value("320.870.268-10"))
                .andExpect(jsonPath("$.birth").value("1967-01-04"))
                .andExpect(jsonPath("$.addresses", hasSize(1)))
                .andExpect(jsonPath("$.addresses.[0].id", notNullValue()))
                .andExpect(jsonPath("$.addresses.[0].street").value("Avenue One"))
                .andExpect(jsonPath("$.addresses.[0].number").value("200"))
                .andExpect(jsonPath("$.addresses.[0].complement").value("Apartment 112"))
                .andExpect(jsonPath("$.addresses.[0].district").value("Downtown"))
                .andExpect(jsonPath("$.addresses.[0].city").value("New York City"))
                .andExpect(jsonPath("$.addresses.[0].state").value("New York"))
                .andExpect(jsonPath("$.addresses.[0].country").value("USA"));
    }

    @Test
    public void shouldDeletePerson() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/people/e538828c-cb07-11ea-a597-6b5986558a35")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNoContent());
    }


    private PersonCreateForm getPersonCreateForm() {
        final AddressCreateForm addressCreateForm = new AddressCreateForm();
        addressCreateForm.setStreet("Street One");
        addressCreateForm.setNumber(200);
        addressCreateForm.setComplement("Apartment 112");
        addressCreateForm.setDistrict("Downtown");
        addressCreateForm.setCity("Campinas");
        addressCreateForm.setState("SP");
        addressCreateForm.setCountry("Brazil");

        final PersonCreateForm personCreateForm = new PersonCreateForm();
        personCreateForm.setName("Rafael");
        personCreateForm.setCpf("410.350.468-25");
        personCreateForm.setBirth(LocalDate.of(1967, 1, 4));
        personCreateForm.setAddresses(Collections.singleton(addressCreateForm));

        return personCreateForm;
    }

    private PersonUpdateForm getPersonUpdateForm() {
        final AddressUpdateForm addressUpdateForm = new AddressUpdateForm();
        addressUpdateForm.setStreet("Avenue One");
        addressUpdateForm.setNumber(200);
        addressUpdateForm.setComplement("Apartment 112");
        addressUpdateForm.setDistrict("Downtown");
        addressUpdateForm.setCity("New York City");
        addressUpdateForm.setState("New York");
        addressUpdateForm.setCountry("USA");

        final PersonUpdateForm personUpdateForm = new PersonUpdateForm();
        personUpdateForm.setName("Rafael");
        personUpdateForm.setBirth(LocalDate.of(1967, 1, 4));
        personUpdateForm.setAddresses(Collections.singleton(addressUpdateForm));

        return personUpdateForm;
    }
}
