package com.orionsson.spring5mvcrest.services;

import com.orionsson.spring5mvcrest.api.v1.mapper.CustomerMapper;
import com.orionsson.spring5mvcrest.api.v1.model.CustomerDTO;
import com.orionsson.spring5mvcrest.controllers.v1.CustomerController;
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
                    customerDTO.setCustomerUrl(CustomerController.BASE_URL);
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
        customerDTO.setCustomerUrl(getCustomerURL(Id));
        return customerDTO;
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = customerRepository
                .save(customerMapper.customerDTOToCustomer(customerDTO));
        CustomerDTO savedCustomerDTO = customerMapper.customerToCustomerDTO(customer);
        savedCustomerDTO.setCustomerUrl(getCustomerURL(customer.getId()));
        return savedCustomerDTO;
    }

    private CustomerDTO saveAndReturnDTO(Customer customer){
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO savedCustomerDTO = customerMapper.customerToCustomerDTO(savedCustomer);
        savedCustomerDTO.setCustomerUrl(getCustomerURL(customer.getId()));
        return savedCustomerDTO;
    }

    @Override
    public CustomerDTO updateCustomerByDTO(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        customer.setId(id);
        return saveAndReturnDTO(customer);
    }

    @Override
    public CustomerDTO patchCustomerByDTO(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id).map(customer -> {
                if(customerDTO.getFirstname()!=null){
                    customer.setFirstname(customerDTO.getFirstname());
                }
                if(customerDTO.getLastname() != null){
                    customer.setLastname(customerDTO.getLastname());
                }
                CustomerDTO savedCustomerDTO = customerMapper.customerToCustomerDTO(customerRepository.save(customer));
                savedCustomerDTO.setCustomerUrl(getCustomerURL(id));
                return savedCustomerDTO;
        }).orElseThrow(RuntimeException::new);
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }

    private String getCustomerURL(Long id){
        return CustomerController.BASE_URL +"/" +id.toString();
    }
}
