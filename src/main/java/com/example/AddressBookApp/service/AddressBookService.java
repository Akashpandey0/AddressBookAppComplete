package com.example.AddressBookApp.service;

import com.example.AddressBookApp.dto.AddressBookDTO;
import com.example.AddressBookApp.exception.UserException;
import com.example.AddressBookApp.Interface.AddressBookServiceInterface;
import com.example.AddressBookApp.repository.AddressRepository;
import com.example.AddressBookApp.model.AddressBookModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AddressBookService implements AddressBookServiceInterface {

    @Autowired
    private AddressRepository repository;

    // ===================== GET ALL CONTACTS =====================
    @Override
    @Cacheable(value = "contacts", key = "#root.methodName")
    public List<AddressBookDTO> getAllContacts() {
        try {
            log.info("Fetching all contacts from the database.");

            return repository.findAll().stream()
                    .filter(contact -> contact.getName() != null && contact.getPhone() != null && contact.getEmail() != null)  // Filter out null entries
                    .map(contact -> new AddressBookDTO(
                            contact.getId(),
                            contact.getName(),
                            contact.getPhone(),
                            contact.getEmail())
                    ).collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Error fetching contacts: {}", e.getMessage());
            throw new UserException("Failed to fetch contacts. Please try again.");
        }
    }

    // ===================== SAVE CONTACT =====================
    @Override
    @CacheEvict(value = "contacts", allEntries = true) // Clears cache after save
    public AddressBookDTO saveContact(AddressBookDTO dto) {
        log.info("Saving new contact: {}", dto);

        AddressBookModel contact = new AddressBookModel();
        contact.setName(dto.getName());
        contact.setPhone(dto.getPhone());
        contact.setEmail(dto.getEmail());
        AddressBookModel savedContact = repository.save(contact);

        log.info("✅ Contact saved successfully with ID: {}", savedContact.getId());
        return new AddressBookDTO(savedContact.getId(), savedContact.getName(), savedContact.getPhone(), savedContact.getEmail());
    }

    // ===================== GET CONTACT BY ID =====================
    @Override
    @Cacheable(value = "contacts", key = "#id")
    public AddressBookDTO getContactById(Long id) {
        log.info("Fetching contact with ID: {}", id);

        Optional<AddressBookModel> contact = repository.findById(id);

        if (contact.isEmpty()) {
            log.warn("❗ Contact with ID {} not found.", id);
            throw new UserException("Contact not found with ID: " + id);
        }

        return new AddressBookDTO(contact.get().getId(), contact.get().getName(), contact.get().getPhone(), contact.get().getEmail());
    }

    // ===================== UPDATE CONTACT =====================
    @Override
    @CachePut(value = "contacts", key = "'contactList'")
    public AddressBookDTO updateContact(Long id, AddressBookDTO dto) {
        log.info("Updating contact with ID: {}", id);

        AddressBookModel contact = repository.findById(id)
                .orElseThrow(() -> new UserException("Contact not found with ID: " + id));

        contact.setName(dto.getName());
        contact.setPhone(dto.getPhone());
        contact.setEmail(dto.getEmail());
        AddressBookModel updatedContact = repository.save(contact);

        log.info("✅ Contact updated successfully: {}", updatedContact);
        return new AddressBookDTO(updatedContact.getId(), updatedContact.getName(), updatedContact.getPhone(), updatedContact.getEmail());
    }

    // ===================== DELETE CONTACT =====================
    @Override
    @CacheEvict(value = "contacts", allEntries = true)
    public boolean deleteContact(Long id) {
        log.info("Deleting contact with ID: {}", id);

        if (!repository.existsById(id)) {
            log.warn("❗ Attempted to delete non-existing contact with ID: {}", id);
            throw new UserException("Contact not found with ID: " + id);
        }

        repository.deleteById(id);
        log.info("✅ Contact with ID {} deleted successfully.", id);

        return true;
    }
}