package com.mjc.school.service.mapper;

import com.mjc.school.repository.implementation.model.NewsModel;
import com.mjc.school.service.dto.NewsDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface NewsMapper {
    NewsModel dtoToModel(NewsDto dto);

    List<NewsModel> dtoToModelList(List<NewsDto> dto);

    NewsDto modelToDto(NewsModel model);

    List<NewsDto> modelToDtoList(List<NewsModel> model);
}
