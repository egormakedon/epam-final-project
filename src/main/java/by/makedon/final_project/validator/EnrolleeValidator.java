package by.makedon.final_project.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class EnrolleeValidator {
    private static final String NAME_PATTERN = "^([а-яА-Я]{1,50})|([a-zA-Z]{1,50})&";
    private static final String PASSPORT_PATTERN = "^([A-Z0-9]{7,10})&";
    private static final String PHONE_PATTERN = "^375(29|33|25)[0-9]{7}$";

    public static boolean validate(String nameValue, String surnameValue, String secondNameValue, String passportIdValue,
                                   String phoneValue, String russianLangValue, String belorussianLangValue, String physicsValue,
                                   String mathValue, String chemistryValue, String biologyValue, String foreignLangValue,
                                   String historyOfBelarusValue, String socialStudiesValue, String geographyValue,
                                   String historyValue, String certificateValue) {
        if (!validateName(nameValue, surnameValue, secondNameValue)) {
            if (!secondNameValue.isEmpty()) {
                return false;
            }
        }

        if (!validatePassport(passportIdValue)) {
            return false;
        }

        if (!validatePhone(phoneValue)) {
            return false;
        }

        return validateNumbers(russianLangValue, belorussianLangValue, physicsValue, mathValue, chemistryValue, biologyValue,
                foreignLangValue, historyOfBelarusValue, socialStudiesValue, geographyValue, historyValue, certificateValue);
    }

    private static boolean validateName(String nameValue, String surnameValue, String secondNameValue) {
        return Pattern.matches(NAME_PATTERN, nameValue) && Pattern.matches(NAME_PATTERN, surnameValue) &&
                Pattern.matches(NAME_PATTERN, secondNameValue);
    }

    private static boolean validatePassport(String passportIdValue) {
        return Pattern.matches(PASSPORT_PATTERN, passportIdValue);
    }

    private static boolean validatePhone(String phoneValue) {
        return Pattern.matches(PHONE_PATTERN, phoneValue);
    }

    private static boolean validateNumbers(String russianLangValue, String belorussianLangValue, String physicsValue,
                                         String mathValue, String chemistryValue, String biologyValue, String foreignLangValue,
                                         String historyOfBelarusValue, String socialStudiesValue, String geographyValue,
                                         String historyValue, String certificateValue) {
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
