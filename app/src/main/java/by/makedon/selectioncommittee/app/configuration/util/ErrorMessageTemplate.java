package by.makedon.selectioncommittee.app.configuration.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Yahor Makedon
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorMessageTemplate {
    public static final String USER_NOT_EXIST_WITH_USERNAME = "User does not exist by provided username: '%s'";
    public static final String USER_NOT_EXIST_WITH_EMAIL = "User does not exist by provided email: '%s'";
    public static final String USER_EXISTS_WITH_EMAIL_USERNAME = "Such User already exists by provided email: '%s' and username: '%s'";
    public static final String ENROLLEE_FORM_NOT_FOUND_WITH_USERNAME = "Enrollee Form has not been found by provided username: '%s'";
    public static final String ENROLLEE_FORM_NOT_FOUND_WITH_USERNAME_SPECIALITY = "Enrollee Form has not been found by provided username: '%s' and speciality: '%s'";
    public static final String SPECIALITY_NOT_FOUND_WITH_SPECIALITY = "Speciality has not been found by provided speciality: '%s'";
    public static final String INVALID_INPUT_PARAMETERS_SIZE_WITH_EXPECTED_ACTUAL = "Invalid input parameters size: expected='%d', actual='%d'";
    public static final String INVALID_INPUT_PARAMETER_WITH_PARAMETER_VALUE = "Invalid input '%s' parameter: '%s'";
    public static final String ACTION_DENIED_WITH_NAME_USERNAME = "Action '%s' has been denied for User: '%s'";
}
