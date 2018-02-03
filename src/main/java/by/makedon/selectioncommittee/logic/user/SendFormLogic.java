package by.makedon.selectioncommittee.logic.user;

import by.makedon.selectioncommittee.dao.user.UserDAO;
import by.makedon.selectioncommittee.dao.user.UserDAOImpl;
import by.makedon.selectioncommittee.entity.Enrollee;
import by.makedon.selectioncommittee.entity.enrollee.EnrolleeFormCriteria;
import by.makedon.selectioncommittee.exception.LogicException;
import by.makedon.selectioncommittee.logic.Logic;
import by.makedon.selectioncommittee.validator.EnrolleeValidator;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendFormLogic implements Logic {
    private static final int LIST_SIZE = 22;

    @Override
    public void doAction(@NotNull List<String> parameters) throws LogicException {
        if (parameters.size() != LIST_SIZE) {
            throw new LogicException("wrong number of parameters");
        }

        String usernameValue = parameters.get(0);
        String universityValue = parameters.get(1);
        String facultyValue = parameters.get(2);
        String specialityValue = parameters.get(3);
        String nameValue = parameters.get(4);
        String surnameValue = parameters.get(5);
        String secondNameValue = parameters.get(6);
        String passportIdValue = parameters.get(7);
        String countryDomenValue = parameters.get(8);
        String phoneValue = parameters.get(9);
        String russianLangValue = parameters.get(10);
        String belorussianLangValue = parameters.get(11);
        String physicsValue = parameters.get(12);
        String mathValue = parameters.get(13);
        String chemistryValue = parameters.get(14);
        String biologyValue = parameters.get(15);
        String foreignLangValue = parameters.get(16);
        String historyOfBelarusValue = parameters.get(17);
        String socialStudiesValue = parameters.get(18);
        String geographyValue = parameters.get(19);
        String historyValue = parameters.get(20);
        String certificateValue =  parameters.get(21);

        List<String> parametersForValidator = new ArrayList<String>();
        parametersForValidator.add(nameValue);
        parametersForValidator.add(surnameValue);
        parametersForValidator.add(secondNameValue);
        parametersForValidator.add(passportIdValue);
        parametersForValidator.add(phoneValue);
        parametersForValidator.add(russianLangValue);
        parametersForValidator.add(belorussianLangValue);
        parametersForValidator.add(physicsValue);
        parametersForValidator.add(mathValue);
        parametersForValidator.add(chemistryValue);
        parametersForValidator.add(biologyValue);
        parametersForValidator.add(foreignLangValue);
        parametersForValidator.add(historyOfBelarusValue);
        parametersForValidator.add(socialStudiesValue);
        parametersForValidator.add(geographyValue);
        parametersForValidator.add(historyValue);
        parametersForValidator.add(certificateValue);

        if (!EnrolleeValidator.validate(parametersForValidator)) {
            throw new LogicException("invalid input parameters");
        }

        ////////////////////////////////////////////
        Map<EnrolleeFormCriteria, String> parameters = new HashMap<EnrolleeFormCriteria, String>();
        parameters.put(EnrolleeFormCriteria.UNIVERSITY, universityValue);
        parameters.put(EnrolleeFormCriteria.FACULTY, facultyValue);
        parameters.put(EnrolleeFormCriteria.SPECIALITY, specialityValue);
        parameters.put(EnrolleeFormCriteria.NAME, nameValue);
        parameters.put(EnrolleeFormCriteria.SURNAME, surnameValue);
        if (secondNameValue.isEmpty()) {
            parameters.put(EnrolleeFormCriteria.SECONDNAME, "");
        } else {
            parameters.put(EnrolleeFormCriteria.SECONDNAME, secondNameValue);
        }
        parameters.put(EnrolleeFormCriteria.PASSPORTID, passportIdValue);
        parameters.put(EnrolleeFormCriteria.COUNTRYDOMEN, countryDomenValue);
        parameters.put(EnrolleeFormCriteria.PHONE, phoneValue);
        if (russianLangValue != null) {
            parameters.put(EnrolleeFormCriteria.RUSSIANLANG, russianLangValue);
        }
        if (belorussianLangValue != null) {
            parameters.put(EnrolleeFormCriteria.BELORUSSIANLANG, belorussianLangValue);
        }
        if (physicsValue != null) {
            parameters.put(EnrolleeFormCriteria.PHYSICS, physicsValue);
        }
        if (mathValue != null) {
            parameters.put(EnrolleeFormCriteria.MATH, mathValue);
        }
        if (chemistryValue != null) {
            parameters.put(EnrolleeFormCriteria.CHEMISTRY, chemistryValue);
        }
        if (biologyValue != null) {
            parameters.put(EnrolleeFormCriteria.BIOLOGY, biologyValue);
        }
        if (foreignLangValue != null) {
            parameters.put(EnrolleeFormCriteria.FOREIGNLANG, foreignLangValue);
        }
        if (historyOfBelarusValue != null) {
            parameters.put(EnrolleeFormCriteria.HISTORYOFBELARUS, historyOfBelarusValue);
        }
        if (socialStudiesValue != null) {
            parameters.put(EnrolleeFormCriteria.SOCIALSTUDIES, socialStudiesValue);
        }
        if (geographyValue != null) {
            parameters.put(EnrolleeFormCriteria.GEOGRAPHY, geographyValue);
        }
        if (historyValue != null) {
            parameters.put(EnrolleeFormCriteria.HISTORY, historyValue);
        }
        parameters.put(EnrolleeFormCriteria.CERTIFICATE, certificateValue);

        Enrollee enrollee = new Enrollee(parameters);
        UserDAO dao = UserDAOImpl.getInstance();
        dao.addForm(usernameValue, enrollee);
    }
}
