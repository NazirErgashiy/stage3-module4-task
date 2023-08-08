package com.mjc.school.service.mapper;

import com.mjc.school.repository.implementation.model.TagModel;
import com.mjc.school.service.dto.TagDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-08T14:46:47+0500",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.2.jar, environment: Java 17.0.4 (Oracle Corporation)"
)
public class TagMapperImpl implements TagMapper {

    @Override
    public TagModel dtoToModel(TagDto dto) {
        if ( dto == null ) {
            return null;
        }

        TagModel tagModel = new TagModel();

        tagModel.setId( dto.getId() );
        tagModel.setName( dto.getName() );

        return tagModel;
    }

    @Override
    public List<TagModel> dtoToModelList(List<TagDto> dto) {
        if ( dto == null ) {
            return null;
        }

        List<TagModel> list = new ArrayList<TagModel>( dto.size() );
        for ( TagDto tagDto : dto ) {
            list.add( dtoToModel( tagDto ) );
        }

        return list;
    }

    @Override
    public TagDto modelToDto(TagModel model) {
        if ( model == null ) {
            return null;
        }

        TagDto tagDto = new TagDto();

        tagDto.setId( model.getId() );
        tagDto.setName( model.getName() );

        return tagDto;
    }

    @Override
    public List<TagDto> modelToDtoList(List<TagModel> model) {
        if ( model == null ) {
            return null;
        }

        List<TagDto> list = new ArrayList<TagDto>( model.size() );
        for ( TagModel tagModel : model ) {
            list.add( modelToDto( tagModel ) );
        }

        return list;
    }
}
