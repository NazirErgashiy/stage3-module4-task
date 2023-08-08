package com.mjc.school.service.impl;

import com.mjc.school.repository.implementation.dao.TagRepository;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.TagDto;
import com.mjc.school.service.exceptions.AuthorNotFoundRuntimeException;
import com.mjc.school.service.impl.validators.TagValidator;
import com.mjc.school.service.mapper.TagMapperImpl;
import com.mjc.school.service.requests.TagRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagService implements BaseService<TagRequest, TagDto, Long> {

    @Autowired
    public TagService(TagRepository repository, TagValidator validator) {
        REPOSITORY = repository;
        VALIDATOR = validator;
    }

    private TagRepository REPOSITORY;
    private TagValidator VALIDATOR;
    private final TagMapperImpl MAPPER = new TagMapperImpl();


    @Override
    public List<TagDto> readAll() {
        return MAPPER.modelToDtoList(REPOSITORY.readAll());
    }

    @Override
    public TagDto readById(Long id) {
        if (!REPOSITORY.existById(id)) {
            throw new AuthorNotFoundRuntimeException("Tag with id [" + id + "] not found");
        }
        return MAPPER.modelToDto(REPOSITORY.readById(id).get());
    }

    @Override
    public TagDto create(TagRequest createRequest) {
        if (VALIDATOR.validate(createRequest)) {
            TagDto dto = new TagDto();
            dto.setName(createRequest.getName());
            return MAPPER.modelToDto(REPOSITORY.create(MAPPER.dtoToModel(dto)));
        }
        return null;
    }

    @Override
    public TagDto update(TagRequest updateRequest) {
        if (VALIDATOR.validate(updateRequest)) {
            TagDto dto = new TagDto();
            dto.setName(updateRequest.getName());
            dto.setId(updateRequest.getId());
            return MAPPER.modelToDto(REPOSITORY.update(MAPPER.dtoToModel(dto)));
        }
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return REPOSITORY.deleteById(id);
    }

    private TagDto createSpecialTag(String name) {
        TagRequest aReq1 = new TagRequest();
        aReq1.setName(name);
        return create(aReq1);
    }

    public void createTestDB() {

        createSpecialTag("crime");
        createSpecialTag("wizard");
        createSpecialTag("love");
        createSpecialTag("war");
        createSpecialTag("luck");
        createSpecialTag("sport");
        createSpecialTag("computer");
        createSpecialTag("games");
        createSpecialTag("java");
        createSpecialTag("heart");
    }
}
