package by.makedon.selectioncommittee.app.logic.user;

import by.makedon.selectioncommittee.app.dao.BaseDao;
import by.makedon.selectioncommittee.app.dao.UserDao;
import by.makedon.selectioncommittee.app.dao.impl.BaseDaoImpl;
import by.makedon.selectioncommittee.app.dao.impl.UserDaoImpl;
import by.makedon.selectioncommittee.app.entity.enrolleeform.EnrolleeForm;
import by.makedon.selectioncommittee.app.entity.enrolleeform.NullEnrolleeForm;
import by.makedon.selectioncommittee.app.logic.Logic;
import by.makedon.selectioncommittee.app.logic.LogicException;
import by.makedon.selectioncommittee.app.validator.UserValidator;
import by.makedon.selectioncommittee.app.validator.ValidationException;
import by.makedon.selectioncommittee.common.dao.DaoException;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static by.makedon.selectioncommittee.app.configuration.util.ErrorMessageTemplate.INVALID_INPUT_PARAMETER_WITH_PARAMETER_VALUE;
import static by.makedon.selectioncommittee.app.configuration.util.ErrorMessageTemplate.USER_NOT_EXIST_WITH_USERNAME;

public class ShowFormLogic implements Logic {
    private static final String PARAMETER_KEY_UNIVERSITY = "UNIVERSITY";
    private static final String PARAMETER_KEY_FACULTY = "FACULTY";
    private static final String PARAMETER_KEY_SPECIALITY = "SPECIALITY";
    private static final String PARAMETER_KEY_NAME = "NAME";
    private static final String PARAMETER_KEY_SURNAME = "SURNAME";
    private static final String PARAMETER_KEY_SECONDNAME = "SECONDNAME";
    private static final String PARAMETER_KEY_PASSPORTID = "PASSPORTID";
    private static final String PARAMETER_KEY_COUNTRYDOMEN = "COUNTRYDOMEN";
    private static final String PARAMETER_KEY_PHONE = "PHONE";
    private static final String PARAMETER_KEY_DATE = "DATE";
    private static final String PARAMETER_KEY_SCORE = "SCORE";

    private static final int VALID_PARAMETERS_SIZE = 1;

    private final BaseDao baseDao = BaseDaoImpl.getInstance();
    private final UserDao userDao = UserDaoImpl.getInstance();
    private final UserValidator userValidator = new UserValidator();

    private EnrolleeForm enrolleeForm = NullEnrolleeForm.getInstance();

    @Override
    public int getValidParametersSize() {
        return VALID_PARAMETERS_SIZE;
    }

    @Override
    public void validate(@NotNull List<String> parameters) throws ValidationException {
        String usernameValue = parameters.get(0);
        if (!userValidator.validateUsername(usernameValue)) {
            final String message = String.format(
                INVALID_INPUT_PARAMETER_WITH_PARAMETER_VALUE, "username", usernameValue);
            throw new ValidationException(message);
        }
        if (!baseDao.matchUsername(usernameValue)) {
            final String message = String.format(USER_NOT_EXIST_WITH_USERNAME, usernameValue);
            throw new ValidationException(message);
        }
    }

    @Override
    public void action(@NotNull List<String> parameters) throws DaoException, LogicException {
        enrolleeForm = NullEnrolleeForm.getInstance();

        String username = parameters.get(0);
        enrolleeForm = userDao.getEnrolleeFormByUsername(username);
    }

    public void updateServletRequest(HttpServletRequest request) {
        request.setAttribute(PARAMETER_KEY_UNIVERSITY, enrolleeForm.getUniversity());
        request.setAttribute(PARAMETER_KEY_FACULTY, enrolleeForm.getFaculty());
        request.setAttribute(PARAMETER_KEY_SPECIALITY, enrolleeForm.getSpeciality());
        request.setAttribute(PARAMETER_KEY_NAME, enrolleeForm.getName());
        request.setAttribute(PARAMETER_KEY_SURNAME, enrolleeForm.getSurname());
        request.setAttribute(PARAMETER_KEY_SECONDNAME, enrolleeForm.getSecondName());
        request.setAttribute(PARAMETER_KEY_PASSPORTID, enrolleeForm.getPassportId());
        request.setAttribute(PARAMETER_KEY_COUNTRYDOMEN, enrolleeForm.getCountryDomen());
        request.setAttribute(PARAMETER_KEY_PHONE, enrolleeForm.getPhone());
        request.setAttribute(PARAMETER_KEY_DATE, enrolleeForm.getDate());
        request.setAttribute(PARAMETER_KEY_SCORE, enrolleeForm.getScore());
    }
}
