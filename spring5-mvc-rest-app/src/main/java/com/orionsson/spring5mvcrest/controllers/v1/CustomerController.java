package com.orionsson.spring5mvcrest.controllers.v1;

import com.orionsson.model.CustomerDTO;
import com.orionsson.model.CustomerListDTO;
import com.orionsson.spring5mvcrest.services.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(description = "My Beautiful Customer Controller")
@RestController
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {
    public final static String BASE_URL = "/api/v1/customers";
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ApiOperation(value = "Lists all customers", notes = "Development notes of API !!!")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CustomerListDTO getAllCustomers(){
        CustomerListDTO customerListDTO = new CustomerListDTO();
        customerListDTO.getCustomers().addAll(customerService.getAllCustomers());
        return customerListDTO;
    }

    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO getCustomerById(@PathVariable Long id){
        return customerService.getCustomerById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createCustomer(@RequestBody CustomerDTO customerDTO){
        return customerService.createCustomer(customerDTO);
    }

    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO updateCustomer(@PathVariable Long id,
                                      @RequestBody CustomerDTO customerDTO){
        return customerService.updateCustomerByDTO(id,customerDTO);
    }

    @PatchMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO patchCustomer(@PathVariable Long id,
                                     @RequestBody CustomerDTO customerDTO){
        return customerService.patchCustomerByDTO(id,customerDTO);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomerById(id);
    }
}
