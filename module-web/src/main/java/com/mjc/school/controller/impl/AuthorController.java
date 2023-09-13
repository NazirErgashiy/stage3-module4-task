package com.mjc.school.controller.impl;

import com.mjc.school.controller.NextGenController;
import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.dto.update.AuthorUpdateDto;
import com.mjc.school.service.impl.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/authors")
public class AuthorController implements NextGenController<AuthorUpdateDto, AuthorDto, Long> {

    private final AuthorService authorService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AuthorDto> getAll(@RequestParam(required = false) Integer pageNumber,
                                  @RequestParam(required = false, defaultValue = "3") Integer pageSize,
                                  @RequestParam(required = false) String sortBy) {
        return authorService.readAll(pageNumber, pageSize, sortBy);
    }

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDto getById(@PathVariable Long id) {
        return authorService.readById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDto create(@RequestBody @Validated AuthorDto createRequest) {
        return authorService.create(createRequest);
    }

    @Override
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDto update(@PathVariable Long id, @RequestBody @Validated AuthorUpdateDto updateRequest) {
        updateRequest.setId(id);
        return authorService.update(updateRequest);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        authorService.deleteById(id);
    }

    public void createTestDataBase() {
        authorService.createTestDB();
    }
}
