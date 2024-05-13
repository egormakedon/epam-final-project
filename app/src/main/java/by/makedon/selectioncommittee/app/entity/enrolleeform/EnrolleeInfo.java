package by.makedon.selectioncommittee.app.entity.enrolleeform;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

/**
 * @author Yahor Makedon
 */
@Data
@Builder(access = AccessLevel.PACKAGE)
class EnrolleeInfo {
    private final String name;
    private final String surname;
    private final String secondName;
    private final String passportId;
    private final String countryDomen;
    private final String phone;
    private final String date;
}
