package by.makedon.selectioncommittee.logic.user;

import by.makedon.selectioncommittee.dao.user.UserDAO;
import by.makedon.selectioncommittee.dao.user.UserDAOImpl;
import by.makedon.selectioncommittee.entity.enrollee.EnrolleeForm;
import by.makedon.selectioncommittee.entity.enrollee.EnrolleeFormCriteria;
import by.makedon.selectioncommittee.exception.DAOException;
import by.makedon.selectioncommittee.exception.LogicException;
import by.makedon.selectioncommittee.logic.Logic;
import com.sun.istack.internal.NotNull;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowFormLogic implements Logic {
    private static final int LIST_SIZE = 1;
    private static final String SCORE = "SCORE";

    private EnrolleeForm enrolleeForm;

    @Override
    public void doAction(@NotNull List<String> parameters) throws LogicException {
        if (parameters.size() != LIST_SIZE) {
            throw new LogicException("wrong number of parameters");
        }

        String usernameValue = parameters.get(0);

        UserDAO dao = UserDAOImpl.getInstance();
        try {
            enrolleeForm = dao.takeEnrolleeForm(usernameValue);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    public void updateServletRequest(HttpServletRequest req) {
        EnrolleeForm.UniversityInfo universityInfo = enrolleeForm.getUniversityInfo();
        req.setAttribute(EnrolleeFormCriteria.UNIVERSITY.toString(), universityInfo.getUniversity());
        req.setAttribute(EnrolleeFormCriteria.FACULTY.toString(), universityInfo.getFaculty());
        req.setAttribute(EnrolleeFormCriteria.SPECIALITY.toString(), universityInfo.getSpeciality());

        EnrolleeForm.EnrolleeInfo enrolleeInfo = enrolleeForm.getEnrolleInfo();
        req.setAttribute(EnrolleeFormCriteria.COUNTRYDOMEN.toString(), enrolleeInfo.getCountryDomen());
        req.setAttribute(EnrolleeFormCriteria.PASSPORTID.toString(), enrolleeInfo.getPassportId());
        req.setAttribute(EnrolleeFormCriteria.DATE.toString(), enrolleeInfo.getDate());
        req.setAttribute(EnrolleeFormCriteria.NAME.toString(), enrolleeInfo.getName());
        req.setAttribute(EnrolleeFormCriteria.PHONE.toString(), enrolleeInfo.getPhone());
        req.setAttribute(EnrolleeFormCriteria.SECONDNAME.toString(), enrolleeInfo.getSecondName());
        req.setAttribute(EnrolleeFormCriteria.SURNAME.toString(), enrolleeInfo.getSurname());

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
