package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.NextGenController;
import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.dto.TagDto;
import com.mjc.school.service.dto.update.NewsUpdateDto;
import com.mjc.school.service.impl.NewsService;
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
@RequestMapping("/api/v1/news")
public class NewsController implements NextGenController<NewsUpdateDto, NewsDto, Long> {
    private final NewsService newsService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NewsDto> getAll(@RequestParam(required = false) Integer pageNumber,
                                @RequestParam(required = false, defaultValue = "3") Integer pageSize,
                                @RequestParam(required = false) String sortBy) {
        return newsService.readAll(pageNumber, pageSize, sortBy);
    }

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NewsDto getById(@PathVariable Long id) {
        return newsService.readById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewsDto create(@RequestBody @Validated NewsDto createRequest) {
        return newsService.create(createRequest);
    }

    @Override
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NewsDto update(@PathVariable Long id,@RequestBody @Validated NewsUpdateDto updateRequest) {
        updateRequest.setId(id);
        return newsService.update(updateRequest);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        newsService.deleteById(id);
    }

    @GetMapping("/{id}/author")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDto getAuthorByNewsId(@PathVariable Long id) {
        return newsService.getAuthorByNewsId(id);
    }

    @GetMapping("/{id}/tag")
    @ResponseStatus(HttpStatus.OK)
    public List<TagDto> getTagsByNewsId(@PathVariable Long id) {
        return newsService.getTagsByNewsId(id);
    }

    public List<NewsDto> getNewsByParams(String tagNames, List<Long> tagIds, String authorName, String title, String content) {
        if ("-".equals(tagNames))
            tagNames = null;
        if (tagIds.get(0) == 0L)
            tagIds = null;
        if ("-".equals(authorName))
            authorName = null;
        if ("-".equals(title))
            title = null;
        if ("-".equals(content))
            content = null;
        return newsService.getNewsByParams(tagNames, tagIds, authorName, title, content);
    }

    public void createTestDataBase() {
        newsService.createTestDB();
    }
}
