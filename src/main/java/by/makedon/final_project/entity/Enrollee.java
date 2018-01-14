package by.makedon.final_project.entity;

import java.util.List;

public class Enrollee {
    private UniversityInfo universityInfo;
    private EnrolleeInfo enrolleInfo;
    private EnrolleeMark enrolleeMark;

    private String statement;

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
    }
    public class EnrolleeInfo {
        private String name;
        private String surname;
        private String secondName;
        private String passportId;
        private String countryDomen;
        private String phone;

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

        public String getStatement() {
            return statement;
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
    }
    public class EnrolleeMark {
        private int russianLang = 0;
        private int belorussianLang = 0;
        private int physics = 0;
        private int math = 0;
        private int chemistry = 0;
        private int biology = 0;
        private int foreignLang = 0;
        private int historyOfBelarus = 0;
        private int socialStudies = 0;
        private int geography = 0;
        private int history = 0;
        private int certificate = 0;

        public int getRussianLang() {
            return russianLang;
        }

        public int getBelorussianLang() {
            return belorussianLang;
        }

        public int getPhysics() {
            return physics;
        }

        public int getMath() {
            return math;
        }

        public int getChemistry() {
            return chemistry;
        }

        public int getBiology() {
            return biology;
        }

        public int getForeignLang() {
            return foreignLang;
        }

        public int getHistoryOfBelarus() {
            return historyOfBelarus;
        }

        public int getSocialStudies() {
            return socialStudies;
        }

        public int getGeography() {
            return geography;
        }

        public int getHistory() {
            return history;
        }

        public int getCertificate() {
            return certificate;
        }

        private void setRussianLang(int russianLang) {
            this.russianLang = russianLang;
        }

        private void setBelorussianLang(int belorussianLang) {
            this.belorussianLang = belorussianLang;
        }

        private void setPhysics(int physics) {
            this.physics = physics;
        }

        private void setMath(int math) {
            this.math = math;
        }

        private void setChemistry(int chemistry) {
            this.chemistry = chemistry;
        }

        private void setBiology(int biology) {
            this.biology = biology;
        }

        private void setForeignLang(int foreignLang) {
            this.foreignLang = foreignLang;
        }

        private void setHistoryOfBelarus(int historyOfBelarus) {
            this.historyOfBelarus = historyOfBelarus;
        }

        private void setSocialStudies(int socialStudies) {
            this.socialStudies = socialStudies;
        }

        private void setGeography(int geography) {
            this.geography = geography;
        }

        private void setHistory(int history) {
            this.history = history;
        }

        private void setCertificate(int certificate) {
            this.certificate = certificate;
        }
    }

    public Enrollee(List<String> params) {
        ///////
    }

    public String getStatement() {
        return statement;
    }
    public void setStatement(String statement) {
        this.statement = statement;
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
}