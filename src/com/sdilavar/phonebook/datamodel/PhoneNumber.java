package com.sdilavar.phonebook.datamodel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumber {
    private String number = "";

    public PhoneNumber(String number) {
        if (numberValidator(number)) {
            this.number = number;
        }
    }

    private static boolean numberValidator(String entryNumber) {
        String patterns = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";

        Pattern pattern = Pattern.compile(patterns);
        Matcher matcher = pattern.matcher(entryNumber);
        return matcher.matches();
    }


    public boolean isSpecified() {
        return !number.trim().isEmpty();
    }

    @Override
    public String toString() {
        return number;
    }
}
