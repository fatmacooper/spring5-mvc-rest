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
    public static final String API_ROOT = "/api/v1/customers/";
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
                    customerDTO.setCustomerUrl(API_ROOT + customer.getId());
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
        customerDTO.setCustomerUrl(API_ROOT + Id.toString());
        return customerDTO;
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = customerRepository
                .save(customerMapper.customerDTOToCustomer(customerDTO));
        CustomerDTO savedCustomerDTO = customerMapper.customerToCustomerDTO(customer);
        savedCustomerDTO.setCustomerUrl(API_ROOT + customer.getId().toString());
        return savedCustomerDTO;
    }

    private CustomerDTO saveAndReturnDTO(Customer customer){
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO savedCustomerDTO = customerMapper.customerToCustomerDTO(savedCustomer);
        savedCustomerDTO.setCustomerUrl(API_ROOT + savedCustomer.getId());
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
                return customerMapper.customerToCustomerDTO(customerRepository.save(customer));
        }).orElseThrow(RuntimeException::new);
    }
}
