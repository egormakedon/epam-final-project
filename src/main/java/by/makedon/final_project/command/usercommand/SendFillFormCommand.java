package by.makedon.final_project.command.usercommand;

import by.makedon.final_project.command.Command;
import by.makedon.final_project.constant.PageConstant;
import by.makedon.final_project.controller.Router;
import by.makedon.final_project.entity.EnrolleeParameter;
import by.makedon.final_project.exception.DAOException;
import by.makedon.final_project.logic.userlogic.SendFillFormLogic;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SendFillFormCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(SendFillFormCommand.class);
    private static final String USERNAME = "username";
    private SendFillFormLogic logic;

    public SendFillFormCommand(SendFillFormLogic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String usernameValue = (String)session.getAttribute(USERNAME);

        String universityValue = req.getParameter(EnrolleeParameter.UNIVERSITY.getParameter());
        String facultyValue = req.getParameter(EnrolleeParameter.FACULTY.getParameter());
        String specialityValue = req.getParameter(EnrolleeParameter.SPECIALITY.getParameter());
        String nameValue = req.getParameter(EnrolleeParameter.NAME.getParameter());
        String surnameValue = req.getParameter(EnrolleeParameter.SURNAME.getParameter());
        String secondNameValue = req.getParameter(EnrolleeParameter.SECONDNAME.getParameter());
        String passportIdValue = req.getParameter(EnrolleeParameter.PASSPORTID.getParameter());
        String countryDomenValue = req.getParameter(EnrolleeParameter.COUNTRYDOMEN.getParameter());
        String phoneValue = req.getParameter(EnrolleeParameter.PHONE.getParameter());
        String russianLangValue = req.getParameter(EnrolleeParameter.RUSSIANLANG.getParameter());
        String belorussianLangValue = req.getParameter(EnrolleeParameter.BELORUSSIANLANG.getParameter());
        String physicsValue = req.getParameter(EnrolleeParameter.PHYSICS.getParameter());
        String mathValue = req.getParameter(EnrolleeParameter.MATH.getParameter());
        String chemistryValue = req.getParameter(EnrolleeParameter.CHEMISTRY.getParameter());
        String biologyValue = req.getParameter(EnrolleeParameter.BIOLOGY.getParameter());
        String foreignLangValue = req.getParameter(EnrolleeParameter.FOREIGNLANG.getParameter());
        String historyOfBelarusValue = req.getParameter(EnrolleeParameter.HISTORYOFBELARUS.getParameter());
        String socialStudiesValue = req.getParameter(EnrolleeParameter.SOCIALSTUDIES.getParameter());
        String geographyValue = req.getParameter(EnrolleeParameter.GEOGRAPHY.getParameter());
        String historyValue = req.getParameter(EnrolleeParameter.HISTORY.getParameter());
        String certificateValue = req.getParameter(EnrolleeParameter.CERTIFICATE.getParameter());

        Router router = new Router();
        try {
            logic.doAction(universityValue, facultyValue, specialityValue, countryDomenValue, nameValue, surnameValue, secondNameValue, passportIdValue, phoneValue,
                    russianLangValue, belorussianLangValue, physicsValue, mathValue, chemistryValue, biologyValue,
                    foreignLangValue, historyOfBelarusValue, socialStudiesValue, geographyValue, historyValue, certificateValue, usernameValue);
            router.setPagePath(PageConstant.MESSAGE_PAGE + "?message=" + "form sent successfully");
            router.setRoute(Router.RouteType.REDIRECT);
            return router;
        } catch (DAOException e) {
            LOGGER.log(Level.ERROR, e);
            router.setPagePath(PageConstant.MESSAGE_PAGE + "?message=" + e.getMessage());
            router.setRoute(Router.RouteType.REDIRECT);
            return router;
        }
    }
}
