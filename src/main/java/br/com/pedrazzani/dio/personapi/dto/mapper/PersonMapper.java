package br.com.pedrazzani.dio.personapi.dto.mapper;

import br.com.pedrazzani.dio.personapi.dto.request.PersonDto;
import br.com.pedrazzani.dio.personapi.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mapping(source = "birthDate", target = "birthDate", dateFormat = "dd-MM-yyyy")
    Person toEntity(PersonDto dto);

    PersonDto fromEntity(Person person);
}
