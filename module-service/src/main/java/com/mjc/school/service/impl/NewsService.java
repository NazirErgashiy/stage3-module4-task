package com.mjc.school.service.impl;

import com.mjc.school.repository.implementation.dao.NewsRepository;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.dto.TagDto;
import com.mjc.school.service.exceptions.NewsNotFoundRuntimeException;
import com.mjc.school.service.impl.validators.NewsValidator;
import com.mjc.school.service.mapper.NewsMapperImpl;
import com.mjc.school.service.requests.NewsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class NewsService implements BaseService<NewsRequest, NewsDto, Long> {

    @Autowired
    public NewsService(NewsRepository newsRepository, NewsValidator newsValidator, TagService tagService, AuthorService authorService) {
        REPOSITORY = newsRepository;
        VALIDATOR = newsValidator;
        TAG_SERVICE = tagService;
        AUTHOR_SERVICE = authorService;
    }

    private NewsRepository REPOSITORY;
    private final NewsMapperImpl MAPPER = new NewsMapperImpl();
    private NewsValidator VALIDATOR;
    private TagService TAG_SERVICE;
    private AuthorService AUTHOR_SERVICE;

    @Override
    public List<NewsDto> readAll() {
        return MAPPER.modelToDtoList(REPOSITORY.readAll());
    }

    @Override
    public NewsDto readById(Long id) {
        if (!REPOSITORY.existById(id)) {
            throw new NewsNotFoundRuntimeException("News with id [" + id + "] not found");
        }
        return MAPPER.modelToDto(REPOSITORY.readById(id).get());
    }

    @Override
    public NewsDto create(NewsRequest request) {
        if (VALIDATOR.validate(request)) {
            NewsDto dto = new NewsDto();
            dto.setTitle(request.getTitle());
            dto.setContent(request.getContent());
            dto.setAuthor(AUTHOR_SERVICE.readById(request.getAuthorId()));

            List<TagDto> tagDtos = new ArrayList<>();
            if (request.getTagsId() != null)
                for (int i = 0; i < request.getTagsId().size(); i++) {
                    tagDtos.add(TAG_SERVICE.readById(request.getTagsId().get(i)));
                }
            dto.setTagsId(tagDtos);
            //dto.setAuthorId(AUTHOR_SERVICE.readById(request.getAuthorId()));
            return MAPPER.modelToDto(REPOSITORY.create(MAPPER.dtoToModel(dto)));
        }
        return null;
    }

    @Override
    public NewsDto update(NewsRequest request) {
        if (!REPOSITORY.existById(request.getId())) {
            throw new NewsNotFoundRuntimeException("News with id [" + request.getId() + "] not found");
        }

        if (VALIDATOR.validate(request)) {
            NewsDto dto = new NewsDto();
            dto.setId(request.getId());
            dto.setTitle(request.getTitle());
            dto.setContent(request.getContent());

            dto.setAuthor(AUTHOR_SERVICE.readById(request.getAuthorId()));
            List<TagDto> tagDtos = new ArrayList<>();
            if (request.getTagsId() != null)
                for (int i = 0; i < request.getTagsId().size(); i++) {
                    tagDtos.add(TAG_SERVICE.readById(request.getTagsId().get(i)));
                }
            dto.setTagsId(tagDtos);
            //dto.setAuthorId(AUTHOR_SERVICE.readById(request.getAuthorId()));
            return MAPPER.modelToDto(REPOSITORY.update(MAPPER.dtoToModel(dto)));
        }
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return REPOSITORY.deleteById(id);
    }

    public AuthorDto getAuthorByNewsId(Long id) {
        return AUTHOR_SERVICE.readById(readById(id).getAuthor().getId());
    }

    public List<TagDto> getTagsByNewsId(Long id) {
        return readById(id).getTagsId();
    }

    public List<NewsDto> getNewsByParams(String tagNames, List<Long> tagIds, String authorName, String title, String content) {
        return MAPPER.modelToDtoList(REPOSITORY.getNewsByParams(tagNames, tagIds, authorName, title, content));
    }

    public void createTestDB() {
        List<Long> arr1 = new ArrayList<>();
        arr1.add(1L);
        arr1.add(2L);

        List<Long> arr2 = new ArrayList<>();
        arr2.add(4L);
        arr2.add(5L);
        arr2.add(7L);

        createSpecialNews("Lord Of Dawn", "Now is the winter of our discontent", 1L, arr1);
        createSpecialNews("Heir Of The Ancients", "Made glorious summer by this sun of York;", 1L, arr1);
        createSpecialNews("Pirates Of The Ancestors", "And all the clouds that lour'd upon our house", 3L, arr2);
        createSpecialNews("Rebels Of Earth", "In the deep bosom of the ocean buried.", 4L, arr1);
        createSpecialNews("Kings And Mice", "Now are our brows bound with victorious wreaths;", 5L, new ArrayList<>());
        createSpecialNews("Gods And Priests", "Our bruised arms hung up for monuments;", 6L, new ArrayList<>());
        createSpecialNews("Goal Of The Lost Ones", "Our stern alarums changed to merry meetings,", 7L, new ArrayList<>());
        createSpecialNews("End Of Nightmares", "Our dreadful marches to delightful measures.", 8L, new ArrayList<>());
        createSpecialNews("Begging In The Future", "Grim-visaged war hath smooth'd his wrinkled front;", 9L, new ArrayList<>());
        createSpecialNews("Flying Into The North", "I, that am curtail'd of this fair proportion,", 10L, new ArrayList<>());
        createSpecialNews("Hero Of Dawn", "Creator Of Tomorrow", 11L, new ArrayList<>());
        createSpecialNews("Human With Honor", "Priestess Without Desire", 12L, new ArrayList<>());
        createSpecialNews("Strangers Of The Night", "Giants Of The Light", 13L, new ArrayList<>());
        createSpecialNews("Officers With Vigor", "Boys Without Courage", 14L, new ArrayList<>());
        createSpecialNews("Serpents And Companions", "Lords And Knights", 15L, new ArrayList<>());
        createSpecialNews("Horses And Foreigners", "Wolves And Aliens", 16L, new ArrayList<>());
        createSpecialNews("Cause Of Tomorrow", "Planet Of Water", 17L, new ArrayList<>());
        createSpecialNews("Destiny Without Time", "Success Of The Void", 18L, new ArrayList<>());
        createSpecialNews("Healing My Nightmares", "Clinging To The Future", 19L, new ArrayList<>());
        createSpecialNews("Choking In Eternity", "Hurt By Nightmares", 20L, new ArrayList<>());
    }

    private NewsDto createSpecialNews(String title, String content, Long authorId, List<Long> tags_id) {
        NewsRequest request1 = new NewsRequest();
        request1.setTitle(title);
        request1.setContent(content);
        request1.setAuthorId(authorId);
        request1.setTagsId(tags_id);
        return create(request1);
    }
}
