package by.makedon.final_project.entity;

import java.util.List;

public class Enrollee {
    private long id;
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
    }
    public class EnrolleeInfo {
        private String name;
        private String surname;
        private String secondName;
        private String passportId;
        private String countryDomen;
        private String phone;
        private String statement;

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
    }

    public Enrollee(List<String> params) {
        ///////
    }

    public long getId() {
        return id;
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