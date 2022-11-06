package service;

import model.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Customer Service Class uses the model classes to implement a Map (dictionary) data structures that holds each Customer
 */
public class CustomerService {
//    Making sure that the CustomerService class is a Singleton Class that can only be instantiated once.
//    In order to do this I create a private constructor, static instance of the object and a getter method for that specific object
    private CustomerService() {}
    private static final CustomerService INSTANCE = new CustomerService();

    public static CustomerService getInstance() {
        return INSTANCE;
    }

//    Map used for storage and quick look-up of all Customers
    private Map<String, Customer> customers = new HashMap<>();

//    Method used to add Customer to Map
    public void addCustomer(String email, String firstName, String lastName) {
        Customer customer = new Customer(firstName,lastName,email);
        customers.put(email, customer);
    }
// Method uses Map for constant time look-up by using customer e-mail as key
    public Customer getCustomer(String customerEmail) {
        return customers.get(customerEmail);
    }

// Method returns every Customer object in the Map
    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }



}
