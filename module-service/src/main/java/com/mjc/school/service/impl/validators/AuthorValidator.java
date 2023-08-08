package com.mjc.school.service.impl.validators;

import com.mjc.school.service.Validator;
import com.mjc.school.service.exceptions.LengthRuntimeException;
import com.mjc.school.service.requests.AuthorRequest;
import org.springframework.stereotype.Component;

@Component
public class AuthorValidator implements Validator<AuthorRequest> {

    private final int NAME_MIN_LENGTH = 3;
    private final int NAME_MAX_LENGTH = 15;

    @Override
    public Boolean validate(AuthorRequest dto) {
        if (dto.getName().length() < NAME_MIN_LENGTH || dto.getName().length() > NAME_MAX_LENGTH) {
            throw new LengthRuntimeException("Name length is too small or too big");
        }
        return true;
    }
}
