package by.makedon.selectioncommittee.app.command.user;

import by.makedon.selectioncommittee.app.command.Command;
import by.makedon.selectioncommittee.app.configuration.controller.Router;
import by.makedon.selectioncommittee.app.configuration.util.Page;
import by.makedon.selectioncommittee.app.configuration.util.RequestParameterBuilder;
import by.makedon.selectioncommittee.app.configuration.util.RequestParameterKey;
import by.makedon.selectioncommittee.app.logic.Logic;
import by.makedon.selectioncommittee.app.logic.LogicException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.function.Consumer;

public class SendFormCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(SendFormCommand.class);

    private static final String PARAMETER_KEY_UNIVERSITY = "UNIVERSITY";
    private static final String PARAMETER_KEY_FACULTY = "FACULTY";
    private static final String PARAMETER_KEY_SPECIALITY = "SPECIALITY";
    private static final String PARAMETER_KEY_NAME = "NAME";
    private static final String PARAMETER_KEY_SURNAME = "SURNAME";
    private static final String PARAMETER_KEY_SECONDNAME = "SECONDNAME";
    private static final String PARAMETER_KEY_PASSPORTID = "PASSPORTID";
    private static final String PARAMETER_KEY_COUNTRYDOMEN = "COUNTRYDOMEN";
    private static final String PARAMETER_KEY_PHONE = "PHONE";
    private static final String PARAMETER_KEY_CERTIFICATE = "CERTIFICATE";

    private final Logic logic;

    public SendFormCommand(Logic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String usernameValue = (String) session.getAttribute(RequestParameterKey.USERNAME);

        String universityValue = request.getParameter(PARAMETER_KEY_UNIVERSITY);
        String facultyValue = request.getParameter(PARAMETER_KEY_FACULTY);
        String specialityValue = request.getParameter(PARAMETER_KEY_SPECIALITY);
        String nameValue = request.getParameter(PARAMETER_KEY_NAME);
        String surnameValue = request.getParameter(PARAMETER_KEY_SURNAME);
        String secondNameValue = request.getParameter(PARAMETER_KEY_SECONDNAME);
        String passportIdValue = request.getParameter(PARAMETER_KEY_PASSPORTID);
        String countryDomenValue = request.getParameter(PARAMETER_KEY_COUNTRYDOMEN);
        String phoneValue = request.getParameter(PARAMETER_KEY_PHONE);
        String certificateValue = request.getParameter(PARAMETER_KEY_CERTIFICATE);

        String subjectId1Value = request.getParameter(RequestParameterKey.SUBJECT_ID_1);
        String subjectId2Value = request.getParameter(RequestParameterKey.SUBJECT_ID_2);
        String subjectId3Value = request.getParameter(RequestParameterKey.SUBJECT_ID_3);
        String subject1Value = request.getParameter(RequestParameterKey.SUBJECT_VALUE_1);
        String subject2Value = request.getParameter(RequestParameterKey.SUBJECT_VALUE_2);
        String subject3Value = request.getParameter(RequestParameterKey.SUBJECT_VALUE_3);

        logger.debug("Execute SendFormCommand: {}={} {}={} {}={} {}={} {}={} {}={} {}={} {}={} {}={} {}={} {}={} {}={} {}={} {}={} {}={} {}={}",
            PARAMETER_KEY_UNIVERSITY, universityValue,
            PARAMETER_KEY_FACULTY, facultyValue,
            PARAMETER_KEY_SPECIALITY, specialityValue,
            PARAMETER_KEY_NAME, nameValue,
            PARAMETER_KEY_SURNAME, surnameValue,
            PARAMETER_KEY_SECONDNAME, secondNameValue,
            PARAMETER_KEY_PASSPORTID, passportIdValue,
            PARAMETER_KEY_COUNTRYDOMEN, countryDomenValue,
            PARAMETER_KEY_PHONE, phoneValue,
            PARAMETER_KEY_CERTIFICATE, certificateValue,
            RequestParameterKey.SUBJECT_ID_1, subjectId1Value,
            RequestParameterKey.SUBJECT_ID_2, subjectId2Value,
            RequestParameterKey.SUBJECT_ID_3, subjectId3Value,
            RequestParameterKey.SUBJECT_VALUE_1, subject1Value,
            RequestParameterKey.SUBJECT_VALUE_2, subject2Value,
            RequestParameterKey.SUBJECT_VALUE_3, subject3Value);

        List<String> parameters = ParameterBuilder.builder()
            .username(usernameValue)
            .university(universityValue)
            .faculty(facultyValue)
            .speciality(specialityValue)
            .name(nameValue)
            .surname(surnameValue)
            .secondName(secondNameValue)
            .passportId(passportIdValue)
            .countryDomen(countryDomenValue)
            .phone(phoneValue)
            .certificate(certificateValue)
            .putSubject(subjectId1Value, subject1Value)
            .putSubject(subjectId2Value, subject2Value)
            .putSubject(subjectId3Value, subject3Value)
            .build();

        RequestParameterBuilder parameterBuilder = RequestParameterBuilder.builder();
        try {
            logic.doAction(parameters);
            parameterBuilder.put(RequestParameterKey.MESSAGE, "form has been sent successfully!!");
        } catch (LogicException e) {
            logger.error(e.getMessage(), e);
            parameterBuilder.put(RequestParameterKey.MESSAGE, e.getMessage());
        }

        Router router = Router.redirectRouter();
        router.setPagePath(Page.MESSAGE + "?" + parameterBuilder.build());
        return router;
    }

    private static class ParameterBuilder {
        private final Map<String, Consumer<String>> subjectIdToSubjectValueSetterConsumer;

        private String usernameValue;
        private String universityValue;
        private String facultyValue;
        private String specialityValue;
        private String nameValue;
        private String surnameValue;
        private String secondNameValue;
        private String passportIdValue;
        private String countryDomenValue;
        private String phoneValue;
        private String certificateValue;

        private String russianLangValue;
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

        private ParameterBuilder() {
            subjectIdToSubjectValueSetterConsumer = new HashMap<>();
            subjectIdToSubjectValueSetterConsumer.put("0", value -> russianLangValue = value);
            subjectIdToSubjectValueSetterConsumer.put("1", value -> belorussianLangValue = value);
            subjectIdToSubjectValueSetterConsumer.put("2", value -> physicsValue = value);
            subjectIdToSubjectValueSetterConsumer.put("3", value -> mathValue = value);
            subjectIdToSubjectValueSetterConsumer.put("4", value -> chemistryValue = value);
            subjectIdToSubjectValueSetterConsumer.put("5", value -> biologyValue = value);
            subjectIdToSubjectValueSetterConsumer.put("6", value -> foreignLangValue = value);
            subjectIdToSubjectValueSetterConsumer.put("7", value -> historyOfBelarusValue = value);
            subjectIdToSubjectValueSetterConsumer.put("8", value -> socialStudiesValue = value);
            subjectIdToSubjectValueSetterConsumer.put("9", value -> geographyValue = value);
            subjectIdToSubjectValueSetterConsumer.put("10", value -> historyValue = value);
        }

        public static ParameterBuilder builder() {
            return new ParameterBuilder();
        }

        public ParameterBuilder putSubject(String subjectId, String subjectValue) {
            Optional<Consumer<String>> subjectValueSetterConsumerOptional =
                Optional.ofNullable(subjectIdToSubjectValueSetterConsumer.get(subjectId));
            subjectValueSetterConsumerOptional.ifPresent(f -> f.accept(subjectValue));
            return this;
        }

        public ParameterBuilder username(String usernameValue) {
            this.usernameValue = usernameValue;
            return this;
        }

        public ParameterBuilder university(String universityValue) {
            this.universityValue = universityValue;
            return this;
        }

        public ParameterBuilder faculty(String facultyValue) {
            this.facultyValue = facultyValue;
            return this;
        }

        public ParameterBuilder speciality(String specialityValue) {
            this.specialityValue = specialityValue;
            return this;
        }

        public ParameterBuilder name(String nameValue) {
            this.nameValue = nameValue;
            return this;
        }

        public ParameterBuilder surname(String surnameValue) {
            this.surnameValue = surnameValue;
            return this;
        }

        public ParameterBuilder secondName(String secondNameValue) {
            this.secondNameValue = secondNameValue;
            return this;
        }

        public ParameterBuilder passportId(String passportIdValue) {
            this.passportIdValue = passportIdValue;
            return this;
        }

        public ParameterBuilder countryDomen(String countryDomenValue) {
            this.countryDomenValue = countryDomenValue;
            return this;
        }

        public ParameterBuilder phone(String phoneValue) {
            this.phoneValue = phoneValue;
            return this;
        }

        public ParameterBuilder certificate(String certificateValue) {
            this.certificateValue = certificateValue;
            return this;
        }

        public List<String> build() {
            List<String> parameters = new ArrayList<>();
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
            return parameters;
        }
    }
}
