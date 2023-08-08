package com.mjc.school.service.mapper;

import com.mjc.school.repository.implementation.model.AuthorModel;
import com.mjc.school.repository.implementation.model.NewsModel;
import com.mjc.school.repository.implementation.model.TagModel;
import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.dto.TagDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-08T14:46:47+0500",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.2.jar, environment: Java 17.0.4 (Oracle Corporation)"
)
public class NewsMapperImpl implements NewsMapper {

    @Override
    public NewsModel dtoToModel(NewsDto dto) {
        if ( dto == null ) {
            return null;
        }

        NewsModel newsModel = new NewsModel();

        newsModel.setId( dto.getId() );
        newsModel.setTitle( dto.getTitle() );
        newsModel.setContent( dto.getContent() );
        newsModel.setCreateDate( dto.getCreateDate() );
        newsModel.setLastUpdatedDate( dto.getLastUpdatedDate() );
        newsModel.setAuthor( authorDtoToAuthorModel( dto.getAuthor() ) );
        newsModel.setTagsId( tagDtoListToTagModelList( dto.getTagsId() ) );

        return newsModel;
    }

    @Override
    public List<NewsModel> dtoToModelList(List<NewsDto> dto) {
        if ( dto == null ) {
            return null;
        }

        List<NewsModel> list = new ArrayList<NewsModel>( dto.size() );
        for ( NewsDto newsDto : dto ) {
            list.add( dtoToModel( newsDto ) );
        }

        return list;
    }

    @Override
    public NewsDto modelToDto(NewsModel model) {
        if ( model == null ) {
            return null;
        }

        NewsDto newsDto = new NewsDto();

        newsDto.setId( model.getId() );
        newsDto.setTitle( model.getTitle() );
        newsDto.setContent( model.getContent() );
        newsDto.setCreateDate( model.getCreateDate() );
        newsDto.setLastUpdatedDate( model.getLastUpdatedDate() );
        newsDto.setAuthor( authorModelToAuthorDto( model.getAuthor() ) );
        newsDto.setTagsId( tagModelListToTagDtoList( model.getTagsId() ) );

        return newsDto;
    }

    @Override
    public List<NewsDto> modelToDtoList(List<NewsModel> model) {
        if ( model == null ) {
            return null;
        }

        List<NewsDto> list = new ArrayList<NewsDto>( model.size() );
        for ( NewsModel newsModel : model ) {
            list.add( modelToDto( newsModel ) );
        }

        return list;
    }

    protected AuthorModel authorDtoToAuthorModel(AuthorDto authorDto) {
        if ( authorDto == null ) {
            return null;
        }

        AuthorModel authorModel = new AuthorModel();

        authorModel.setId( authorDto.getId() );
        authorModel.setName( authorDto.getName() );
        authorModel.setCreateDate( authorDto.getCreateDate() );
        authorModel.setLastUpdatedDate( authorDto.getLastUpdatedDate() );

        return authorModel;
    }

    protected TagModel tagDtoToTagModel(TagDto tagDto) {
        if ( tagDto == null ) {
            return null;
        }

        TagModel tagModel = new TagModel();

        tagModel.setId( tagDto.getId() );
        tagModel.setName( tagDto.getName() );

        return tagModel;
    }

    protected List<TagModel> tagDtoListToTagModelList(List<TagDto> list) {
        if ( list == null ) {
            return null;
        }

        List<TagModel> list1 = new ArrayList<TagModel>( list.size() );
        for ( TagDto tagDto : list ) {
            list1.add( tagDtoToTagModel( tagDto ) );
        }

        return list1;
    }

    protected AuthorDto authorModelToAuthorDto(AuthorModel authorModel) {
        if ( authorModel == null ) {
            return null;
        }

        AuthorDto authorDto = new AuthorDto();

        authorDto.setId( authorModel.getId() );
        authorDto.setName( authorModel.getName() );
        authorDto.setCreateDate( authorModel.getCreateDate() );
        authorDto.setLastUpdatedDate( authorModel.getLastUpdatedDate() );

        return authorDto;
    }

    protected TagDto tagModelToTagDto(TagModel tagModel) {
        if ( tagModel == null ) {
            return null;
        }

        TagDto tagDto = new TagDto();

        tagDto.setId( tagModel.getId() );
        tagDto.setName( tagModel.getName() );

        return tagDto;
    }

    protected List<TagDto> tagModelListToTagDtoList(List<TagModel> list) {
        if ( list == null ) {
            return null;
        }

        List<TagDto> list1 = new ArrayList<TagDto>( list.size() );
        for ( TagModel tagModel : list ) {
            list1.add( tagModelToTagDto( tagModel ) );
        }

        return list1;
    }
}
