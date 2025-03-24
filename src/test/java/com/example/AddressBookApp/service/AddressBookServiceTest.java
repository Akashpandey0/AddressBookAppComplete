package com.example.AddressBookApp.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.AddressBookApp.dto.AddressBookDTO;
import com.example.AddressBookApp.exception.UserException;
import com.example.AddressBookApp.repository.AddressRepository;
import com.example.AddressBookApp.model.AddressBookModel;
import com.example.AddressBookApp.service.AddressBookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressBookServiceTest {

    @Mock
    private AddressRepository repository;

    @InjectMocks
    private AddressBookService service;

    private AddressBookModel sampleContact;
    private AddressBookDTO sampleContactDTO;

    @BeforeEach
    void setUp() {
        sampleContact = new AddressBookModel(1L, "John Doe", "9876543210", "john@example.com");
        sampleContactDTO = new AddressBookDTO(1L, "John Doe", "9876543210", "john@example.com");
    }

    @Test
    void getAllContacts_ShouldReturnContacts() {
        List<AddressBookModel> contactList = new ArrayList<>();
        contactList.add(sampleContact);

        when(repository.findAll()).thenReturn(contactList);

        List<AddressBookDTO> result = service.getAllContacts();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("john@example.com", result.get(0).getEmail());
    }

    @Test
    void saveContact_ShouldSaveSuccessfully() {
        when(repository.save(any(AddressBookModel.class))).thenReturn(sampleContact);

        AddressBookDTO result = service.saveContact(sampleContactDTO);

        assertEquals("John Doe", result.getName());
        assertEquals("9876543210", result.getPhone());
        assertEquals("john@example.com", result.getEmail());
    }

    @Test
    void getContactById_ShouldReturnContact() {
        when(repository.findById(1L)).thenReturn(Optional.of(sampleContact));

        AddressBookDTO result = service.getContactById(1L);

        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());
    }

    @Test
    void updateContact_ShouldUpdateSuccessfully() {
        AddressBookDTO updatedDTO = new AddressBookDTO(1L, "Jane Doe", "1234567890", "jane@example.com");

        when(repository.findById(1L)).thenReturn(Optional.of(sampleContact));
        when(repository.save(any(AddressBookModel.class))).thenReturn(new AddressBookModel(1L, "Jane Doe", "1234567890", "jane@example.com"));

        AddressBookDTO result = service.updateContact(1L, updatedDTO);

        assertEquals("Jane Doe", result.getName());
        assertEquals("1234567890", result.getPhone());
        assertEquals("jane@example.com", result.getEmail());
    }

    @Test
    void deleteContact_ShouldDeleteSuccessfully() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        assertTrue(service.deleteContact(1L));
    }
}