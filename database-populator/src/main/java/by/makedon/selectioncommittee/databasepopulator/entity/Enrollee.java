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
public class Enrollee {
    @EqualsAndHashCode.Include
    private Integer enrolleeId;

    @EqualsAndHashCode.Include
    @ToString.Exclude
    private String passportId;

    @EqualsAndHashCode.Include
    private String countryDomain;

    private Integer specialityId;
    private String surname;
    private String name;
    private String secondName;
    private String phoneNumber;

    private int russianLang;
    private int belorussianLang;
    private int physics;
    private int math;
    private int chemistry;
    private int biology;
    private int foreignLang;
    private int historyOfBelarus;
    private int socialStudies;
    private int geography;
    private int history;

    private Integer certificate;
    private String date;

    @ToString.Include(name = "passportId")
    private String passportIdMask() {
        return "*******";
    }
}
