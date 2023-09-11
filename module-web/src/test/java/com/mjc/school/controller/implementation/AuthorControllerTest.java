package com.mjc.school.controller.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjc.school.controller.ControllerConfig;
import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.dto.update.AuthorUpdateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ContextConfiguration(initializers = IntegrationTestInitializer.class)
@SpringBootTest(classes = ControllerConfig.class)
@WebAppConfiguration
class AuthorControllerTest {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    public void createTestData() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext)
                .build();

        AuthorDto authorRequest = new AuthorDto();

        authorRequest.setName("someName");
        mockMvc.perform(post("/api/v1/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(authorRequest)));
    }

    @BeforeEach
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext)
                .build();
    }

    /**
     * 201
     */
    @Test
    void create() throws Exception {
        AuthorDto authorRequest = new AuthorDto();
        authorRequest.setName("someName");

        mockMvc.perform(post("/api/v1/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(authorRequest))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is(authorRequest.getName())));
    }

    /**
     * 400
     */
    @Test
    void createAndGetExceptionTooShortName() throws Exception {
        AuthorDto authorRequest = new AuthorDto();
        authorRequest.setName("Na");

        mockMvc.perform(post("/api/v1/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(authorRequest))
        ).andExpect(status().isBadRequest());
    }

    /**
     * 400
     */
    @Test
    void createAndGetExceptionIdShouldNotBeProvided() throws Exception {
        AuthorDto authorRequest = new AuthorDto();
        authorRequest.setId(123L);
        authorRequest.setName("testName");

        mockMvc.perform(post("/api/v1/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(authorRequest))
        ).andExpect(status().isBadRequest());
    }

    /**
     * 200
     */
    @Test
    void readAll() throws Exception {
        mockMvc.perform(get("/api/v1/authors"))
                .andExpect(status().isOk());
    }

    /**
     * 200
     */
    @Test
    void readAllWithPaging() throws Exception {
        mockMvc.perform(get("/api/v1/authors?pageNumber=1&pageSize=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", notNullValue()))
                .andExpect(jsonPath("$.[0].name", notNullValue()))
                .andExpect(jsonPath("$.[1].id", notNullValue()))
                .andExpect(jsonPath("$.[1].name", notNullValue()));
    }

    /**
     * 200
     */
    @Test
    void readAllWithSorting() throws Exception {
        mockMvc.perform(get("/api/v1/authors?sortBy=id asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", notNullValue()))
                .andExpect(jsonPath("$.[0].name", notNullValue()))
                .andExpect(jsonPath("$.[1].id", notNullValue()))
                .andExpect(jsonPath("$.[1].name", notNullValue()));
    }

    /**
     * 200
     */
    @Test
    void readAllWithPagingAndSorting() throws Exception {
        mockMvc.perform(get("/api/v1/authors?pageNumber=1&pageSize=5&sortBy=id asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", notNullValue()))
                .andExpect(jsonPath("$.[0].name", notNullValue()))
                .andExpect(jsonPath("$.[1].id", notNullValue()))
                .andExpect(jsonPath("$.[1].name", notNullValue()));
    }

    /**
     * 200
     */
    @Test
    void readById() throws Exception {
        mockMvc.perform(get("/api/v1/authors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("someName")));
    }

    /**
     * 404
     */
    @Test
    void readByIdAndGetNotFoundException() throws Exception {
        mockMvc.perform(get("/api/v1/authors/535"))
                .andExpect(status().isNotFound());
    }

    /**
     * 200
     */
    @Test
    void update() throws Exception {
        AuthorUpdateDto request = AuthorUpdateDto.builder()
                .id(1L)
                .name("newName")
                .build();

        mockMvc.perform(patch("/api/v1/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(request))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("newName")));
    }

    /**
     * 404
     */
    @Test
    void updateAndGetNotFoundException() throws Exception {
        AuthorUpdateDto request = AuthorUpdateDto.builder()
                .id(535L)
                .name("newName")
                .build();

        mockMvc.perform(patch("/api/v1/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(request))
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete("/api/v1/authors/2"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteByIdAndGetNotFoundException() throws Exception {
        mockMvc.perform(delete("/api/v1/authors/535"))
                .andExpect(status().isNotFound());
    }

    private String json(Object o) throws IOException {
        return objectMapper.writeValueAsString(o);
    }
}