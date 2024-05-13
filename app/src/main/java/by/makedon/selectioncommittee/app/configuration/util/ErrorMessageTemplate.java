package by.makedon.selectioncommittee.app.configuration.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Yahor Makedon
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorMessageTemplate {
    public static final String USER_NOT_EXIST_WITH_USERNAME = "User does not exist by provided username: [%s]";
    public static final String USER_NOT_EXIST_WITH_EMAIL = "User does not exist by provided email: [%s]";
    public static final String ENROLLEE_FORM_NOT_FOUND_WITH_USERNAME = "Enrollee Form has not been found by provided username: [%s]";
    public static final String ENROLLEE_FORM_NOT_FOUND_WITH_USERNAME_SPECIALITY = "Enrollee Form has not been found by provided username: [%s] and speciality: [%s]";
    public static final String SPECIALITY_NOT_FOUND_WITH_SPECIALITY = "Speciality has not been found by provided speciality: [%s]";
}
