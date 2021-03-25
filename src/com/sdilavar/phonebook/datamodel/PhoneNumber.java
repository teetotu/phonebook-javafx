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
        String patterns
                = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";

        Pattern pattern = Pattern.compile(patterns);
        Matcher matcher = pattern.matcher(entryNumber);
        return matcher.matches();
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        if (number.trim().isEmpty()) {
            res.append("Не установлен");
        } else {
            res.append(number);
        }
        return res.toString();
    }
}
