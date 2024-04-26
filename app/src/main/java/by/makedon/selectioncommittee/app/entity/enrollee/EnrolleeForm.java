package by.makedon.selectioncommittee.app.entity.enrollee;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Objects;

public class EnrolleeForm {
    private static final Logger LOGGER = LogManager.getLogger(EnrolleeForm.class);

    private UniversityInfo universityInfo;
    private EnrolleeInfo enrolleInfo;
    private EnrolleeMark enrolleeMark;

    public class UniversityInfo {
        private String university;
        private String faculty;
        private String speciality;

        private UniversityInfo() {}

        public String getUniversity() {
            return university;
        }
        public String getFaculty() {
            return faculty;
        }
        public String getSpeciality() {
            return speciality;
        }
        private void setUniversity(String university) {
            this.university = university;
        }
        private void setFaculty(String faculty) {
            this.faculty = faculty;
        }
        private void setSpeciality(String speciality) {
            this.speciality = speciality;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof UniversityInfo)) return false;
            UniversityInfo that = (UniversityInfo) o;
            return Objects.equals(getUniversity(), that.getUniversity()) &&
                    Objects.equals(getFaculty(), that.getFaculty()) &&
                    Objects.equals(getSpeciality(), that.getSpeciality());
        }

        @Override
        public int hashCode() {

            return Objects.hash(getUniversity(), getFaculty(), getSpeciality());
        }
    }
    public class EnrolleeInfo {
        private String name;
        private String surname;
        private String secondName;
        private String passportId;
        private String countryDomen;
        private String phone;
        private String date;

        private EnrolleeInfo() {}

        public String getName() {
            return name;
        }
        public String getSurname() {
            return surname;
        }
        public String getSecondName() {
            return secondName;
        }
        public String getPassportId() {
            return passportId;
        }
        public String getCountryDomen() {
            return countryDomen;
        }
        public String getPhone() {
            return phone;
        }
        private void setName(String name) {
            this.name = name;
        }
        private void setSurname(String surname) {
            this.surname = surname;
        }
        private void setSecondName(String secondName) {
            this.secondName = secondName;
        }
        private void setPassportId(String passportId) {
            this.passportId = passportId;
        }
        private void setCountryDomen(String countryDomen) {
            this.countryDomen = countryDomen;
        }
        private void setPhone(String phone) {
            this.phone = phone;
        }
        public String getDate() {
            return date;
        }
        public void setDate(String date) {
            this.date = date;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof EnrolleeInfo)) return false;
            EnrolleeInfo that = (EnrolleeInfo) o;
            return Objects.equals(getName(), that.getName()) &&
                    Objects.equals(getSurname(), that.getSurname()) &&
                    Objects.equals(getSecondName(), that.getSecondName()) &&
                    Objects.equals(getPassportId(), that.getPassportId()) &&
                    Objects.equals(getCountryDomen(), that.getCountryDomen()) &&
                    Objects.equals(getPhone(), that.getPhone()) &&
                    Objects.equals(getDate(), that.getDate());
        }

        @Override
        public int hashCode() {

            return Objects.hash(getName(), getSurname(), getSecondName(), getPassportId(), getCountryDomen(), getPhone(), getDate());
        }
    }
    public class EnrolleeMark {
        private byte russianLang;
        private byte belorussianLang;
        private byte physics;
        private byte math;
        private byte chemistry;
        private byte biology;
        private byte foreignLang;
        private byte historyOfBelarus;
        private byte socialStudies;
        private byte geography;
        private byte history;
        private byte certificate;

        public byte getRussianLang() {
            return russianLang;
        }
        public byte getBelorussianLang() {
            return belorussianLang;
        }
        public byte getPhysics() {
            return physics;
        }
        public byte getMath() {
            return math;
        }
        public byte getChemistry() {
            return chemistry;
        }
        public byte getBiology() {
            return biology;
        }
        public byte getForeignLang() {
            return foreignLang;
        }
        public byte getHistoryOfBelarus() {
            return historyOfBelarus;
        }
        public byte getSocialStudies() {
            return socialStudies;
        }
        public byte getGeography() {
            return geography;
        }
        public byte getHistory() {
            return history;
        }
        public byte getCertificate() {
            return certificate;
        }
        private void setRussianLang(byte russianLang) {
            this.russianLang = russianLang;
        }
        private void setBelorussianLang(byte belorussianLang) {
            this.belorussianLang = belorussianLang;
        }
        private void setPhysics(byte physics) {
            this.physics = physics;
        }
        private void setMath(byte math) {
            this.math = math;
        }
        private void setChemistry(byte chemistry) {
            this.chemistry = chemistry;
        }
        private void setBiology(byte biology) {
            this.biology = biology;
        }
        private void setForeignLang(byte foreignLang) {
            this.foreignLang = foreignLang;
        }
        private void setHistoryOfBelarus(byte historyOfBelarus) {
            this.historyOfBelarus = historyOfBelarus;
        }
        private void setSocialStudies(byte socialStudies) {
            this.socialStudies = socialStudies;
        }
        private void setGeography(byte geography) {
            this.geography = geography;
        }
        private void setHistory(byte history) {
            this.history = history;
        }
        private void setCertificate(byte certificate) {
            this.certificate = certificate;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof EnrolleeMark)) return false;
            EnrolleeMark that = (EnrolleeMark) o;
            return getRussianLang() == that.getRussianLang() &&
                    getBelorussianLang() == that.getBelorussianLang() &&
                    getPhysics() == that.getPhysics() &&
                    getMath() == that.getMath() &&
                    getChemistry() == that.getChemistry() &&
                    getBiology() == that.getBiology() &&
                    getForeignLang() == that.getForeignLang() &&
                    getHistoryOfBelarus() == that.getHistoryOfBelarus() &&
                    getSocialStudies() == that.getSocialStudies() &&
                    getGeography() == that.getGeography() &&
                    getHistory() == that.getHistory() &&
                    getCertificate() == that.getCertificate();
        }

        @Override
        public int hashCode() {

            return Objects.hash(getRussianLang(), getBelorussianLang(), getPhysics(), getMath(), getChemistry(), getBiology(), getForeignLang(), getHistoryOfBelarus(), getSocialStudies(), getGeography(), getHistory(), getCertificate());
        }
    }

    private class Factory {
        void set(Map.Entry<EnrolleeFormCriteria, String> entry) {
            String value = entry.getValue();
            switch (entry.getKey()) {
                case MATH:
                    enrolleeMark.setMath(Byte.parseByte(value));
                    break;
                case NAME:
                    enrolleInfo.setName(value);
                    break;
                case PHONE:
                    enrolleInfo.setPhone(value);
                    break;
                case BIOLOGY:
                    enrolleeMark.setBiology(Byte.parseByte(value));
                    break;
                case FACULTY:
                    universityInfo.setFaculty(value);
                    break;
                case HISTORY:
                    enrolleeMark.setHistory(Byte.parseByte(value));
                    break;
                case PHYSICS:
                    enrolleeMark.setPhysics(Byte.parseByte(value));
                    break;
                case SURNAME:
                    enrolleInfo.setSurname(value);
                    break;
                case CHEMISTRY:
                    enrolleeMark.setChemistry(Byte.parseByte(value));
                    break;
                case GEOGRAPHY:
                    enrolleeMark.setGeography(Byte.parseByte(value));
                    break;
                case PASSPORTID:
                    enrolleInfo.setPassportId(value);
                    break;
                case SECONDNAME:
                    enrolleInfo.setSecondName(value);
                    break;
                case SPECIALITY:
                    universityInfo.setSpeciality(value);
                    break;
                case UNIVERSITY:
                    universityInfo.setUniversity(value);
                    break;
                case CERTIFICATE:
                    enrolleeMark.setCertificate(Byte.parseByte(value));
                    break;
                case FOREIGNLANG:
                    enrolleeMark.setForeignLang(Byte.parseByte(value));
                    break;
                case RUSSIANLANG:
                    enrolleeMark.setRussianLang(Byte.parseByte(value));
                    break;
                case COUNTRYDOMEN:
                    enrolleInfo.setCountryDomen(value);
                    break;
                case SOCIALSTUDIES:
                    enrolleeMark.setSocialStudies(Byte.parseByte(value));
                    break;
                case BELORUSSIANLANG:
                    enrolleeMark.setBelorussianLang(Byte.parseByte(value));
                    break;
                case HISTORYOFBELARUS:
                    enrolleeMark.setHistoryOfBelarus(Byte.parseByte(value));
                    break;
                case DATE:
                    enrolleInfo.setDate(value);
                    break;
                default:
                    LOGGER.log(Level.FATAL, "unknown parameter");
                    throw new RuntimeException("unknown parameter");
            }
        }
    }

    public EnrolleeForm(Map<EnrolleeFormCriteria, String> parametersMap) {
        universityInfo = new UniversityInfo();
        enrolleInfo = new EnrolleeInfo();
        enrolleeMark = new EnrolleeMark();

        Factory factory = new Factory();
        for (Map.Entry<EnrolleeFormCriteria, String> entry : parametersMap.entrySet()) {
            factory.set(entry);
        }
    }

    public UniversityInfo getUniversityInfo() {
        return universityInfo;
    }
    public EnrolleeInfo getEnrolleInfo() {
        return enrolleInfo;
    }
    public EnrolleeMark getEnrolleeMark() {
        return enrolleeMark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EnrolleeForm)) return false;
        EnrolleeForm that = (EnrolleeForm) o;
        return Objects.equals(getUniversityInfo(), that.getUniversityInfo()) &&
                Objects.equals(getEnrolleInfo(), that.getEnrolleInfo()) &&
                Objects.equals(getEnrolleeMark(), that.getEnrolleeMark());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUniversityInfo(), getEnrolleInfo(), getEnrolleeMark());
    }
}
