package by.makedon.selectioncommittee.validator;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class EnrolleeValidator {
    private static final String NAME_REGEXP = "^(([А-Я][а-я]{2,50})|([A-Z][a-z]{2,50}))$";
    private static final String PASSPORT_REGEXP = "^([A-Z0-9]{7,10})$";
    private static final String PHONE_REGEXP = "^(375(29|33|25)[0-9]{7})$";

    private static final int LIST_SIZE = 17;

    public static boolean validate(@NotNull List<String> parameters) {
        if (parameters.size() != LIST_SIZE) {
            return false;
        }

        String nameValue = parameters.get(0);
        String surnameValue = parameters.get(1);
        String secondNameValue = parameters.get(2);
        String passportIdValue = parameters.get(3);
        String phoneValue = parameters.get(4);
        String russianLangValue = parameters.get(5);
        String belorussianLangValue = parameters.get(6);
        String physicsValue = parameters.get(7);
        String mathValue = parameters.get(8);
        String chemistryValue = parameters.get(9);
        String biologyValue = parameters.get(10);
        String foreignLangValue = parameters.get(11);
        String historyOfBelarusValue = parameters.get(12);
        String socialStudiesValue = parameters.get(13);
        String geographyValue = parameters.get(14);
        String historyValue = parameters.get(15);
        String certificateValue = parameters.get(16);

        if (!validateName(secondNameValue)) {
            if (!secondNameValue.isEmpty()) {
                return false;
            }
        }

        boolean resultNameValidation = validateName(nameValue);
        boolean resultSurnameValidation = validateName(surnameValue);
        boolean resultPassportValidation = validatePassport(passportIdValue);
        boolean resultPhoneValidation = validatePhone(phoneValue);
        boolean resultNumbersValidation = validateNumbers(russianLangValue, belorussianLangValue, physicsValue,
                                                          mathValue, chemistryValue, biologyValue, foreignLangValue,
                                                          historyOfBelarusValue, socialStudiesValue, geographyValue,
                                                          historyValue, certificateValue);

        return resultNameValidation && resultSurnameValidation &&
                resultPassportValidation && resultPhoneValidation && resultNumbersValidation;
    }

    private static boolean validateName(@NotNull String nameValue) {
        return nameValue != null && Pattern.matches(NAME_REGEXP, nameValue);
    }
    private static boolean validatePassport(@NotNull String passportIdValue) {
        return passportIdValue != null && Pattern.matches(PASSPORT_REGEXP, passportIdValue);
    }
    private static boolean validatePhone(@NotNull String phoneValue) {
        return phoneValue != null && Pattern.matches(PHONE_REGEXP, phoneValue);
    }
    private static boolean validateNumbers(@NotNull String russianLangValue, @NotNull String belorussianLangValue, @NotNull String physicsValue,
                                           @NotNull String mathValue, @NotNull String chemistryValue, @NotNull String biologyValue,
                                           @NotNull String foreignLangValue, @NotNull String historyOfBelarusValue, @NotNull String socialStudiesValue,
                                           @NotNull String geographyValue, @NotNull String historyValue, @NotNull String certificateValue) {

        List<String> parameters = new ArrayList<String>();
        parameters.add(certificateValue);

        if (russianLangValue != null) {
            parameters.add(russianLangValue);
        }
        if (belorussianLangValue != null) {
            parameters.add(belorussianLangValue);
        }
        if (physicsValue != null) {
            parameters.add(physicsValue);
        }
        if (mathValue != null) {
            parameters.add(mathValue);
        }
        if (chemistryValue != null) {
            parameters.add(chemistryValue);
        }
        if (biologyValue != null) {
            parameters.add(biologyValue);
        }
        if (foreignLangValue != null) {
            parameters.add(foreignLangValue);
        }
        if (historyOfBelarusValue != null) {
            parameters.add(historyOfBelarusValue);
        }
        if (socialStudiesValue != null) {
            parameters.add(socialStudiesValue);
        }
        if (geographyValue != null) {
            parameters.add(geographyValue);
        }
        if (historyValue != null) {
            parameters.add(historyValue);
        }

        if (parameters.size() != 4) {
            return false;
        }

        for (String parameter : parameters) {
            if (!parseStringToInt(parameter)) {
                return false;
            }
        }

        return true;
    }

    private static boolean parseStringToInt(String parameter) {
        try {
            int num = Integer.parseInt(parameter);
            return num > 0 && num <= 100;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}