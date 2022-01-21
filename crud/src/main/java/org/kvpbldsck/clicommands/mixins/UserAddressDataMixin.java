package org.kvpbldsck.clicommands.mixins;

import picocli.CommandLine.Parameters;

import javax.validation.constraints.Pattern;

public final class UserAddressDataMixin {
    @Parameters(description = "User last name")
    @Pattern(regexp = "^\\w{2,50}$", message = "Last name should consists only of letters and length should be between 2 and 50")
    private String lastName;

    @Parameters(description = "User first name")
    @Pattern(regexp = "^\\w{2,50}$", message = "First name should consists only of letters and length should be between 2 and 50")
    private String firstName;

    @Parameters(description = "User address")
    @Pattern(regexp = "^[\\w#(),.\\s]{5,100}$", message = "Address should consists only of letters, digits, commas, brackets, sharp and spaces and length should be between 5 and 100")
    private String address;

    @Parameters(description = "User phone")
    @Pattern(regexp = "^[\\d\\s()-]{5,20}$", message = "Phone number should consists only of digits, brackets, dashes and spaces  and length should be between 5 and 20")
    private String phone;

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}


