package by.makedon.selectioncommittee.app.entity.enrollee;

import java.util.Objects;

public class AdminEnrolleeForm {
    private String username;
    private String passportId;
    private String countryDomen;
    private String surname;
    private String name;
    private String secondname;
    private String phone;
    private String speciality;
    private int score;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassportId() {
        return passportId;
    }

    public void setPassportId(String passportId) {
        this.passportId = passportId;
    }

    public String getCountryDomen() {
        return countryDomen;
    }

    public void setCountryDomen(String countryDomen) {
        this.countryDomen = countryDomen;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdminEnrolleeForm)) return false;
        AdminEnrolleeForm that = (AdminEnrolleeForm) o;
        return getScore() == that.getScore() &&
                Objects.equals(getUsername(), that.getUsername()) &&
                Objects.equals(getPassportId(), that.getPassportId()) &&
                Objects.equals(getCountryDomen(), that.getCountryDomen()) &&
                Objects.equals(getSurname(), that.getSurname()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getSecondname(), that.getSecondname()) &&
                Objects.equals(getPhone(), that.getPhone()) &&
                Objects.equals(getSpeciality(), that.getSpeciality());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getUsername(), getPassportId(), getCountryDomen(), getSurname(), getName(), getSecondname(), getPhone(), getSpeciality(), getScore());
    }
}
