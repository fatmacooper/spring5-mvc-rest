package com.orionsson.spring5mvcrest.api.v1.mapper;

import com.orionsson.spring5mvcrest.api.v1.model.CustomerDTO;
import com.orionsson.spring5mvcrest.domain.Customer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CustomerMapperTest {

    CustomerMapper mapper = CustomerMapper.INSTANCE;

    @Test
    public void testCustomerToCustomerDTO() throws Exception{
        Customer customer = new Customer();
        customer.setId(1l);
        customer.setFirstname("kezban");
        customer.setLastname("arik");

        CustomerDTO customerDTO = mapper.customerToCustomerDTO(customer);
        assertEquals(customer.getFirstname(),customerDTO.getFirstname());
        assertEquals(customer.getLastname(),customerDTO.getLastname());
    }
}