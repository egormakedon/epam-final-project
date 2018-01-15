package by.makedon.final_project.command.usercommand;

import by.makedon.final_project.command.Command;
import by.makedon.final_project.constant.PageConstant;
import by.makedon.final_project.controller.Router;
import by.makedon.final_project.entity.EnrolleeParameter;
import by.makedon.final_project.logic.userlogic.SendFillFormLogic;
import by.makedon.final_project.validator.EnrolleeValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class SendFillFormCommand implements Command {
    private SendFillFormLogic logic;

    public SendFillFormCommand(SendFillFormLogic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest req) {
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

        if (!EnrolleeValidator.validate(nameValue, surnameValue, secondNameValue, passportIdValue, phoneValue,
                russianLangValue, belorussianLangValue, physicsValue, mathValue, chemistryValue, biologyValue,
                foreignLangValue, historyOfBelarusValue, socialStudiesValue, geographyValue, historyValue, certificateValue)) {
            req.setAttribute("error", "input error");
            Router router = new Router();
            router.setRoute(Router.RouteType.FORWARD);
            router.setPagePath(PageConstant.MESSAGE_PAGE);
            return router;
        }

        Map<EnrolleeParameter, String> parameters = new HashMap<EnrolleeParameter, String>();
        parameters.put(EnrolleeParameter.UNIVERSITY, universityValue);
        parameters.put(EnrolleeParameter.FACULTY, facultyValue);
        parameters.put(EnrolleeParameter.SPECIALITY, specialityValue);
        parameters.put(EnrolleeParameter.NAME, nameValue);
        parameters.put(EnrolleeParameter.SURNAME, surnameValue);
        if (!secondNameValue.isEmpty()) {
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

        logic.doAction(parameters);

        return null;
    }
}
