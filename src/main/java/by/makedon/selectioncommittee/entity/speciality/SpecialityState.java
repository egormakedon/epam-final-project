package by.makedon.selectioncommittee.entity.speciality;

import java.util.Objects;

public class SpecialityState {
    private String speciality;
    private int numberOfSeats;
    private int filledDocuments;

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public int getFilledDocuments() {
        return filledDocuments;
    }

    public void setFilledDocuments(int filledDocuments) {
        this.filledDocuments = filledDocuments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpecialityState)) return false;
        SpecialityState that = (SpecialityState) o;
        return getNumberOfSeats() == that.getNumberOfSeats() &&
                getFilledDocuments() == that.getFilledDocuments() &&
                Objects.equals(getSpeciality(), that.getSpeciality());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getSpeciality(), getNumberOfSeats(), getFilledDocuments());
    }
}
