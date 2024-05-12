package by.makedon.selectioncommittee.app.validator;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public final class EnrolleeValidator {
    private static final String NAME_REGEXP = "^(([А-Я][а-я]{2,50})|([A-Z][a-z]{2,50}))$";
    private static final String PASSPORT_REGEXP = "^([A-Z0-9]{7,10})$";
    private static final String PHONE_REGEXP = "^(375(29|33|25)[0-9]{7})$";
    private static final int VALID_PARAMETERS_SIZE = 17;
    private static final String ERROR_FIELD_TEMPLATE = "%s=`%s`";

    @NotNull
    public List<String> validate(@NotNull List<String> parameters) {
        Objects.requireNonNull(parameters);
        if (parameters.size() != VALID_PARAMETERS_SIZE) {
            return Collections.singletonList(String.format(
                "Invalid input parameters size: expected=`%d`, actual=`%d`", VALID_PARAMETERS_SIZE, parameters.size()));
        }
        return validateParameters(parameters);
    }

    private static List<String> validateParameters(List<String> parameters) {
        List<String> errors = new ArrayList<>();
        validateName(parameters, errors::add);
        validatePassport(parameters, errors::add);
        validatePhone(parameters, errors::add);
        validateMarks(parameters, errors::add);
        return errors;
    }

    private static void validateName(List<String> parameters, Consumer<String> addToErrorsConsumer) {
        String nameValue = parameters.get(0);
        String surnameValue = parameters.get(1);
        String secondNameValue = parameters.get(2);

        if (isNameNotValid(nameValue)) {
            addToErrorsConsumer.accept(String.format(ERROR_FIELD_TEMPLATE, "name", nameValue));
        }

        if (isNameNotValid(surnameValue)) {
            addToErrorsConsumer.accept(String.format(ERROR_FIELD_TEMPLATE, "surname", surnameValue));
        }

        boolean isSecondNameEmpty = secondNameValue == null || secondNameValue.isEmpty();
        if (!isSecondNameEmpty) {
            if (isNameNotValid(secondNameValue)) {
                addToErrorsConsumer.accept(String.format(ERROR_FIELD_TEMPLATE, "secondName", secondNameValue));
            }
        }
    }

    private static boolean isNameNotValid(String nameValue) {
        return !isNameValid(nameValue);
    }

    private static boolean isNameValid(String nameValue) {
        return nameValue != null && Pattern.matches(NAME_REGEXP, nameValue);
    }

    private static void validatePassport(List<String> parameters, Consumer<String> addToErrorsConsumer) {
        String passportIdValue = parameters.get(3);
        if (isPassportNotValid(passportIdValue)) {
            addToErrorsConsumer.accept(String.format(ERROR_FIELD_TEMPLATE, "passportId", passportIdValue));
        }
    }

    private static boolean isPassportNotValid(String passportIdValue) {
        return !isPassportValid(passportIdValue);
    }

    private static boolean isPassportValid(String passportIdValue) {
        return passportIdValue != null && Pattern.matches(PASSPORT_REGEXP, passportIdValue);
    }

    private static void validatePhone(List<String> parameters, Consumer<String> addToErrorsConsumer) {
        String phoneValue = parameters.get(4);
        if (isPhoneNotValid(phoneValue)) {
            addToErrorsConsumer.accept(String.format(ERROR_FIELD_TEMPLATE, "phone", phoneValue));
        }
    }

    private static boolean isPhoneNotValid(String phoneValue) {
        return !isPhoneValid(phoneValue);
    }

    private static boolean isPhoneValid(String phoneValue) {
        return phoneValue != null && Pattern.matches(PHONE_REGEXP, phoneValue);
    }

    private static void validateMarks(List<String> parameters, Consumer<String> addToErrorsConsumer) {
        String certificateValue = parameters.get(16);
        validateCertificate(certificateValue, addToErrorsConsumer);

        List<String> markValues = extractMarkValuesFrom(parameters);
        validateMarkValues(markValues, addToErrorsConsumer);
    }

    private static void validateCertificate(String certificateValue, Consumer<String> addToErrorsConsumer) {
        if (certificateValue == null) {
            addToErrorsConsumer.accept("certificate is required");
        } else if (isMarkNotValid(certificateValue)) {
            addToErrorsConsumer.accept(String.format(ERROR_FIELD_TEMPLATE, "certificate", certificateValue));
        }
    }

    private static List<String> extractMarkValuesFrom(List<String> parameters) {
        //5-15
        return parameters.subList(5, 16);
    }

    private static void validateMarkValues(List<String> markValues, Consumer<String> addToErrorsConsumer) {
        markValues = markValues
            .stream()
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        if (markValues.size() == 3) {
            markValues.forEach(markValue -> {
                if (isMarkNotValid(markValue)) {
                    addToErrorsConsumer.accept(String.format(ERROR_FIELD_TEMPLATE, "mark", markValue));
                }
            });
        } else {
            final String message = String.format(
                "only [3] subject marks must be submitted, but provided [%d]", markValues.size());
            addToErrorsConsumer.accept(message);
        }
    }

    private static boolean isMarkNotValid(String markValue) {
        return !isMarkValid(markValue);
    }

    private static boolean isMarkValid(String markValue) {
        try {
            int mark = Integer.parseInt(markValue);
            return mark > 0 && mark <= 100;
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            return false;
        }
    }
}
