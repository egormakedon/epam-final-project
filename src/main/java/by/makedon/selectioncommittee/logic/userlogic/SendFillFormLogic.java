package by.makedon.selectioncommittee.logic.userlogic;

import by.makedon.selectioncommittee.dao.userdao.UserDAO;
import by.makedon.selectioncommittee.dao.userdao.UserDAOImpl;
import by.makedon.selectioncommittee.entity.Enrollee;
import by.makedon.selectioncommittee.entity.EnrolleeParameter;
import by.makedon.selectioncommittee.exception.DAOException;
import by.makedon.selectioncommittee.validator.EnrolleeValidator;

import java.util.HashMap;
import java.util.Map;

public class SendFillFormLogic {
    public void doAction(String universityValue, String facultyValue, String specialityValue, String  countryDomenValue,
                         String nameValue, String surnameValue, String secondNameValue, String passportIdValue,
                         String phoneValue, String russianLangValue, String belorussianLangValue, String physicsValue,
                         String mathValue, String chemistryValue, String biologyValue, String foreignLangValue,
                         String historyOfBelarusValue, String socialStudiesValue, String geographyValue,
                         String historyValue, String certificateValue, String usernameValue) throws DAOException {

//        if (!EnrolleeValidator.validate(nameValue, surnameValue, secondNameValue, passportIdValue, phoneValue,
//                russianLangValue, belorussianLangValue, physicsValue, mathValue, chemistryValue, biologyValue,
//                foreignLangValue, historyOfBelarusValue, socialStudiesValue, geographyValue, historyValue, certificateValue)) {
//            throw new DAOException("input error");
//        }

        Map<EnrolleeParameter, String> parameters = new HashMap<EnrolleeParameter, String>();
        parameters.put(EnrolleeParameter.UNIVERSITY, universityValue);
        parameters.put(EnrolleeParameter.FACULTY, facultyValue);
        parameters.put(EnrolleeParameter.SPECIALITY, specialityValue);
        parameters.put(EnrolleeParameter.NAME, nameValue);
        parameters.put(EnrolleeParameter.SURNAME, surnameValue);
        if (secondNameValue.isEmpty()) {
            parameters.put(EnrolleeParameter.SECONDNAME, "");
        } else {
            parameters.put(EnrolleeParameter.SECONDNAME, secondNameValue);
        }
        parameters.put(EnrolleeParameter.PASSPORTID, passportIdValue);
        parameters.put(EnrolleeParameter.COUNTRYDOMEN, countryDomenValue);
        parameters.put(EnrolleeParameter.PHONE, phoneValue);
        if (russianLangValue != null) {
            parameters.put(EnrolleeParameter.RUSSIANLANG, russianLangValue);
        }
        if (belorussianLangValue != null) {
            parameters.put(EnrolleeParameter.BELORUSSIANLANG, belorussianLangValue);
        }
        if (physicsValue != null) {
            parameters.put(EnrolleeParameter.PHYSICS, physicsValue);
        }
        if (mathValue != null) {
            parameters.put(EnrolleeParameter.MATH, mathValue);
        }
        if (chemistryValue != null) {
            parameters.put(EnrolleeParameter.CHEMISTRY, chemistryValue);
        }
        if (biologyValue != null) {
            parameters.put(EnrolleeParameter.BIOLOGY, biologyValue);
        }
        if (foreignLangValue != null) {
            parameters.put(EnrolleeParameter.FOREIGNLANG, foreignLangValue);
        }
        if (historyOfBelarusValue != null) {
            parameters.put(EnrolleeParameter.HISTORYOFBELARUS, historyOfBelarusValue);
        }
        if (socialStudiesValue != null) {
            parameters.put(EnrolleeParameter.SOCIALSTUDIES, socialStudiesValue);
        }
        if (geographyValue != null) {
            parameters.put(EnrolleeParameter.GEOGRAPHY, geographyValue);
        }
        if (historyValue != null) {
            parameters.put(EnrolleeParameter.HISTORY, historyValue);
        }
        parameters.put(EnrolleeParameter.CERTIFICATE, certificateValue);

        Enrollee enrollee = new Enrollee(parameters);
        UserDAO dao = UserDAOImpl.getInstance();
        dao.addForm(usernameValue, enrollee);
    }
}
