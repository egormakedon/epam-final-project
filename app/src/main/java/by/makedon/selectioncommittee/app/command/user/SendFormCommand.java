package by.makedon.selectioncommittee.app.command.user;

import by.makedon.selectioncommittee.command.Command;
import by.makedon.selectioncommittee.constant.Page;
import by.makedon.selectioncommittee.controller.Router;
import by.makedon.selectioncommittee.entity.enrollee.EnrolleeFormCriteria;
import by.makedon.selectioncommittee.exception.LogicException;
import by.makedon.selectioncommittee.logic.Logic;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class SendFormCommand implements Command {
    private static final String USERNAME = "username";

    private static final String SUBJECT_ID_1 = "subjectId1";
    private static final String SUBJECT_ID_2 = "subjectId2";
    private static final String SUBJECT_ID_3 = "subjectId3";

    private static final String SUBJECT_VALUE_1 = "subjectValue1";
    private static final String SUBJECT_VALUE_2 = "subjectValue2";
    private static final String SUBJECT_VALUE_3 = "subjectValue3";

    private static final String ZERO = "0";
    private static final String ONE = "1";
    private static final String TWO = "2";
    private static final String THREE = "3";
    private static final String FOUR = "4";
    private static final String FIVE = "5";
    private static final String SIX = "6";
    private static final String SEVEN = "7";
    private static final String EIGHT = "8";
    private static final String NINE = "9";
    private static final String TEN = "10";

    private String russianLangValue ;
    private String belorussianLangValue;
    private String physicsValue;
    private String mathValue;
    private String chemistryValue;
    private String biologyValue;
    private String foreignLangValue;
    private String historyOfBelarusValue;
    private String socialStudiesValue;
    private String geographyValue;
    private String historyValue;

    private Logic logic;

    public SendFormCommand(Logic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String usernameValue = (String)session.getAttribute(USERNAME);

        String universityValue = req.getParameter(EnrolleeFormCriteria.UNIVERSITY.toString());
        String facultyValue = req.getParameter(EnrolleeFormCriteria.FACULTY.toString());
        String specialityValue = req.getParameter(EnrolleeFormCriteria.SPECIALITY.toString());
        String nameValue = req.getParameter(EnrolleeFormCriteria.NAME.toString());
        String surnameValue = req.getParameter(EnrolleeFormCriteria.SURNAME.toString());
        String secondNameValue = req.getParameter(EnrolleeFormCriteria.SECONDNAME.toString());
        String passportIdValue = req.getParameter(EnrolleeFormCriteria.PASSPORTID.toString());
        String countryDomenValue = req.getParameter(EnrolleeFormCriteria.COUNTRYDOMEN.toString());
        String phoneValue = req.getParameter(EnrolleeFormCriteria.PHONE.toString());
        String certificateValue = req.getParameter(EnrolleeFormCriteria.CERTIFICATE.toString());

        String subjectId1 = req.getParameter(SUBJECT_ID_1);
        String subjectId2 = req.getParameter(SUBJECT_ID_2);
        String subjectId3 = req.getParameter(SUBJECT_ID_3);

        String subjectValue1 = req.getParameter(SUBJECT_VALUE_1);
        String subjectValue2 = req.getParameter(SUBJECT_VALUE_2);
        String subjectValue3 = req.getParameter(SUBJECT_VALUE_3);

        factory(subjectId1, subjectValue1);
        factory(subjectId2, subjectValue2);
        factory(subjectId3, subjectValue3);

        List<String> parameters = new ArrayList<String>();
        parameters.add(usernameValue);
        parameters.add(universityValue);
        parameters.add(facultyValue);
        parameters.add(specialityValue);
        parameters.add(nameValue);
        parameters.add(surnameValue);
        parameters.add(secondNameValue);
        parameters.add(passportIdValue);
        parameters.add(countryDomenValue);
        parameters.add(phoneValue);
        parameters.add(russianLangValue);
        parameters.add(belorussianLangValue);
        parameters.add(physicsValue);
        parameters.add(mathValue);
        parameters.add(chemistryValue);
        parameters.add(biologyValue);
        parameters.add(foreignLangValue);
        parameters.add(historyOfBelarusValue);
        parameters.add(socialStudiesValue);
        parameters.add(geographyValue);
        parameters.add(historyValue);
        parameters.add(certificateValue);

        Router router = new Router();
        router.setRoute(Router.RouteType.REDIRECT);
        try {
            logic.doAction(parameters);
            router.setPagePath(Page.MESSAGE + "?message=" + "form sent successfully");
        } catch (LogicException e) {
            LOGGER.log(Level.ERROR, e);
            router.setPagePath(Page.MESSAGE + "?message=" + e.getMessage());
        }
        return router;
    }

    private void factory(String key, String value) {
        switch (key) {
            case ZERO:
                russianLangValue = value;
                break;
            case ONE:
                belorussianLangValue = value;
                break;
            case TWO:
                physicsValue = value;
                break;
            case THREE:
                mathValue = value;
                break;
            case FOUR:
                chemistryValue = value;
                break;
            case FIVE:
                biologyValue = value;
                break;
            case SIX:
                foreignLangValue = value;
                break;
            case SEVEN:
                historyOfBelarusValue = value;
                break;
            case EIGHT:
                socialStudiesValue = value;
                break;
            case NINE:
                geographyValue = value;
                break;
            case TEN:
                historyValue = value;
                break;
        }
    }
}
