package by.makedon.selectioncommittee.app.entity;

import lombok.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EnrolleeState {
    private static final String STATEMENT_ENLISTED = "Зачислен";
    private static final String STATEMENT_NOT_LISTED = "Не зачислен";

    @EqualsAndHashCode.Include
    private final long enrolleeId;
    @EqualsAndHashCode.Include
    private final long specialityId;
    private final short score;
    private final String date;

    @Setter(value = AccessLevel.NONE)
    private String statement;

    public void setStatementEnlisted() {
        statement = STATEMENT_ENLISTED;
    }

    public void setStatementNotListed() {
        statement = STATEMENT_NOT_LISTED;
    }
}
