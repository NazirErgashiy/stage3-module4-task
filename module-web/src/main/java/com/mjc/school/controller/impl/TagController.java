package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.NextGenController;
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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TagDto> getAll(@RequestParam(required = false) Integer pageNumber,
                                @RequestParam(required = false, defaultValue = "3") Integer pageSize,
                                @RequestParam(required = false) String sortBy) {
        return tagService.readAll(pageNumber, pageSize, sortBy);
    }

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDto getById(@PathVariable Long id) {
        return tagService.readById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto create(@RequestBody @Validated TagDto createRequest) {
        return tagService.create(createRequest);
    }

    @Override
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDto update(@PathVariable Long id,@RequestBody @Validated TagUpdateDto updateRequest) {
        updateRequest.setId(id);
        return tagService.update(updateRequest);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        tagService.deleteById(id);
    }

    public void createTestDataBase() {
        tagService.createTestDB();
    }
}
