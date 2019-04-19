package com.orionsson.spring5mvcrest.controllers.v1;

import com.orionsson.spring5mvcrest.api.v1.model.CustomerDTO;
import com.orionsson.spring5mvcrest.domain.Customer;
import com.orionsson.spring5mvcrest.repositories.CustomerRepository;
import com.orionsson.spring5mvcrest.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static com.orionsson.spring5mvcrest.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class CustomerControllerTest {

    MockMvc mockMvc;

    @Mock
    CustomerService customerService;
    @InjectMocks
    CustomerController customerController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void testGetAllCustomers() throws Exception {
        //given
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setFirstname("ftm");
        customer1.setLastname("cpr");
        customer1.setCustomerUrl("/api/v1/customers/1");

        CustomerDTO customer2 = new CustomerDTO();
        customer2.setFirstname("rcp");
        customer2.setLastname("cpr");
        customer2.setCustomerUrl("/api/v1/customers/2");

        when(customerService.getAllCustomers()).thenReturn(Arrays.asList(customer1,customer2));
        //then
        mockMvc.perform(get("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    public void testGetCustomerById() throws Exception {
        //given
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setFirstname("ftm");
        customer1.setLastname("cpr");
        customer1.setCustomerUrl("/api/v1/customers/1");
        when(customerService.getCustomerById(anyLong())).thenReturn(customer1);
        //then
        mockMvc.perform(get("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname",equalTo("ftm")));
    }
    @Test
    public void testCreateCustomer() throws Exception{
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname("ftm");
        customer.setLastname("cpr");
        customer.setCustomerUrl("/api/v1/customers/1");
        when(customerService.createCustomer(any())).thenReturn(customer);
        //then
        mockMvc.perform(post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", equalTo("ftm")))
                .andExpect(jsonPath("$.customer_url",equalTo("/api/v1/customers/1")));
    }
    @Test
    public void testUpdateCustomer() throws Exception{
        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname("ftm");
        customer.setLastname("cpr");
        customer.setCustomerUrl("/api/v1/customers/1");
        when(customerService.updateCustomerByDTO(anyLong(),any(CustomerDTO.class))).thenReturn(customer);
        //then
        mockMvc.perform(put("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname",equalTo("ftm")))
                .andExpect(jsonPath("$.customer_url",equalTo("/api/v1/customers/1")));
    }

    @Test
    public void testPatchCustomer() throws Exception{
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("rcp");

        CustomerDTO savedCustomerDTO = new CustomerDTO();
        savedCustomerDTO.setFirstname(customerDTO.getFirstname());
        savedCustomerDTO.setCustomerUrl("/api/v1/customers/1");
        when(customerService.patchCustomerByDTO(anyLong(),any(CustomerDTO.class))).thenReturn(savedCustomerDTO);
        //then
        mockMvc.perform(patch("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname",equalTo(customerDTO.getFirstname())))
                .andExpect(jsonPath("$.customer_url",equalTo("/api/v1/customers/1")));
    }

    @Test
    public void testDeleteCustomer() throws Exception{
        mockMvc.perform(delete("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(customerService,times(1)).deleteCustomerById(anyLong());
    }
}