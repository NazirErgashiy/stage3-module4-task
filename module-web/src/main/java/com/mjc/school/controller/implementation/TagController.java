package com.mjc.school.controller.implementation;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.TagControllerRequest;
import com.mjc.school.service.dto.TagDto;
import com.mjc.school.service.impl.TagService;
import com.mjc.school.service.requests.TagRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.List;

@Component
@Controller
public class TagController implements BaseController<TagControllerRequest, TagDto, Long> {

    @Autowired
    public TagController(TagService service) {
        SERVICE = service;
    }

    TagService SERVICE;

    @Override
    public List<TagDto> readAll() {
        return SERVICE.readAll();
    }

    @Override
    public TagDto readById(Long id) {
        return SERVICE.readById(id);
    }

    @Override
    public TagDto create(TagControllerRequest createRequest) {
        TagRequest request = new TagRequest();
        request.setName(createRequest.getName());
        request.setId(createRequest.getId());
        return SERVICE.create(request);
    }

    @Override
    public TagDto update(TagControllerRequest updateRequest) {
        TagRequest request = new TagRequest();
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
