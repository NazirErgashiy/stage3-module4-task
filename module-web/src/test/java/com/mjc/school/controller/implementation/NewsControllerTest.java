package com.mjc.school.controller.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjc.school.controller.ControllerConfig;
import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.dto.update.AuthorUpdateDto;
import com.mjc.school.service.dto.update.NewsUpdateDto;
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
class NewsControllerTest {


    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ObjectMapper objectMapper;

    Long launchCount = 1L;

    @BeforeEach
    public void createTestAuthor() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext)
                .build();

        AuthorDto authorRequest = AuthorDto.builder()
                .name("someName")
                .build();

        mockMvc.perform(post("/api/v1/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(authorRequest))
                )
                .andExpect(status().isCreated());
        launchCount++;
    }

    @BeforeEach
    public void createTestData() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext)
                .build();

        NewsDto request = new NewsDto();
        request.setTitle("someTitle");
        request.setContent("someContent");
        request.setAuthorId(launchCount++);

        mockMvc.perform(post("/api/v1/news")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(request)));
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
        NewsDto request = new NewsDto();
        request.setTitle("someTitle");
        request.setContent("someContent");
        request.setAuthorId(3L);

        mockMvc.perform(post("/api/v1/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(request))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.title", is(request.getTitle())))
                .andExpect(jsonPath("$.content", is(request.getContent())))
                .andExpect(jsonPath("$.authorId", is(3)));
    }

    /**
     * 400
     */
    @Test
    void createAndGetExceptionTooShortTitle() throws Exception {
        NewsDto request = new NewsDto();
        request.setTitle("s");
        request.setContent("someContent");
        request.setAuthorId(3L);

        mockMvc.perform(post("/api/v1/news")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(request))
        ).andExpect(status().isBadRequest());
    }

    /**
     * 400
     */
    @Test
    void createAndGetExceptionTooShortContent() throws Exception {
        NewsDto request = new NewsDto();
        request.setTitle("someTitle");
        request.setContent("s");
        request.setAuthorId(3L);

        mockMvc.perform(post("/api/v1/news")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(request))
        ).andExpect(status().isBadRequest());
    }

    /**
     * 400
     */
    @Test
    void createAndGetExceptionIdShouldNotBeProvided() throws Exception {
        NewsDto request = new NewsDto();
        request.setId(123L);
        request.setTitle("someTitle");
        request.setContent("someContent");
        request.setAuthorId(3L);

        mockMvc.perform(post("/api/v1/news")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(request))
        ).andExpect(status().isBadRequest());
    }

    /**
     * 200
     */
    @Test
    void readAll() throws Exception {
        mockMvc.perform(get("/api/v1/news"))
                .andExpect(status().isOk());
    }

    /**
     * 200
     */
    @Test
    void readAllWithPaging() throws Exception {
        mockMvc.perform(get("/api/v1/news?pageNumber=1&pageSize=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", notNullValue()))
                .andExpect(jsonPath("$.[0].title", notNullValue()))
                .andExpect(jsonPath("$.[0].content", notNullValue()))
                .andExpect(jsonPath("$.[0].authorId", notNullValue()))
                .andExpect(jsonPath("$.[1].id", notNullValue()))
                .andExpect(jsonPath("$.[1].title", notNullValue()))
                .andExpect(jsonPath("$.[1].content", notNullValue()))
                .andExpect(jsonPath("$.[1].authorId", notNullValue()));
    }

    /**
     * 200
     */
    @Test
    void readAllWithSorting() throws Exception {
        mockMvc.perform(get("/api/v1/news?sortBy=id asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", notNullValue()))
                .andExpect(jsonPath("$.[0].title", notNullValue()))
                .andExpect(jsonPath("$.[0].content", notNullValue()))
                .andExpect(jsonPath("$.[0].authorId", notNullValue()))
                .andExpect(jsonPath("$.[1].id", notNullValue()))
                .andExpect(jsonPath("$.[1].title", notNullValue()))
                .andExpect(jsonPath("$.[1].content", notNullValue()))
                .andExpect(jsonPath("$.[1].authorId", notNullValue()));
    }

    /**
     * 200
     */
    @Test
    void readAllWithPagingAndSorting() throws Exception {
        mockMvc.perform(get("/api/v1/news?pageNumber=1&pageSize=5&sortBy=id asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", notNullValue()))
                .andExpect(jsonPath("$.[0].title", notNullValue()))
                .andExpect(jsonPath("$.[0].content", notNullValue()))
                .andExpect(jsonPath("$.[0].authorId", notNullValue()))
                .andExpect(jsonPath("$.[1].id", notNullValue()))
                .andExpect(jsonPath("$.[1].title", notNullValue()))
                .andExpect(jsonPath("$.[1].content", notNullValue()))
                .andExpect(jsonPath("$.[1].authorId", notNullValue()));
    }

    /**
     * 200
     */
    @Test
    void readById() throws Exception {
        mockMvc.perform(get("/api/v1/news/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.title", is("someTitle")))
                .andExpect(jsonPath("$.content", is("someContent")));
    }

    /**
     * 404
     */
    @Test
    void readByIdAndGetNotFoundException() throws Exception {
        mockMvc.perform(get("/api/v1/news/535"))
                .andExpect(status().isNotFound());
    }

    /**
     * 200
     */
    @Test
    void update() throws Exception {
        NewsUpdateDto request = NewsUpdateDto.builder()
                .id(3L)
                .title("newTitle")
                .content("newContent")
                .build();

        mockMvc.perform(patch("/api/v1/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(request))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.title", is(request.getTitle())))
                .andExpect(jsonPath("$.content", is(request.getContent())));
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

        mockMvc.perform(patch("/api/v1/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(request))
                )
                .andExpect(status().isNotFound());
    }

    /**
     * 404
     */
    @Test
    void createAndGetExceptionAuthorNotFound() throws Exception {
        NewsDto request = new NewsDto();
        request.setTitle("someTitle");
        request.setContent("someContent");
        request.setAuthorId(535L);

        mockMvc.perform(post("/api/v1/news")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(request))
        ).andExpect(status().isNotFound());
    }

    /**
     * 204
     */
    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete("/api/v1/news/2"))
                .andExpect(status().isNoContent());
    }

    /**
     * 404
     */
    @Test
    void deleteByIdAndGetNotFoundException() throws Exception {
        mockMvc.perform(delete("/api/v1/news/535"))
                .andExpect(status().isNotFound());
    }

    private String json(Object o) throws IOException {
        return objectMapper.writeValueAsString(o);
    }
}