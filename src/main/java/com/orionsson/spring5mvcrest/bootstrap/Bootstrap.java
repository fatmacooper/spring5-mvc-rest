package com.orionsson.spring5mvcrest.bootstrap;

import com.orionsson.spring5mvcrest.domain.Category;
import com.orionsson.spring5mvcrest.domain.Customer;
import com.orionsson.spring5mvcrest.domain.Vendor;
import com.orionsson.spring5mvcrest.repositories.CategoryRepository;
import com.orionsson.spring5mvcrest.repositories.CustomerRepository;
import com.orionsson.spring5mvcrest.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository,
                     VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategories();
        loadCustomers();
        loadVendors();
    }
    private void loadCategories(){
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        System.out.println("Total saved items " + categoryRepository.count());
    }
    private void loadCustomers(){
        //given
        Customer customer1 = new Customer();
        customer1.setId(1l);
        customer1.setFirstname("Michale");
        customer1.setLastname("Weston");
        customerRepository.save(customer1);

        Customer customer2 = new Customer();
        customer2.setId(2l);
        customer2.setFirstname("Sam");
        customer2.setLastname("Axe");

        customerRepository.save(customer2);

        System.out.println("Customers Loaded: " + customerRepository.count());
    }

    private void loadVendors(){
        Vendor vendor1 = new Vendor();
        vendor1.setId(1L);
        vendor1.setName("Lambert Vet Supply");
        vendorRepository.save(vendor1);

        Vendor vendor2 = new Vendor();
        vendor2.setId(2L);
        vendor2.setName("Healthy Pets");
        vendorRepository.save(vendor2);

        Vendor vendor3 = new Vendor();
        vendor3.setId(3L);
        vendor3.setName("Kong");
        vendorRepository.save(vendor3);

        Vendor vendor4 = new Vendor();
        vendor4.setId(4L);
        vendor4.setName("Phillips");
        vendorRepository.save(vendor4);

        System.out.println("Vendors Loaded: " + vendorRepository.count());
    }
}
