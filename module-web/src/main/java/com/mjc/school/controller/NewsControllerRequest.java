package com.mjc.school.controller;

import lombok.Data;

import java.util.List;

@Data
public class NewsControllerRequest {
    private Long id;
    private String title;
    private String content;
    private Long authorId;
    private List<Long> tagsId;
}
