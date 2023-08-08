package com.mjc.school.service.mapper;

import com.mjc.school.repository.implementation.model.AuthorModel;
import com.mjc.school.service.dto.AuthorDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface AuthorMapper {
    AuthorModel dtoToModel(AuthorDto dto);

    List<AuthorModel> dtoToModelList(List<AuthorDto> dto);

    AuthorDto modelToDto(AuthorModel model);

    List<AuthorDto> modelToDtoList(List<AuthorModel> model);
}
