package com.mjc.school.service.test;

import com.mjc.school.repository.implementation.dao.AuthorRepository;
import com.mjc.school.repository.implementation.dao.NewsRepository;
import com.mjc.school.repository.implementation.dao.TagRepository;
import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.exceptions.AuthorNotFoundRuntimeException;
import com.mjc.school.service.exceptions.NewsNotFoundRuntimeException;
import com.mjc.school.service.impl.AuthorService;
import com.mjc.school.service.impl.NewsService;
import com.mjc.school.service.impl.TagService;
import com.mjc.school.service.impl.validators.AuthorValidator;
import com.mjc.school.service.impl.validators.NewsValidator;
import com.mjc.school.service.impl.validators.TagValidator;
import com.mjc.school.service.requests.AuthorRequest;
import com.mjc.school.service.requests.NewsRequest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NewsServiceTest {

    private AuthorRepository authorRepository = new AuthorRepository();
    private NewsRepository newsRepository = new NewsRepository();
    private TagRepository tagRepository = new TagRepository();

    private NewsValidator newsValidator = new NewsValidator(authorRepository);
    private AuthorValidator authorValidator = new AuthorValidator();
    private TagValidator tagValidator = new TagValidator();
    private TagService tagService = new TagService(tagRepository, tagValidator);

    private AuthorService AUTHOR_SERVICE = new AuthorService(authorRepository, authorValidator);
    private NewsService NEWS_SERVICE = new NewsService(newsRepository, newsValidator, tagService, AUTHOR_SERVICE);

    private NewsDto createBasicNews(Long authorId) {
        NewsRequest request1 = new NewsRequest();
        request1.setTitle("Title");
        request1.setContent("Abobus & Amogus");
        request1.setAuthorId(authorId);
        return NEWS_SERVICE.create(request1);
    }

    private AuthorDto createBasicAuthor() {
        AuthorRequest aReq1 = new AuthorRequest();
        aReq1.setName("Aboba");
        return AUTHOR_SERVICE.create(aReq1);
    }

    @Test
    void createReadByIdDelete() {
        AuthorDto authorDto1 = createBasicAuthor();
        NewsDto newsDto1 = createBasicNews(authorDto1.getId());

        assertEquals(newsDto1, NEWS_SERVICE.readById(newsDto1.getId()));
        assertEquals(authorDto1, AUTHOR_SERVICE.readById(authorDto1.getId()));

        AUTHOR_SERVICE.deleteById(authorDto1.getId());

        assertThrows(AuthorNotFoundRuntimeException.class, () -> AUTHOR_SERVICE.readById(authorDto1.getId()));
        assertThrows(NewsNotFoundRuntimeException.class, () -> NEWS_SERVICE.readById(newsDto1.getId()));
    }
}