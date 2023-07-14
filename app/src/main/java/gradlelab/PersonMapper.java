package gradlelab;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {

  PersonDto toDto(Person person);
  Person toEntity(PersonDto personDto);
}
