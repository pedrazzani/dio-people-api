package br.com.pedrazzani.dio.personapi.service;

import br.com.pedrazzani.dio.personapi.dto.mapper.PersonMapper;
import br.com.pedrazzani.dio.personapi.dto.request.PersonDto;
import br.com.pedrazzani.dio.personapi.dto.request.PhoneDto;
import br.com.pedrazzani.dio.personapi.entity.Person;
import br.com.pedrazzani.dio.personapi.entity.Phone;
import br.com.pedrazzani.dio.personapi.enums.PhoneType;
import br.com.pedrazzani.dio.personapi.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @InjectMocks
    private PersonService personService;
    @Mock
    private PersonRepository personRepository;
    @Mock
    private PersonMapper personMapper;

    @Test
    void findAll() {
        Person personFake = createPersonFake();
        PersonDto personDtoFake = createPersonDtoFake();

        when(personRepository.findAll()).thenReturn(List.of(personFake));
        when(personMapper.fromEntity(personFake)).thenReturn(personDtoFake);

        Optional<List<PersonDto>> personDtoList = personService.findAll();

        assertThat(personDtoList).isNotEmpty();
        assertThat(personDtoList.get().get(0).getId()).isEqualTo(1L);
        assertThat(personDtoList.get().get(0).getFirstName()).isEqualTo("FirstName");
        assertThat(personDtoList.get().get(0).getLastName()).isEqualTo("LastName");
        assertThat(personDtoList.get().get(0).getCpf()).isEqualTo("11111111111");
        assertThat(personDtoList.get().get(0).getBirthDate()).isEqualTo("01-01-2021");
        assertThat(personDtoList.get().get(0).getPhones()).isNotEmpty();
        assertThat(personDtoList.get().get(0).getPhones().get(0).getId()).isOne();
        assertThat(personDtoList.get().get(0).getPhones().get(0).getType()).isEqualTo(PhoneType.HOME);
        assertThat(personDtoList.get().get(0).getPhones().get(0).getNumber()).isEqualTo("(11)111111111");
    }

    @Test
    void findAllEmptyList() {
        when(personRepository.findAll()).thenReturn(List.of());
        Optional<List<PersonDto>> personDtoList = personService.findAll();
        assertThat(personDtoList).isEmpty();
    }

    @Test
    void createPersonWithSuccessful() {
        PersonDto personDtoFake = createPersonDtoFake();
        personDtoFake.setId(null);
        PersonDto personDtoFakeSaved = createPersonDtoFake();
        Person personFakeToSave = createPersonFake();
        personFakeToSave.setId(null);
        Person personFakeSaved = createPersonFake();

        when(personMapper.toEntity(personDtoFake)).thenReturn(personFakeToSave);
        when(personRepository.save(personFakeToSave)).thenReturn(personFakeSaved);
        when(personMapper.fromEntity(personFakeSaved)).thenReturn(personDtoFakeSaved);
        Optional<PersonDto> personDtoOptional = personService.createPerson(personDtoFake);

        assertThat(personDtoOptional).isNotEmpty();
        assertThat(personDtoOptional.get().getId()).isOne();
        assertThat(personDtoOptional.get()).isEqualTo(personDtoFakeSaved);
    }

    @Test
    void doNotCreatePerson() {
        Optional<PersonDto> personDtoOptional = personService.createPerson(null);
        assertThat(personDtoOptional).isEmpty();
    }

    @Test
    void findByIdWithSuccessful() {
        Person personFake = createPersonFake();
        PersonDto personDtoFake = createPersonDtoFake();
        when(personRepository.findById(1L)).thenReturn(Optional.of(personFake));
        when(personMapper.fromEntity(personFake)).thenReturn(personDtoFake);
        Optional<PersonDto> personDtoOptional = personService.findById(1L);

        assertThat(personDtoOptional).isNotEmpty();
        assertThat(personDtoOptional.get()).isEqualTo(personDtoFake);
    }

    @Test
    void doNotFindById() {
        when(personRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<PersonDto> personDtoOptional = personService.findById(1L);

        assertThat(personDtoOptional).isEmpty();
    }

    @Test
    void delete() {
        Person personFake = createPersonFake();
        when(personRepository.findById(1L)).thenReturn(Optional.of(personFake));
        assertThatCode(() -> personService.delete(1L))
                .doesNotThrowAnyException();
    }

    @Test
    void doThrowWhenDelete() {
        when(personRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatCode(() -> personService.delete(1L))
                .hasMessage("Person not found.");
    }

    @Test
    void updateWithSuccessful() {
        PersonDto personDtoFakeToUpdate = createPersonDtoFake();
        personDtoFakeToUpdate.setLastName("NewLastName");
        Person personFake = createPersonFake();
        Person newPersonFake = createPersonFake();
        newPersonFake.setLastName("NewLastName");

        when(personRepository.findById(1L)).thenReturn(Optional.of(personFake));
        when(personMapper.toEntity(personDtoFakeToUpdate)).thenReturn(newPersonFake);

        personService.update(1L, personDtoFakeToUpdate);

        verify(personRepository).save(newPersonFake);
    }

    private Person createPersonFake() {
        return Person.builder()
                .id(1L)
                .cpf("11111111111")
                .firstName("FirstName")
                .lastName("LastName")
                .birthDate(LocalDate.of(2021, Month.JANUARY.getValue(), 1))
                .phones(List.of(Phone.builder().id(1L).type(PhoneType.HOME).number("(11)111111111").build()))
                .build();
    }

    private PersonDto createPersonDtoFake() {
        return PersonDto.builder()
                .id(1L)
                .cpf("11111111111")
                .firstName("FirstName")
                .lastName("LastName")
                .birthDate("01-01-2021")
                .phones(List.of(PhoneDto.builder().id(1L).type(PhoneType.HOME).number("(11)111111111").build()))
                .build();
    }

}