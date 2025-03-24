package com.example.AddressBookApp.repository;

import com.example.AddressBookApp.model.AddressBookModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressBookModel,Long> {
}
