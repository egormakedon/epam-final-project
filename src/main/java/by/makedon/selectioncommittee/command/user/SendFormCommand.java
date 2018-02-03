package by.makedon.selectioncommittee.command.user;

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
        String russianLangValue = req.getParameter(EnrolleeFormCriteria.RUSSIANLANG.toString());
        String belorussianLangValue = req.getParameter(EnrolleeFormCriteria.BELORUSSIANLANG.toString());
        String physicsValue = req.getParameter(EnrolleeFormCriteria.PHYSICS.toString());
        String mathValue = req.getParameter(EnrolleeFormCriteria.MATH.toString());
        String chemistryValue = req.getParameter(EnrolleeFormCriteria.CHEMISTRY.toString());
        String biologyValue = req.getParameter(EnrolleeFormCriteria.BIOLOGY.toString());
        String foreignLangValue = req.getParameter(EnrolleeFormCriteria.FOREIGNLANG.toString());
        String historyOfBelarusValue = req.getParameter(EnrolleeFormCriteria.HISTORYOFBELARUS.toString());
        String socialStudiesValue = req.getParameter(EnrolleeFormCriteria.SOCIALSTUDIES.toString());
        String geographyValue = req.getParameter(EnrolleeFormCriteria.GEOGRAPHY.toString());
        String historyValue = req.getParameter(EnrolleeFormCriteria.HISTORY.toString());
        String certificateValue = req.getParameter(EnrolleeFormCriteria.CERTIFICATE.toString());

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
}
