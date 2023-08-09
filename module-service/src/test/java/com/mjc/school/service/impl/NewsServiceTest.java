package com.mjc.school.service.impl;

import com.mjc.school.service.spring.ServiceConfig;
import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.exceptions.AuthorNotFoundRuntimeException;
import com.mjc.school.service.exceptions.NewsNotFoundRuntimeException;
import com.mjc.school.service.requests.AuthorRequest;
import com.mjc.school.service.requests.NewsRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ServiceConfig.class)
class NewsServiceTest {
    @Autowired
    private AuthorService authorService;
    @Autowired
    private NewsService newsService;

    @Test
    void test() {

        assertNotNull(authorService);
        assertNotNull(newsService);
    }

    private NewsDto createBasicNews(Long authorId) {
        NewsRequest request1 = new NewsRequest();
        request1.setTitle("Title");
        request1.setContent("Abobus & Amogus");
        request1.setAuthorId(authorId);
        return newsService.create(request1);
    }

    private AuthorDto createBasicAuthor() {
        AuthorRequest aReq1 = new AuthorRequest();
        aReq1.setName("Aboba");
        return authorService.create(aReq1);
    }

    @Test
    void createReadByIdDelete() {
        AuthorDto authorDto1 = createBasicAuthor();
        NewsDto newsDto1 = createBasicNews(authorDto1.getId());

        assertEquals(newsDto1, newsService.readById(newsDto1.getId()));
        assertEquals(authorDto1, authorService.readById(authorDto1.getId()));

        authorService.deleteById(authorDto1.getId());

        assertThrows(AuthorNotFoundRuntimeException.class, () -> authorService.readById(authorDto1.getId()));
        assertThrows(NewsNotFoundRuntimeException.class, () -> newsService.readById(newsDto1.getId()));
    }
}