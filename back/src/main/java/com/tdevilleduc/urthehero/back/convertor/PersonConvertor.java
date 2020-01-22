package com.tdevilleduc.urthehero.back.convertor;

import com.tdevilleduc.urthehero.back.model.Person;
import com.tdevilleduc.urthehero.back.model.PersonDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonConvertor {

    @Autowired
    private ModelMapper modelMapper;

    public PersonDTO convertToDto(Person person) {
        PersonDTO personDto = modelMapper.map(person, PersonDTO.class);
        return personDto;
    }

    public Person convertToEntity(PersonDTO personDto) {
        Person person = modelMapper.map(personDto, Person.class);
        return person;
    }
}
