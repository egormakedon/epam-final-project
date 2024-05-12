package by.makedon.selectioncommittee.app.logic.user;

import by.makedon.selectioncommittee.app.dao.BaseDao;
import by.makedon.selectioncommittee.app.dao.UserDao;
import by.makedon.selectioncommittee.app.dao.impl.BaseDaoImpl;
import by.makedon.selectioncommittee.app.dao.impl.UserDaoImpl;
import by.makedon.selectioncommittee.app.entity.enrolleeform.EnrolleeForm;
import by.makedon.selectioncommittee.app.logic.Logic;
import by.makedon.selectioncommittee.app.logic.LogicException;
import by.makedon.selectioncommittee.app.validator.EnrolleeValidator;
import by.makedon.selectioncommittee.app.validator.UserValidator;
import by.makedon.selectioncommittee.app.validator.ValidationException;
import by.makedon.selectioncommittee.common.dao.DaoException;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SendFormLogic implements Logic {
    private static final int VALID_PARAMETERS_SIZE = 22;
    private static final String DATE_PATTERN = "yyyy-MM-dd";

    private final BaseDao baseDao = BaseDaoImpl.getInstance();
    private final UserDao userDao = UserDaoImpl.getInstance();
    private final UserValidator userValidator = new UserValidator();
    private final EnrolleeValidator enrolleeValidator = new EnrolleeValidator();

    @Override
    public void validate(@NotNull List<String> parameters) throws ValidationException {
        if (parameters.size() != VALID_PARAMETERS_SIZE) {
            final String message = String.format(
                "Invalid input parameters size: expected=`%d`, actual=`%d`", VALID_PARAMETERS_SIZE, parameters.size());
            throw new ValidationException(message);
        }

        String usernameValue = parameters.get(0);
        if (!userValidator.validateUsername(usernameValue)) {
            final String message = String.format("Invalid input username parameter: `%s`", usernameValue);
            throw new ValidationException(message);
        }
        if (!baseDao.matchUsername(usernameValue)) {
            final String message = String.format("Such user with username:`%s` does not exist", usernameValue);
            throw new ValidationException(message);
        }

        validateEnrolleeFormInputParameters(parameters);

        if (!userDao.isStatementInProcess()) {
            throw new ValidationException("Send enrollee form action is declined: statement is not in process!!");
        }

        if (userDao.isUserEnrolleeFormInserted(usernameValue)) {
            final String message = String.format(
                "Enrollee form for the user: `%s` has already been submitted!!", usernameValue);
            throw new ValidationException(message);
        }
    }

    private void validateEnrolleeFormInputParameters(List<String> parameters) throws ValidationException {
        String nameValue = parameters.get(4);
        String surnameValue = parameters.get(5);
        String secondNameValue = parameters.get(6);
        String passportIdValue = parameters.get(7);
        String phoneValue = parameters.get(9);
        String russianLangValue = parameters.get(10);
        String belorussianLangValue = parameters.get(11);
        String physicsValue = parameters.get(12);
        String mathValue = parameters.get(13);
        String chemistryValue = parameters.get(14);
        String biologyValue = parameters.get(15);
        String foreignLangValue = parameters.get(16);
        String historyOfBelarusValue = parameters.get(17);
        String socialStudiesValue = parameters.get(18);
        String geographyValue = parameters.get(19);
        String historyValue = parameters.get(20);
        String certificateValue = parameters.get(21);

        List<String> enrolleeParametersForValidation = Arrays.asList(nameValue, surnameValue, secondNameValue, passportIdValue,
            phoneValue, russianLangValue, belorussianLangValue, physicsValue, mathValue, chemistryValue, biologyValue,
            foreignLangValue, historyOfBelarusValue, socialStudiesValue, geographyValue, historyValue, certificateValue);

        List<String> errors = enrolleeValidator.validate(enrolleeParametersForValidation);
        if (!errors.isEmpty()) {
            final String message = String.format("Invalid input enrolleeForm parameters:\n%s",
                errors.stream().map(error -> String.format("<%s>", error)).collect(Collectors.joining(", ")));
            throw new ValidationException(message);
        }
    }

    @Override
    public void action(@NotNull List<String> parameters) throws DaoException, LogicException {
        String username = parameters.get(0);
        EnrolleeForm enrolleeForm = extractEnrolleeFormFrom(parameters);
        userDao.saveEnrolleeFormByUsername(username, enrolleeForm);
    }

    private static EnrolleeForm extractEnrolleeFormFrom(List<String> parameters) {
        String universityValue = parameters.get(1);
        String facultyValue = parameters.get(2);
        String specialityValue = parameters.get(3);
        String nameValue = parameters.get(4);
        String surnameValue = parameters.get(5);
        String secondNameValue = parameters.get(6);
        String passportIdValue = parameters.get(7);
        String countryDomenValue = parameters.get(8);
        String phoneValue = parameters.get(9);
        String russianLangValue = parameters.get(10);
        String belorussianLangValue = parameters.get(11);
        String physicsValue = parameters.get(12);
        String mathValue = parameters.get(13);
        String chemistryValue = parameters.get(14);
        String biologyValue = parameters.get(15);
        String foreignLangValue = parameters.get(16);
        String historyOfBelarusValue = parameters.get(17);
        String socialStudiesValue = parameters.get(18);
        String geographyValue = parameters.get(19);
        String historyValue = parameters.get(20);
        String certificateValue = parameters.get(21);

        EnrolleeForm.EnrolleeFormBuilder enrolleeFormBuilder = EnrolleeForm
            .builder()
            .university(universityValue)
            .faculty(facultyValue)
            .speciality(specialityValue)
            .passportId(passportIdValue)
            .countryDomen(countryDomenValue)
            .surname(surnameValue)
            .name(nameValue)
            .secondName(secondNameValue == null ? "" : secondNameValue)
            .phone(phoneValue)
            .certificate(Byte.parseByte(certificateValue))
            .date(LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN)));

        if (russianLangValue != null) {
            enrolleeFormBuilder.russianLang(Byte.parseByte(russianLangValue));
        }
        if (belorussianLangValue != null) {
            enrolleeFormBuilder.belorussianLang(Byte.parseByte(belorussianLangValue));
        }
        if (physicsValue != null) {
            enrolleeFormBuilder.physics(Byte.parseByte(physicsValue));
        }
        if (mathValue != null) {
            enrolleeFormBuilder.math(Byte.parseByte(mathValue));
        }
        if (chemistryValue != null) {
            enrolleeFormBuilder.chemistry(Byte.parseByte(chemistryValue));
        }
        if (biologyValue != null) {
            enrolleeFormBuilder.biology(Byte.parseByte(biologyValue));
        }
        if (foreignLangValue != null) {
            enrolleeFormBuilder.foreignLang(Byte.parseByte(foreignLangValue));
        }
        if (historyOfBelarusValue != null) {
            enrolleeFormBuilder.historyOfBelarus(Byte.parseByte(historyOfBelarusValue));
        }
        if (socialStudiesValue != null) {
            enrolleeFormBuilder.socialStudies(Byte.parseByte(socialStudiesValue));
        }
        if (geographyValue != null) {
            enrolleeFormBuilder.geography(Byte.parseByte(geographyValue));
        }
        if (historyValue != null) {
            enrolleeFormBuilder.history(Byte.parseByte(historyValue));
        }

        return enrolleeFormBuilder.build();
    }
}
