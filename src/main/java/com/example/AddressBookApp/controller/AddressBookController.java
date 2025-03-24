package com.example.AddressBookApp.controller;

import com.example.AddressBookApp.dto.AddressBookDTO;
import com.example.AddressBookApp.dto.ResponseDTO;
import com.example.AddressBookApp.Interface.AddressBookServiceInterface;
import com.example.AddressBookApp.service.RabbitMQProducerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/addressbook")
public class AddressBookController {

    @Autowired
    AddressBookServiceInterface service;

    @Autowired
    RabbitMQProducerService rabbitMQProducerService;

    // Get all contacts
    @GetMapping("/showcontacts")
    public ResponseEntity<List<AddressBookDTO>> getAllContacts() {
        return ResponseEntity.ok(service.getAllContacts());
    }

    // Get a single contact by ID
    @GetMapping("/getbyid/{id}")
    public ResponseEntity<AddressBookDTO> getContactById(@PathVariable Long id) {
        AddressBookDTO contact = service.getContactById(id);
        return (contact != null) ? ResponseEntity.ok(contact) : ResponseEntity.notFound().build();
    }

    // Create a new contact (Validation Applied)
    @PostMapping("/create")
    public ResponseEntity<?> createContact(@Valid @RequestBody AddressBookDTO dto) {
        AddressBookDTO createdContact = service.saveContact(dto);
        return ResponseEntity.ok(new ResponseDTO("Contact created successfully", createdContact));
    }

    // Update an existing contact (Validation Applied)
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateContact(@PathVariable Long id, @Valid @RequestBody AddressBookDTO dto) {
        AddressBookDTO updatedContact = service.updateContact(id, dto);
        return (updatedContact != null)
                ? ResponseEntity.ok(new ResponseDTO("Contact updated successfully", updatedContact))
                : ResponseEntity.notFound().build();
    }

    // Delete a contact
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        return (service.deleteContact(id))
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @PostMapping("/send-message")
    public ResponseEntity<String> sendMessage(@RequestBody String message) {
        rabbitMQProducerService.sendMessage(message);
        return ResponseEntity.ok("Message sent to RabbitMQ: " + message);
    }

}