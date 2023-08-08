package com.mjc.school.service.impl.validators;

import com.mjc.school.repository.implementation.dao.AuthorRepository;
import com.mjc.school.service.Validator;
import com.mjc.school.service.exceptions.AuthorNotFoundRuntimeException;
import com.mjc.school.service.exceptions.LengthRuntimeException;
import com.mjc.school.service.requests.NewsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewsValidator implements Validator<NewsRequest> {

    private final int TITLE_MIN_LENGTH = 5;
    private final int TITLE_MAX_LENGTH = 30;
    private final int CONTENT_MIN_LENGTH = 5;
    private final int CONTENT_MAX_LENGTH = 255;
    private AuthorRepository AUTHOR_REPOSITORY;

    @Autowired
    public NewsValidator(AuthorRepository authorRepository) {
        this.AUTHOR_REPOSITORY = authorRepository;
    }

    @Override
    public Boolean validate(NewsRequest request) {
        if (AUTHOR_REPOSITORY.existById(request.getAuthorId())) {
            if (request.getTitle().length() < TITLE_MIN_LENGTH || request.getTitle().length() > TITLE_MAX_LENGTH) {
                throw new LengthRuntimeException("Title length is too small or too big");
            }
            if (request.getContent().length() < CONTENT_MIN_LENGTH || request.getContent().length() > CONTENT_MAX_LENGTH) {
                throw new LengthRuntimeException("Content length is too small or too big");
            }
            return true;
        }
        throw new AuthorNotFoundRuntimeException("Author with id: " + request.getAuthorId() + " not found");
    }
}
