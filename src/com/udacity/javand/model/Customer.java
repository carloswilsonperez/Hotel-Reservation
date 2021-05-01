package com.udacity.javand.model;

import java.util.regex.Pattern;

public class Customer {
    private String firstName;
    private String lastName;
    private String email;

    public Customer(String firstName, String lastName, String email) {
        // Validate if email has right format
        String emailRegex = "^(.+)@(.+).(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);

        if (!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Email has wrong format");
        }
        // If so, then initialize instance properties
        this.firstName = firstName.toLowerCase();
        this.lastName = lastName.toLowerCase();
        this.email = email.toLowerCase();
    }

    /**
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @return user email
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method overrides the equals method from the Object class
     *
     * @param obj the object to compare to
     * @return boolean indicating if the two objects are equal
     */
    @Override public boolean equals(Object obj)
    {
        Customer customer1 = (Customer)obj; // type casting object to the intended class type

        // checking if the two two
        // objects share all the same values
        return this.getFirstName().equals(customer1.getFirstName())
                && this.getLastName().equals(customer1.getLastName())
                && this.getEmail().equals(customer1.getEmail());
    }

    /**
     * @return customized string describing the instance
     */
    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
