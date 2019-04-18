package com.orionsson.spring5mvcrest.services;

import com.orionsson.spring5mvcrest.api.v1.mapper.CustomerMapper;
import com.orionsson.spring5mvcrest.api.v1.model.CustomerDTO;
import com.orionsson.spring5mvcrest.domain.Customer;
import com.orionsson.spring5mvcrest.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CustomerServiceImpl implements CustomerService{
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository
                .findAll()
                .stream()
                .map(customer->{
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    customerDTO.setCustomerUrl("/api/v1/customers/" + customer.getId());
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }
    @Override
    public CustomerDTO getCustomerById(Long Id) {
        Optional<Customer> customer = customerRepository.findById(Id);
        if(customer == null){
            throw new RuntimeException("Customer cannot be found!!!");
        }
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer.get());
        customerDTO.setCustomerUrl("/api/v1/customers/" + Id.toString());
        return customerDTO;
    }
}
