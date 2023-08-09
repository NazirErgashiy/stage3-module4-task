package com.mjc.school.service.impl;

import com.mjc.school.repository.implementation.dao.AuthorRepository;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.exceptions.AuthorNotFoundRuntimeException;
import com.mjc.school.service.impl.validators.AuthorValidator;
import com.mjc.school.service.mapper.AuthorMapperImpl;
import com.mjc.school.service.requests.AuthorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public class AuthorService implements BaseService<AuthorRequest, AuthorDto, Long> {

    @Autowired
    public AuthorService(AuthorRepository REPOSITORY, AuthorValidator authorValidator) {
        this.REPOSITORY = REPOSITORY;
        this.VALIDATOR = authorValidator;
    }

    private AuthorRepository REPOSITORY;
    private final AuthorMapperImpl MAPPER = new AuthorMapperImpl();
    private AuthorValidator VALIDATOR;

    @Override
    public List<AuthorDto> readAll() {
        return MAPPER.modelToDtoList(REPOSITORY.readAll());
    }

    @Override
    public AuthorDto readById(Long id) {
        if (!REPOSITORY.existById(id)) {
            throw new AuthorNotFoundRuntimeException("Author with id [" + id + "] not found");
        }
        return MAPPER.modelToDto(REPOSITORY.readById(id).get());
    }

    @Override
    public AuthorDto create(AuthorRequest request) {
        if (VALIDATOR.validate(request)) {
            AuthorDto dto = new AuthorDto();
            dto.setName(request.getName());
            return MAPPER.modelToDto(REPOSITORY.create(MAPPER.dtoToModel(dto)));
        }
        return null;
    }

    @Override
    public AuthorDto update(AuthorRequest request) {
        if (VALIDATOR.validate(request)) {
            AuthorDto dto = new AuthorDto();
            dto.setName(request.getName());
            dto.setId(request.getId());
            return MAPPER.modelToDto(REPOSITORY.update(MAPPER.dtoToModel(dto)));
        }
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return REPOSITORY.deleteById(id);
    }

    private AuthorDto createSpecialAuthor(String name) {
        AuthorRequest aReq1 = new AuthorRequest();
        aReq1.setName(name);
        return create(aReq1);
    }

    public void createTestDB() {

        createSpecialAuthor("Laila Mcmahon");
        createSpecialAuthor("Kaleb Proctor");
        createSpecialAuthor("Livia Moody");
        createSpecialAuthor("Corey Terry");
        createSpecialAuthor("Charlie West");
        createSpecialAuthor("Cleo Rush");
        createSpecialAuthor("Kamran Wolf");
        createSpecialAuthor("Elissa Swee");
        createSpecialAuthor("Alys Hines");
        createSpecialAuthor("Tiffany Ber");
        createSpecialAuthor("Lowri Ortiz");
        createSpecialAuthor("Kelsey Gal");
        createSpecialAuthor("Darren Salas");
        createSpecialAuthor("Jeremy Eaton");
        createSpecialAuthor("Simon Summers");
        createSpecialAuthor("Julian Gibbons");
        createSpecialAuthor("Nellie Poole");
        createSpecialAuthor("Hazel Murray");
        createSpecialAuthor("Cohen Holmes");
        createSpecialAuthor("Serena Buch");
    }
}
