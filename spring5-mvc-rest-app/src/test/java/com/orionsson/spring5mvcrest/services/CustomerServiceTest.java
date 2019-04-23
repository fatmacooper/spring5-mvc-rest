package com.orionsson.spring5mvcrest.services;

import com.orionsson.model.CustomerDTO;
import com.orionsson.spring5mvcrest.api.v1.mapper.CustomerMapper;
import com.orionsson.spring5mvcrest.controllers.v1.CustomerController;
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
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

public class CustomerServiceTest{
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
        //given
        Customer customer1 = new Customer();
        customer1.setId(1l);
        customer1.setFirstname("Michale");
        customer1.setLastname("Weston");

        Customer customer2 = new Customer();
        customer2.setId(2l);
        customer2.setFirstname("Sam");
        customer2.setLastname("Axe");

        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));
        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();
        assertEquals(customerDTOS.size(),2);
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
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        //then
        CustomerDTO customerDTO = customerService
                .createCustomer(customerMapper.customerToCustomerDTO(customer));

        assertEquals(customer.getFirstname(),customerDTO.getFirstname());
        assertEquals(customer.getLastname(),customerDTO.getLastname());
        assertEquals(customerDTO.getCustomerUrl(), CustomerController.BASE_URL + "/1");
    }

    @Test
    public void testUpdateCustomer(){
        //given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstname("ftm");
        customer.setLastname("cpr");

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("ftm");
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        //then
        CustomerDTO savedCustomerDTO = customerService
                .updateCustomerByDTO(
                1L,customerDTO);
        assertEquals(savedCustomerDTO.getFirstname(),customer.getFirstname());
        assertEquals(savedCustomerDTO.getCustomerUrl(),CustomerController.BASE_URL + "/1");
    }

    @Test
    public void testDeleteCustomer(){
        Long id = 1L;
        customerService.deleteCustomerById(id);
        verify(customerRepository,times(1)).deleteById(anyLong());
    }
}