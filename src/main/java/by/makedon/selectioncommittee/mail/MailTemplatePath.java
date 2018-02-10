package by.makedon.selectioncommittee.mail;

public enum MailTemplatePath {
    FORGOT_PASSWORD("/mailtemplates/forgotPassword.txt"),
    REGISTRATION("/mailtemplates/registration.txt");

    private String templatePath;

    MailTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public String getTemplatePath() {
        return templatePath;
    }
}