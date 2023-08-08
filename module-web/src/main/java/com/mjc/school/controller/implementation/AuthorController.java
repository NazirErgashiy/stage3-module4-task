package com.mjc.school.controller.implementation;

import com.mjc.school.controller.AuthorControllerRequest;
import com.mjc.school.controller.BaseController;
import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.impl.AuthorService;
import com.mjc.school.service.requests.AuthorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorController implements BaseController<AuthorControllerRequest, AuthorDto, Long> {


    @Autowired
    public AuthorController(AuthorService authorServiceBean) {
        SERVICE = authorServiceBean;
    }

    private AuthorService SERVICE;

    @Override
    public List<AuthorDto> readAll() {
        return SERVICE.readAll();
    }

    @Override
    public AuthorDto readById(Long id) {
        return SERVICE.readById(id);
    }

    @Override
    public AuthorDto create(AuthorControllerRequest createRequest) {
        AuthorRequest request = new AuthorRequest();
        request.setName(createRequest.getName());
        request.setId(createRequest.getId());

        return SERVICE.create(request);
    }

    @Override
    public AuthorDto update(AuthorControllerRequest updateRequest) {
        AuthorRequest request = new AuthorRequest();
        request.setName(updateRequest.getName());
        request.setId(updateRequest.getId());

        return SERVICE.update(request);
    }

    @Override
    public boolean deleteById(Long id) {
        return SERVICE.deleteById(id);
    }

    public void createTestDataBase() {
        SERVICE.createTestDB();
    }
}
