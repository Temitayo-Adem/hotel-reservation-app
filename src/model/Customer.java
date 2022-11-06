package model;

import java.util.regex.Pattern;

/**
 * Customer Class will instantiate Customer objects. It holds the e-mail, first, and last name of the customer.
 */
public class Customer {
//    Using private access modifier to ensure other classes cannot access data directly
    private String firstName;
    private String lastName;
    private String email;

//    Regular expression used to validate user e-mail
    private final String emailCheck = "^(?=.{1,64}@)[A-Za-z\\d_-]+(\\.[A-Za-z\\d_-]+)*@" +
            "[^-][A-Za-z\\d-]+(\\.[A-Za-z\\d-]+)*(\\.[A-Za-z]{2,})$";
    private final Pattern pattern = Pattern.compile(emailCheck);

//    Constructor
//    If the user has entered an invalid e-mail then the program will throw an exception
    public Customer(String firstName, String lastName, String email) {
        if (!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid Email.");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

//    Getter/Setter methods for variables

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

// Overriding toString method to make output more readable
    @Override
    public String toString() {
        return "First Name: " + firstName + " Last Name: " + lastName + " Email: " + email + "\n";
    }

}
