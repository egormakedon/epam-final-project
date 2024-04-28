package by.makedon.selectioncommittee.app.entity.enrolleeform;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

/**
 * @author Yahor Makedon
 */
@Data
@Builder(access = AccessLevel.PACKAGE)
class UniversityInfo {
    private final String university;
    private final String faculty;
    private final String speciality;
}
