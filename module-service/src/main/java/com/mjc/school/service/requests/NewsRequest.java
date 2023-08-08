package com.mjc.school.service.requests;

import com.mjc.school.service.dto.TagDto;
import lombok.Data;

import java.util.List;

@Data
public class NewsRequest {
    private Long id;
    private String title;
    private String content;
    private Long authorId;
    private List<Long> tagsId;
}
