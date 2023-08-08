package com.mjc.school.service.mapper;

import com.mjc.school.repository.implementation.model.TagModel;
import com.mjc.school.service.dto.TagDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface TagMapper {
    TagModel dtoToModel(TagDto dto);

    List<TagModel> dtoToModelList(List<TagDto> dto);

    TagDto modelToDto(TagModel model);

    List<TagDto> modelToDtoList(List<TagModel> model);
}
