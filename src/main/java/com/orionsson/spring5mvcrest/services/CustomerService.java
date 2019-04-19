package com.orionsson.spring5mvcrest.services;

import com.orionsson.spring5mvcrest.api.v1.model.CustomerDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> getAllCustomers();
    CustomerDTO getCustomerById(Long Id);
    CustomerDTO createCustomer(CustomerDTO customerDTO);
    CustomerDTO updateCustomerByDTO(Long id, CustomerDTO customerDTO);
    CustomerDTO patchCustomerByDTO(Long id, CustomerDTO customerDTO);
    void deleteCustomerById(Long id);
}
