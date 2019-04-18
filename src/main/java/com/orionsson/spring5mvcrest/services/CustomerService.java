package com.orionsson.spring5mvcrest.services;

import com.orionsson.spring5mvcrest.api.v1.model.CustomerDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> getAllCustomers();
    CustomerDTO getCustomerById(Long Id);
}
