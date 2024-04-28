package by.makedon.selectioncommittee.app.entity;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @EqualsAndHashCode.Include
    private String email;
    @EqualsAndHashCode.Include
    private String username;
    @ToString.Exclude
    private String password;

    @ToString.Include(name = "password")
    private String passwordMask() {
        return "*******";
    }
}
