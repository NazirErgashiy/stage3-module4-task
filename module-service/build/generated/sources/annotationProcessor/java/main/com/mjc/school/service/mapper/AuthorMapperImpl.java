package com.mjc.school.service.mapper;

import com.mjc.school.repository.implementation.model.AuthorModel;
import com.mjc.school.service.dto.AuthorDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-08T14:46:47+0500",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.2.jar, environment: Java 17.0.4 (Oracle Corporation)"
)
public class AuthorMapperImpl implements AuthorMapper {

    @Override
    public AuthorModel dtoToModel(AuthorDto dto) {
        if ( dto == null ) {
            return null;
        }

        AuthorModel authorModel = new AuthorModel();

        authorModel.setId( dto.getId() );
        authorModel.setName( dto.getName() );
        authorModel.setCreateDate( dto.getCreateDate() );
        authorModel.setLastUpdatedDate( dto.getLastUpdatedDate() );

        return authorModel;
    }

    @Override
    public List<AuthorModel> dtoToModelList(List<AuthorDto> dto) {
        if ( dto == null ) {
            return null;
        }

        List<AuthorModel> list = new ArrayList<AuthorModel>( dto.size() );
        for ( AuthorDto authorDto : dto ) {
            list.add( dtoToModel( authorDto ) );
        }

        return list;
    }

    @Override
    public AuthorDto modelToDto(AuthorModel model) {
        if ( model == null ) {
            return null;
        }

        AuthorDto authorDto = new AuthorDto();

        authorDto.setId( model.getId() );
        authorDto.setName( model.getName() );
        authorDto.setCreateDate( model.getCreateDate() );
        authorDto.setLastUpdatedDate( model.getLastUpdatedDate() );

        return authorDto;
    }

    @Override
    public List<AuthorDto> modelToDtoList(List<AuthorModel> model) {
        if ( model == null ) {
            return null;
        }

        List<AuthorDto> list = new ArrayList<AuthorDto>( model.size() );
        for ( AuthorModel authorModel : model ) {
            list.add( modelToDto( authorModel ) );
        }

        return list;
    }
}
