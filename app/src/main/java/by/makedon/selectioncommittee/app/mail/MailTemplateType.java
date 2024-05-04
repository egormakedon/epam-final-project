package by.makedon.selectioncommittee.app.mail;

public enum MailTemplateType {
    FORGOT_PASSWORD("/mailtemplates/forgotPassword.txt"),
    REGISTRATION("/mailtemplates/registration.txt"),
    CHANGE_USER_DATA("/mailtemplates/changeUserData.txt"),
    DELETE_USER("/mailtemplates/deleteUser.txt"),
    SET_STATEMENT("/mailtemplates/setStatement.txt");

    private final String templatePath;

    MailTemplateType(String templatePath) {
        this.templatePath = templatePath;
    }

    String getTemplatePath() {
        return templatePath;
    }
}
