package by.makedon.selectioncommittee.mail;

public enum MailTemplatePath {
    FORGOT_PASSWORD("/mailtemplates/forgotPassword.txt"),
    REGISTRATION("/mailtemplates/registration.txt"),
    CHANGE_USER_DATA("/mailtemplates/changeUserData.txt"),
    DELETE_USER("/mailtemplates/deleteUser.txt"),
    SET_STATEMENT("/mailtemplates/setStatement.txt");

    private String templatePath;

    MailTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public String getTemplatePath() {
        return templatePath;
    }
}