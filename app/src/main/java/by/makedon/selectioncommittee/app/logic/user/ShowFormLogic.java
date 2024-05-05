package by.makedon.selectioncommittee.app.logic.user;

import by.makedon.selectioncommittee.app.dao.DAOException;
import by.makedon.selectioncommittee.app.dao.UserDao;
import by.makedon.selectioncommittee.app.dao.impl.UserDaoImpl;
import by.makedon.selectioncommittee.app.entity.enrolleeform.EnrolleeForm;
import by.makedon.selectioncommittee.app.logic.Logic;
import by.makedon.selectioncommittee.app.logic.LogicException;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowFormLogic implements Logic {
    private static final String PARAMETER_KEY_UNIVERSITY = "UNIVERSITY";
    private static final String PARAMETER_KEY_FACULTY = "FACULTY";
    private static final String PARAMETER_KEY_SPECIALITY = "SPECIALITY";
    private static final String PARAMETER_KEY_NAME = "NAME";
    private static final String PARAMETER_KEY_SURNAME = "SURNAME";
    private static final String PARAMETER_KEY_SECONDNAME = "SECONDNAME";
    private static final String PARAMETER_KEY_PASSPORTID = "PASSPORTID";
    private static final String PARAMETER_KEY_COUNTRYDOMEN = "COUNTRYDOMEN";
    private static final String PARAMETER_KEY_PHONE = "PHONE";
    private static final String SCORE = "SCORE";
    private static final String DATE = "DATE";

    private static final int LIST_SIZE = 1;


    private EnrolleeForm enrolleeForm;

    @Override
    public void doAction(@NotNull List<String> parameters) throws LogicException {
        if (parameters.size() != LIST_SIZE) {
            throw new LogicException("wrong number of parameters");
        }

        String usernameValue = parameters.get(0);

        UserDao dao = UserDaoImpl.getInstance();
        try {
            enrolleeForm = dao.getEnrolleeFormByUsername(usernameValue);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    public void updateServletRequest(HttpServletRequest req) {
        EnrolleeForm.UniversityInfo universityInfo = enrolleeForm.getUniversityInfo();
        req.setAttribute(PARAMETER_KEY_UNIVERSITY, universityInfo.getUniversity());
        req.setAttribute(PARAMETER_KEY_FACULTY, universityInfo.getFaculty());
        req.setAttribute(PARAMETER_KEY_SPECIALITY, universityInfo.getSpeciality());

        EnrolleeForm.EnrolleeInfo enrolleeInfo = enrolleeForm.getEnrolleInfo();
        req.setAttribute(PARAMETER_KEY_COUNTRYDOMEN, enrolleeInfo.getCountryDomen());
        req.setAttribute(PARAMETER_KEY_PASSPORTID, enrolleeInfo.getPassportId());
        req.setAttribute(DATE, enrolleeInfo.getDate());
        req.setAttribute(PARAMETER_KEY_NAME, enrolleeInfo.getName());
        req.setAttribute(PARAMETER_KEY_PHONE, enrolleeInfo.getPhone());
        req.setAttribute(PARAMETER_KEY_SECONDNAME, enrolleeInfo.getSecondName());
        req.setAttribute(PARAMETER_KEY_SURNAME, enrolleeInfo.getSurname());

        EnrolleeForm.EnrolleeMark enrolleeMark = enrolleeForm.getEnrolleeMark();
        byte belorussianLang = enrolleeMark.getBelorussianLang();
        byte biology = enrolleeMark.getBiology();
        byte certificate = enrolleeMark.getCertificate();
        byte chemistry = enrolleeMark.getChemistry();
        byte foreignLang = enrolleeMark.getForeignLang();
        byte geography = enrolleeMark.getGeography();
        byte history = enrolleeMark.getHistory();
        byte historyOfBelarus = enrolleeMark.getHistoryOfBelarus();
        byte math = enrolleeMark.getMath();
        byte physics = enrolleeMark.getPhysics();
        byte russianLang = enrolleeMark.getRussianLang();
        byte socialStudies = enrolleeMark.getSocialStudies();

        short score = (short) (belorussianLang + biology + certificate + chemistry + foreignLang + geography + history + historyOfBelarus +
                math + physics + russianLang + socialStudies);

        req.setAttribute(SCORE, score);
    }
}
