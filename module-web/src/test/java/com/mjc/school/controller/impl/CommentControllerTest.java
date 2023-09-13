package com.mjc.school.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjc.school.controller.ControllerConfig;
import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.dto.CommentDto;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.dto.update.CommentUpdateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommentControllerTest {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ObjectMapper objectMapper;

    Long launchCount = 0L;

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
    public void createTestNews() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext)
                .build();

        NewsDto newsRequest = NewsDto.builder()
                .title("someTitle")
                .content("someContent")
                .authorId(launchCount)
                .build();

        mockMvc.perform(post("/api/v1/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(newsRequest))
                )
                .andExpect(status().isCreated());
    }

    @BeforeEach
    public void createTestComments() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext)
                .build();

        CommentDto newsRequest = CommentDto.builder()
                .content("someComment")
                .newsId(1L)
                .build();

        mockMvc.perform(post("/api/v1/news/1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(newsRequest))
                )
                .andExpect(status().isCreated());
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
    @Order(1)
    void create() throws Exception {
        CommentDto request = new CommentDto();
        request.setContent("someContent");
        request.setNewsId(1L);

        mockMvc.perform(post("/api/v1/news/1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(request))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.content", is(request.getContent())))
                .andExpect(jsonPath("$.newsId", is(1)));
    }

    /**
     * 400
     */
    @Test
    @Order(2)
    void createAndGetExceptionTooShortName() throws Exception {
        CommentDto request = new CommentDto();
        request.setContent("so");
        request.setNewsId(1L);

        mockMvc.perform(post("/api/v1/news/1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(request))
                )
                .andExpect(status().isBadRequest());
    }

    /**
     * 400
     */
    @Test
    @Order(3)
    void createAndGetExceptionIdShouldNotBeProvided() throws Exception {
        CommentDto authorRequest = new CommentDto();
        authorRequest.setId(123L);
        authorRequest.setContent("testName");
        authorRequest.setNewsId(1L);

        mockMvc.perform(post("/api/v1/news/1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(authorRequest))
        ).andExpect(status().isBadRequest());
    }

    /**
     * 404
     */
    @Test
    @Order(4)
    void createAndGetNotFoundExceptionNews() throws Exception {
        CommentDto request = new CommentDto();
        request.setContent("testName");
        request.setNewsId(535L);

        mockMvc.perform(post("/api/v1/news/1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(request))
        ).andExpect(status().isNotFound());
    }

    /**
     * 200
     */
    @Test
    @Order(5)
    void readAll() throws Exception {
        mockMvc.perform(get("/api/v1/news/1/comments"))
                .andExpect(status().isOk());
    }

    /**
     * 200
     */
    @Test
    @Order(6)
    void readAllWithPaging() throws Exception {
        mockMvc.perform(get("/api/v1/news/1/comments?pageNumber=1&pageSize=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", notNullValue()))
                .andExpect(jsonPath("$.[0].content", notNullValue()))
                .andExpect(jsonPath("$.[0].newsId", notNullValue()))
                .andExpect(jsonPath("$.[1].id", notNullValue()))
                .andExpect(jsonPath("$.[1].content", notNullValue()))
                .andExpect(jsonPath("$.[1].newsId", notNullValue()));
    }

    /**
     * 200
     */
    @Test
    @Order(7)
    void readAllWithSorting() throws Exception {
        mockMvc.perform(get("/api/v1/news/1/comments?sortBy=id asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", notNullValue()))
                .andExpect(jsonPath("$.[0].content", notNullValue()))
                .andExpect(jsonPath("$.[0].newsId", notNullValue()))
                .andExpect(jsonPath("$.[1].id", notNullValue()))
                .andExpect(jsonPath("$.[1].content", notNullValue()))
                .andExpect(jsonPath("$.[1].newsId", notNullValue()));
    }

    /**
     * 200
     */
    @Test
    @Order(8)
    void readAllWithPagingAndSorting() throws Exception {
        mockMvc.perform(get("/api/v1/news/1/comments?pageNumber=1&pageSize=5&sortBy=id asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", notNullValue()))
                .andExpect(jsonPath("$.[0].content", notNullValue()))
                .andExpect(jsonPath("$.[0].newsId", notNullValue()))
                .andExpect(jsonPath("$.[1].id", notNullValue()))
                .andExpect(jsonPath("$.[1].content", notNullValue()))
                .andExpect(jsonPath("$.[1].newsId", notNullValue()));
    }

    /**
     * 200
     */
    @Test
    @Order(9)
    void readById() throws Exception {
        mockMvc.perform(get("/api/v1/news/1/comments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.content", is("someComment")))
                .andExpect(jsonPath("$.newsId", is(1)));
    }

    /**
     * 404
     */
    @Test
    @Order(10)
    void readByIdAndGetNotFoundException() throws Exception {
        mockMvc.perform(get("/api/v1/news/1/comments/535"))
                .andExpect(status().isNotFound());
    }

    /**
     * 200
     */
    @Test
    @Order(11)
    void update() throws Exception {
        CommentUpdateDto request = CommentUpdateDto.builder()
                .id(1L)
                .content("newContent")
                .build();

        mockMvc.perform(patch("/api/v1/news/1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(request))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.content", is("newContent")))
                .andExpect(jsonPath("$.newsId", is(1)));
    }

    /**
     * 404
     */
    @Test
    @Order(12)
    void updateAndGetNotFoundException() throws Exception {
        CommentUpdateDto request = CommentUpdateDto.builder()
                .id(535L)
                .content("newContent")
                .build();

        mockMvc.perform(patch("/api/v1/news/1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(request))
                )
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(13)
    void deleteById() throws Exception {
        mockMvc.perform(delete("/api/v1/news/1/comments/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(14)
    void deleteByIdAndGetNotFoundException() throws Exception {
        mockMvc.perform(delete("/api/v1/news/1/comments/535"))
                .andExpect(status().isNotFound());
    }

    private String json(Object o) throws IOException {
        return objectMapper.writeValueAsString(o);
    }
}