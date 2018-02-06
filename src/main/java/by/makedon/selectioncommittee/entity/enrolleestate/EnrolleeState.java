package by.makedon.selectioncommittee.entity.enrolleestate;

import java.util.Objects;

public class EnrolleeState {
    private long enrolleeId;
    private long specialityId;
    private short score;
    private String statement;
    private String date;

    public EnrolleeState(long enrolleeId, long specialityId, short score, String date) {
        this.enrolleeId = enrolleeId;
        this.specialityId = specialityId;
        this.score = score;
    }

    public String getStatement() {
        return statement;
    }
    public void setStatement(String statement) {
        this.statement = statement;
    }

    public long getEnrolleeId() {
        return enrolleeId;
    }
    public long getSpecialityId() {
        return specialityId;
    }
    public int getScore() {
        return score;
    }
    public String getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EnrolleeState)) return false;
        EnrolleeState that = (EnrolleeState) o;
        return getEnrolleeId() == that.getEnrolleeId() &&
                getSpecialityId() == that.getSpecialityId() &&
                getScore() == that.getScore() &&
                Objects.equals(getStatement(), that.getStatement()) &&
                Objects.equals(getDate(), that.getDate());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getEnrolleeId(), getSpecialityId(), getScore(), getStatement(), getDate());
    }
}
