package com.orionsson.spring5mvcrest.services;

import com.orionsson.spring5mvcrest.api.v1.mapper.CustomerMapper;
import com.orionsson.spring5mvcrest.api.v1.model.CustomerDTO;
import com.orionsson.spring5mvcrest.domain.Customer;
import com.orionsson.spring5mvcrest.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {
    private final Long ID = 1L;
    @Mock
    private CustomerRepository customerRepository;
    private CustomerService customerService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    public void testGetAllCustomers() {
        List<Customer> customers = Arrays.asList(new Customer(),new Customer());
        when(customerRepository.findAll()).thenReturn(customers);
        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();
        assertEquals(customerDTOS.size(),customers.size());
    }

    @Test
    public void testGetCustomerById() {
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstname("rcp");
        when(customerRepository.findById(anyLong())).thenReturn(Optional.ofNullable(customer));
        assertEquals(customerService.getCustomerById(ID).getFirstname(),customer.getFirstname());
    }

}