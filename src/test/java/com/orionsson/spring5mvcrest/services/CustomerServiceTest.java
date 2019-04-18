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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {
    private final Long ID = 1L;
    CustomerMapper customerMapper;
    @Mock
    private CustomerRepository customerRepository;
    private CustomerService customerService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        customerMapper = CustomerMapper.INSTANCE;
        customerService = new CustomerServiceImpl(customerRepository, customerMapper);

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

    @Test
    public void testCreateCustomer(){
        //given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstname("ftm");
        customer.setLastname("cpr");
        when(customerRepository.save(any())).thenReturn(customer);
        //then
        CustomerDTO customerDTO = customerService
                .createCustomer(customerMapper.customerToCustomerDTO(customer));

        assertEquals(customer.getFirstname(),customerDTO.getFirstname());
        assertEquals(customer.getLastname(),customer.getLastname());
        assertEquals(customerDTO.getCustomerUrl(),"/api/v1/customers/1");
    }
}