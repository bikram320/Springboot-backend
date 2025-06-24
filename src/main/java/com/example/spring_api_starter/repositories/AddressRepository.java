package com.example.spring_api_starter.repositories;

import com.example.spring_api_starter.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}
