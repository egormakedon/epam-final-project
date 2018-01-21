package by.makedon.final_project.entity;

import java.util.Objects;

public class EnrolleeState {
    private long enrolleeId;
    private long specialityId;
    private int score;

    public EnrolleeState(long enrolleeId, long specialityId, int score) {
        this.enrolleeId = enrolleeId;
        this.specialityId = specialityId;
        this.score = score;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EnrolleeState)) return false;
        EnrolleeState that = (EnrolleeState) o;
        return getEnrolleeId() == that.getEnrolleeId() &&
                getSpecialityId() == that.getSpecialityId() &&
                getScore() == that.getScore();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEnrolleeId(), getSpecialityId(), getScore());
    }

    @Override
    public String toString() {
        return "EnrolleeState{" +
                "enrolleeId=" + enrolleeId +
                ", specialityId=" + specialityId +
                ", score=" + score +
                '}';
    }
}
