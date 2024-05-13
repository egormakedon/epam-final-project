package by.makedon.selectioncommittee.databasepopulator.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Yahor Makedon
 */
@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class User {
    @EqualsAndHashCode.Include
    private Integer userId;

    @EqualsAndHashCode.Include
    private String email;

    @EqualsAndHashCode.Include
    private String username;

    @ToString.Exclude
    private String password;

    @ToString.Exclude
    private Enrollee enrollee;

    @ToString.Include(name = "password")
    private String passwordMask() {
        return "*******";
    }

    @ToString.Include(name = "enrolleeId")
    private Integer enrolleeId() {
        return enrollee.getEnrolleeId();
    }
}
