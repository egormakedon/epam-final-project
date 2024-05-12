package by.makedon.selectioncommittee.app.entity.enrolleeform;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

/**
 * @author Yahor Makedon
 */
@Data
@Builder(access = AccessLevel.PACKAGE)
class EnrolleeMark {
    private final byte russianLang;
    private final byte belorussianLang;
    private final byte physics;
    private final byte math;
    private final byte chemistry;
    private final byte biology;
    private final byte foreignLang;
    private final byte historyOfBelarus;
    private final byte socialStudies;
    private final byte geography;
    private final byte history;
    private final byte certificate;

    public int calculateScore() {
        return russianLang + belorussianLang + physics + math + chemistry + biology + foreignLang
            + historyOfBelarus + socialStudies + geography + history + certificate;
    }
}
