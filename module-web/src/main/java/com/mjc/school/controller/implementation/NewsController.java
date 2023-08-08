package com.mjc.school.controller.implementation;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.NewsControllerRequest;
import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.dto.TagDto;
import com.mjc.school.service.impl.NewsService;
import com.mjc.school.service.requests.NewsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NewsController implements BaseController<NewsControllerRequest, NewsDto, Long> {

    @Autowired
    public NewsController(NewsService SERVICE) {
        this.SERVICE = SERVICE;
    }

    private NewsService SERVICE;

    @Override
    public List<NewsDto> readAll() {
        return SERVICE.readAll();
    }

    @Override
    public NewsDto readById(Long id) {
        return SERVICE.readById(id);
    }

    @Override
    public NewsDto create(NewsControllerRequest createRequest) {
        NewsRequest newsRequest = new NewsRequest();
        newsRequest.setTitle(createRequest.getTitle());
        newsRequest.setContent(createRequest.getContent());
        if (createRequest.getAuthorId() == 0L)
            createRequest.setAuthorId(null);
        newsRequest.setAuthorId(createRequest.getAuthorId());
        if (createRequest.getTagsId().get(0) == 0L)
            createRequest.setTagsId(null);
        newsRequest.setTagsId(createRequest.getTagsId());
        return SERVICE.create(newsRequest);
    }

    @Override
    public NewsDto update(NewsControllerRequest updateRequest) {
        NewsRequest newsRequest = new NewsRequest();
        newsRequest.setId(updateRequest.getId());
        newsRequest.setTitle(updateRequest.getTitle());
        newsRequest.setContent(updateRequest.getContent());
        if (updateRequest.getAuthorId() == 0L)
            updateRequest.setAuthorId(null);
        newsRequest.setAuthorId(updateRequest.getAuthorId());
        if (updateRequest.getTagsId().get(0) == 0L)
            updateRequest.setTagsId(null);
        newsRequest.setTagsId(updateRequest.getTagsId());
        return SERVICE.update(newsRequest);
    }

    @Override
    public boolean deleteById(Long id) {
        return SERVICE.deleteById(id);
    }

    public AuthorDto getAuthorByNewsId(Long id) {
        return SERVICE.getAuthorByNewsId(id);
    }

    public List<TagDto> getTagsByNewsId(Long id) {
        return SERVICE.getTagsByNewsId(id);
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
        return SERVICE.getNewsByParams(tagNames, tagIds, authorName, title, content);
    }

    public void createTestDataBase() {
        SERVICE.createTestDB();
    }
}
