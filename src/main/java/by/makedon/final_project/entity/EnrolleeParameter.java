package by.makedon.final_project.entity;

public enum EnrolleeParameter {
    UNIVERSITY("university"),
    FACULTY("faculty"),
    SPECIALITY("speciality"),
    NAME("name"),
    SURNAME("surname"),
    SECONDNAME("secondName"),
    PASSPORTID("passportId"),
    COUNTRYDOMEN("countryDomen"),
    PHONE("phone"),
    RUSSIANLANG("russianLang"),
    BELORUSSIANLANG("belorussianLang"),
    PHYSICS("physics"),
    MATH("math"),
    CHEMISTRY("chemistry"),
    BIOLOGY("biology"),
    FOREIGNLANG("foreignLang"),
    HISTORYOFBELARUS("historyOfBelarus"),
    SOCIALSTUDIES("socialStudies"),
    GEOGRAPHY("geography"),
    HISTORY("history"),
    CERTIFICATE("certificate");

    private String parameter;

    EnrolleeParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }
}
