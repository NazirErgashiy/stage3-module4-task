package com.mjc.school.controller.implementation;

import com.mjc.school.controller.NextGenController;
import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.dto.TagDto;
import com.mjc.school.service.dto.update.TagUpdateDto;
import com.mjc.school.service.impl.TagService;
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
@RequestMapping("/api/v1/tags")
public class TagController implements NextGenController<TagUpdateDto, TagDto, Long> {

    private final TagService tagService;

    @Override
    public List<TagDto> readAll() {
        return tagService.readAll();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TagDto> readAll(@RequestParam(required = false) Integer pageNumber,
                                   @RequestParam(required = false, defaultValue = "3") Integer pageSize,
                                   @RequestParam(required = false) String sortBy) {
        return tagService.readAll(pageNumber, pageSize, sortBy);
    }

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDto readById(@PathVariable Long id) {
        return tagService.readById(id);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto create(@RequestBody @Validated TagDto createRequest) {
        return tagService.create(createRequest);
    }

    @Override
    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public TagDto update(@RequestBody @Validated TagUpdateDto updateRequest) {
        return tagService.update(updateRequest);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean deleteById(@PathVariable Long id) {
        return tagService.deleteById(id);
    }

    public void createTestDataBase() {
        tagService.createTestDB();
    }
}
