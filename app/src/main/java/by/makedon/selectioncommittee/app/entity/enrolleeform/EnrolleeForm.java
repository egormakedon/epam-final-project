package by.makedon.selectioncommittee.app.entity.enrolleeform;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class EnrolleeForm {
    private final UniversityInfo universityInfo;
    private final EnrolleeInfo enrolleeInfo;
    private final EnrolleeMark enrolleeMark;

    protected EnrolleeForm(UniversityInfo universityInfo, EnrolleeInfo enrolleeInfo, EnrolleeMark enrolleeMark) {
        this.universityInfo = universityInfo;
        this.enrolleeInfo = enrolleeInfo;
        this.enrolleeMark = enrolleeMark;
    }

    public static EnrolleeFormBuilder builder() {
        return new EnrolleeFormBuilder();
    }

    public String getUniversity() {
        return universityInfo.getUniversity();
    }

    public String getFaculty() {
        return universityInfo.getFaculty();
    }

    public String getSpeciality() {
        return universityInfo.getSpeciality();
    }

    public String getName() {
        return enrolleeInfo.getName();
    }

    public String getSurname() {
        return enrolleeInfo.getSurname();
    }

    public String getSecondName() {
        return enrolleeInfo.getSecondName();
    }

    public String getPassportId() {
        return enrolleeInfo.getPassportId();
    }

    public String getCountryDomen() {
        return enrolleeInfo.getCountryDomen();
    }

    public String getPhone() {
        return enrolleeInfo.getPhone();
    }

    public String getDate() {
        return enrolleeInfo.getDate();
    }

    public byte getRussianLang() {
        return enrolleeMark.getRussianLang();
    }

    public byte getBelorussianLang() {
        return enrolleeMark.getBelorussianLang();
    }

    public byte getPhysics() {
        return enrolleeMark.getPhysics();
    }

    public byte getMath() {
        return enrolleeMark.getMath();
    }

    public byte getChemistry() {
        return enrolleeMark.getChemistry();
    }

    public byte getBiology() {
        return enrolleeMark.getBiology();
    }

    public byte getForeignLang() {
        return enrolleeMark.getForeignLang();
    }

    public byte getHistoryOfBelarus() {
        return enrolleeMark.getHistoryOfBelarus();
    }

    public byte getSocialStudies() {
        return enrolleeMark.getSocialStudies();
    }

    public byte getGeography() {
        return enrolleeMark.getGeography();
    }

    public byte getHistory() {
        return enrolleeMark.getHistory();
    }

    public byte getCertificate() {
        return enrolleeMark.getCertificate();
    }

    public int getScore() {
        return enrolleeMark.calculateScore();
    }

    public static class EnrolleeFormBuilder {
        private String university;
        private String faculty;
        private String speciality;

        private String name;
        private String surname;
        private String secondName;
        private String passportId;
        private String countryDomen;
        private String phone;
        private String date;

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

        public EnrolleeFormBuilder university(String university) {
            this.university = university;
            return this;
        }

        public EnrolleeFormBuilder faculty(String faculty) {
            this.faculty = faculty;
            return this;
        }

        public EnrolleeFormBuilder speciality(String speciality) {
            this.speciality = speciality;
            return this;
        }

        public EnrolleeFormBuilder name(String name) {
            this.name = name;
            return this;
        }

        public EnrolleeFormBuilder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public EnrolleeFormBuilder secondName(String secondName) {
            this.secondName = secondName;
            return this;
        }

        public EnrolleeFormBuilder passportId(String passportId) {
            this.passportId = passportId;
            return this;
        }

        public EnrolleeFormBuilder countryDomen(String countryDomen) {
            this.countryDomen = countryDomen;
            return this;
        }

        public EnrolleeFormBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public EnrolleeFormBuilder date(String date) {
            this.date = date;
            return this;
        }

        public EnrolleeFormBuilder russianLang(byte russianLang) {
            this.russianLang = russianLang;
            return this;
        }

        public EnrolleeFormBuilder belorussianLang(byte belorussianLang) {
            this.belorussianLang = belorussianLang;
            return this;
        }

        public EnrolleeFormBuilder physics(byte physics) {
            this.physics = physics;
            return this;
        }

        public EnrolleeFormBuilder math(byte math) {
            this.math = math;
            return this;
        }

        public EnrolleeFormBuilder chemistry(byte chemistry) {
            this.chemistry = chemistry;
            return this;
        }

        public EnrolleeFormBuilder biology(byte biology) {
            this.biology = biology;
            return this;
        }

        public EnrolleeFormBuilder foreignLang(byte foreignLang) {
            this.foreignLang = foreignLang;
            return this;
        }

        public EnrolleeFormBuilder historyOfBelarus(byte historyOfBelarus) {
            this.historyOfBelarus = historyOfBelarus;
            return this;
        }

        public EnrolleeFormBuilder socialStudies(byte socialStudies) {
            this.socialStudies = socialStudies;
            return this;
        }

        public EnrolleeFormBuilder geography(byte geography) {
            this.geography = geography;
            return this;
        }

        public EnrolleeFormBuilder history(byte history) {
            this.history = history;
            return this;
        }

        public EnrolleeFormBuilder certificate(byte certificate) {
            this.certificate = certificate;
            return this;
        }

        public EnrolleeForm build() {
            UniversityInfo universityInfo = UniversityInfo
                .builder()
                .university(university)
                .faculty(faculty)
                .speciality(speciality)
                .build();

            EnrolleeInfo enrolleeInfo = EnrolleeInfo
                .builder()
                .name(name)
                .surname(surname)
                .secondName(secondName)
                .passportId(passportId)
                .countryDomen(countryDomen)
                .phone(phone)
                .date(date)
                .build();

            EnrolleeMark enrolleeMark = EnrolleeMark
                .builder()
                .russianLang(russianLang)
                .belorussianLang(belorussianLang)
                .physics(physics)
                .math(math)
                .chemistry(chemistry)
                .biology(biology)
                .foreignLang(foreignLang)
                .historyOfBelarus(historyOfBelarus)
                .socialStudies(socialStudies)
                .geography(geography)
                .history(history)
                .certificate(certificate)
                .build();

            return new EnrolleeForm(universityInfo, enrolleeInfo, enrolleeMark);
        }
    }
}
