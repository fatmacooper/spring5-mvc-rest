package com.orionsson.spring5mvcrest.services;

import com.orionsson.spring5mvcrest.api.v1.mapper.CustomerMapper;
import com.orionsson.spring5mvcrest.api.v1.model.CustomerDTO;
import com.orionsson.spring5mvcrest.bootstrap.Bootstrap;
import com.orionsson.spring5mvcrest.domain.Customer;
import com.orionsson.spring5mvcrest.repositories.CategoryRepository;
import com.orionsson.spring5mvcrest.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerServiceITTest {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CategoryRepository categoryRepository;

    CustomerService customerService;

    @Before
    public void setup() throws Exception{
        Bootstrap bootstrap = new Bootstrap(categoryRepository,customerRepository);
        bootstrap.run();

        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }
    @Test
    public void testPatchCustomerUpdateFirstName() throws Exception {
        String updatedFirstName = "ftm";
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(updatedFirstName);
        CustomerDTO initialCustomerDTO = customerService.getCustomerById(getCustomerIdValue());
        assertNotNull(initialCustomerDTO);
        CustomerDTO savedCustomerDTO = customerService.patchCustomerByDTO(getCustomerIdValue(),customerDTO);
        assertEquals(savedCustomerDTO.getFirstname(),updatedFirstName);
        assertNotEquals(savedCustomerDTO.getFirstname(),initialCustomerDTO.getFirstname());
        assertEquals(savedCustomerDTO.getLastname(),initialCustomerDTO.getLastname());
    }

    @Test
    public void testPatchCustomerUpdateLastName() throws Exception {
        String updatedLastName = "cpr";
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastname(updatedLastName);
        CustomerDTO initialCustomerDTO = customerService.getCustomerById(getCustomerIdValue());
        assertNotNull(initialCustomerDTO);
        CustomerDTO savedCustomerDTO = customerService.patchCustomerByDTO(getCustomerIdValue(),customerDTO);
        assertEquals(savedCustomerDTO.getLastname(),updatedLastName);
        assertEquals(savedCustomerDTO.getFirstname(),initialCustomerDTO.getFirstname());
        assertNotEquals(savedCustomerDTO.getLastname(),initialCustomerDTO.getLastname());
    }
    private Long getCustomerIdValue(){
        List<Customer> customers = customerRepository.findAll();
        return customers.get(0).getId();
    }
}
