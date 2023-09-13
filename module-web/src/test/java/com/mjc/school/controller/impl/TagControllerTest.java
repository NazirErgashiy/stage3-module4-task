package com.mjc.school.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjc.school.controller.ControllerConfig;
import com.mjc.school.service.dto.TagDto;
import com.mjc.school.service.dto.update.TagUpdateDto;
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
class TagControllerTest {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void createTestData() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext)
                .build();

        TagDto tagRequest = new TagDto();

        tagRequest.setName("someName");
        mockMvc.perform(post("/api/v1/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(tagRequest)));
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
        TagDto request = new TagDto();
        request.setName("someName");

        mockMvc.perform(post("/api/v1/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(request))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is(request.getName())));
    }

    /**
     * 400
     */
    @Test
    void createAndGetExceptionTooShortName() throws Exception {
        TagDto request = new TagDto();
        request.setName("Na");

        mockMvc.perform(post("/api/v1/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(request))
        ).andExpect(status().isBadRequest());
    }

    /**
     * 400
     */
    @Test
    void createAndGetExceptionIdShouldNotBeProvided() throws Exception {
        TagDto request = new TagDto();
        request.setId(123L);
        request.setName("testName");

        mockMvc.perform(post("/api/v1/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(request))
        ).andExpect(status().isBadRequest());
    }

    /**
     * 200
     */
    @Test
    void readAll() throws Exception {
        mockMvc.perform(get("/api/v1/tags"))
                .andExpect(status().isOk());
    }

    /**
     * 200
     */
    @Test
    void readAllWithPaging() throws Exception {
        mockMvc.perform(get("/api/v1/tags?pageNumber=1&pageSize=2"))
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
        mockMvc.perform(get("/api/v1/tags?sortBy=id asc"))
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
        mockMvc.perform(get("/api/v1/tags?pageNumber=1&pageSize=5&sortBy=id asc"))
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
        mockMvc.perform(get("/api/v1/tags/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("someName")));
    }

    /**
     * 404
     */
    @Test
    void readByIdAndGetNotFoundException() throws Exception {
        mockMvc.perform(get("/api/v1/tags/535"))
                .andExpect(status().isNotFound());
    }

    /**
     * 200
     */
    @Test
    void update() throws Exception {
        TagUpdateDto request = TagUpdateDto.builder()
                .id(1L)
                .name("newName")
                .build();

        mockMvc.perform(patch("/api/v1/tags/1")
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
        TagUpdateDto request = TagUpdateDto.builder()
                .id(535L)
                .name("newName")
                .build();

        mockMvc.perform(patch("/api/v1/tags/535")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(request))
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete("/api/v1/tags/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteByIdAndGetNotFoundException() throws Exception {
        mockMvc.perform(delete("/api/v1/tags/535"))
                .andExpect(status().isNotFound());
    }

    private String json(Object o) throws IOException {
        return objectMapper.writeValueAsString(o);
    }

}