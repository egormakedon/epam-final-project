package by.makedon.selectioncommittee.app.logic.user;

import by.makedon.selectioncommittee.app.dao.UserDao;
import by.makedon.selectioncommittee.app.dao.impl.UserDaoImpl;
import by.makedon.selectioncommittee.app.entity.enrolleeform.EnrolleeForm;
import by.makedon.selectioncommittee.app.entity.enrolleeform.EnrolleeFormCriteria;
import by.makedon.selectioncommittee.app.dao.DAOException;
import by.makedon.selectioncommittee.app.logic.LogicException;
import by.makedon.selectioncommittee.app.logic.Logic;
import by.makedon.selectioncommittee.app.validator.EnrolleeValidator;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendFormLogic implements Logic {
    private static final int LIST_SIZE = 22;

    private static final String DATE_PATTERN = "yyyy-MM-dd";

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

        Map<EnrolleeFormCriteria, String> parametersMap = new HashMap<EnrolleeFormCriteria, String>();

        parametersMap.put(EnrolleeFormCriteria.UNIVERSITY, universityValue);
        parametersMap.put(EnrolleeFormCriteria.FACULTY, facultyValue);
        parametersMap.put(EnrolleeFormCriteria.SPECIALITY, specialityValue);
        parametersMap.put(EnrolleeFormCriteria.NAME, nameValue);
        parametersMap.put(EnrolleeFormCriteria.SURNAME, surnameValue);
        parametersMap.put(EnrolleeFormCriteria.CERTIFICATE, certificateValue);
        parametersMap.put(EnrolleeFormCriteria.PASSPORTID, passportIdValue);
        parametersMap.put(EnrolleeFormCriteria.COUNTRYDOMEN, countryDomenValue);
        parametersMap.put(EnrolleeFormCriteria.PHONE, phoneValue);

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
        parametersMap.put(EnrolleeFormCriteria.DATE, dateFormat.format(date));

        if (secondNameValue.isEmpty()) {
            parametersMap.put(EnrolleeFormCriteria.SECONDNAME, "");
        } else {
            parametersMap.put(EnrolleeFormCriteria.SECONDNAME, secondNameValue);
        }

        if (russianLangValue != null) {
            parametersMap.put(EnrolleeFormCriteria.RUSSIANLANG, russianLangValue);
        }
        if (belorussianLangValue != null) {
            parametersMap.put(EnrolleeFormCriteria.BELORUSSIANLANG, belorussianLangValue);
        }
        if (physicsValue != null) {
            parametersMap.put(EnrolleeFormCriteria.PHYSICS, physicsValue);
        }
        if (mathValue != null) {
            parametersMap.put(EnrolleeFormCriteria.MATH, mathValue);
        }
        if (chemistryValue != null) {
            parametersMap.put(EnrolleeFormCriteria.CHEMISTRY, chemistryValue);
        }
        if (biologyValue != null) {
            parametersMap.put(EnrolleeFormCriteria.BIOLOGY, biologyValue);
        }
        if (foreignLangValue != null) {
            parametersMap.put(EnrolleeFormCriteria.FOREIGNLANG, foreignLangValue);
        }
        if (historyOfBelarusValue != null) {
            parametersMap.put(EnrolleeFormCriteria.HISTORYOFBELARUS, historyOfBelarusValue);
        }
        if (socialStudiesValue != null) {
            parametersMap.put(EnrolleeFormCriteria.SOCIALSTUDIES, socialStudiesValue);
        }
        if (geographyValue != null) {
            parametersMap.put(EnrolleeFormCriteria.GEOGRAPHY, geographyValue);
        }
        if (historyValue != null) {
            parametersMap.put(EnrolleeFormCriteria.HISTORY, historyValue);
        }

        EnrolleeForm enrolleeForm = new EnrolleeForm(parametersMap);
        UserDao dao = UserDaoImpl.getInstance();

        try {
            if (!dao.isStatementInProcess()) {
                throw new LogicException("you couldn't add form");
            }
            if (dao.isUserEnrolleeFormInserted(usernameValue)) {
                throw new LogicException("form has already added");
            }
            dao.saveEnrolleeFormByUsername(usernameValue, enrolleeForm);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }
}
