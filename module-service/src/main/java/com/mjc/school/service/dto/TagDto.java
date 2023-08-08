package com.mjc.school.service.dto;

import com.mjc.school.repository.implementation.model.NewsModel;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class TagDto {
    private Long id;
    private String name;
    //private List<NewsDto> newsId;
}
