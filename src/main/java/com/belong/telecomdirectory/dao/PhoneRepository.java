package com.belong.telecomdirectory.dao;

import com.belong.telecomdirectory.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Customer, Long> {
}
