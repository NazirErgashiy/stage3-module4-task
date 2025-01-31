package com.mjc.school.controller.impl;

import com.mjc.school.controller.NextGenController;
import com.mjc.school.service.dto.CommentDto;
import com.mjc.school.service.dto.update.CommentUpdateDto;
import com.mjc.school.service.impl.CommentService;
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
@RequestMapping("/api/v1/news/{id}/comments")
public class CommentController implements NextGenController<CommentUpdateDto, CommentDto, Long> {

    private final CommentService commentService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> getAll(@RequestParam(required = false) Integer pageNumber,
                                   @RequestParam(required = false, defaultValue = "3") Integer pageSize,
                                   @RequestParam(required = false) String sortBy) {
        return commentService.readAll(pageNumber, pageSize, sortBy);
    }

    @Override
    @GetMapping("/{comment-id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto getById(@PathVariable("comment-id") Long id) {
        return commentService.readById(id);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto create(@RequestBody @Validated CommentDto createRequest) {
        return commentService.create(createRequest);
    }

    @Override
    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public CommentDto update(@PathVariable Long id, @RequestBody @Validated CommentUpdateDto updateRequest) {
        updateRequest.setNewsId(id);
        return commentService.update(updateRequest);
    }

    @Override
    @DeleteMapping("/{comment-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("comment-id") Long id) {
        commentService.deleteById(id);
    }
}
