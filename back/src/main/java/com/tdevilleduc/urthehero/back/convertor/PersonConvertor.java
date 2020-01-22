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
        return modelMapper.map(person, PersonDTO.class);
    }

    public Person convertToEntity(PersonDTO personDto) {
        return modelMapper.map(personDto, Person.class);
    }
}
