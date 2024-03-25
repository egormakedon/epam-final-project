package by.makedon.selectioncommittee.databasepopulator.entity.factory;

import by.makedon.selectioncommittee.databasepopulator.entity.Enrollee;
import by.makedon.selectioncommittee.databasepopulator.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Yahor Makedon
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserFactory {
    private static final UserFactory instance = new UserFactory();

    public static User getUserBy(Enrollee enrollee) {
        return instance.createUserBy(enrollee);
    }

    private User createUserBy(Enrollee enrollee) {
        int enrolleeId = enrollee.getEnrolleeId();
        return User
            .builder()
            .email(getEmailBy(enrolleeId))
            .username(getUsernameBy(enrolleeId))
            .password(getUsernameBy(enrolleeId))
            .enrollee(enrollee)
            .build();
    }

    private String getEmailBy(int enrolleeId) {
        return String.format("email_%d@gmail.com", enrolleeId);
    }

    private String getUsernameBy(int enrolleeId) {
        return String.format("user_%d", enrolleeId);
    }
}
