package by.makedon.selectioncommittee.app.entity;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AdminEnrolleeForm {
    @EqualsAndHashCode.Include
    private String username;
    @EqualsAndHashCode.Include
    private String passportId;
    private String countryDomen;
    private String surname;
    private String name;
    private String secondName;
    private String phone;
    private String speciality;
    private int score;
}
