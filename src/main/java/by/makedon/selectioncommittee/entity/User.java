package by.makedon.selectioncommittee.entity;

import java.util.Objects;

public class User {
    private String emailValue;
    private String usernameValue;
    private String passwordValue;

    public String getEmailValue() {
        return emailValue;
    }
    public void setEmailValue(String emailValue) {
        this.emailValue = emailValue;
    }
    public String getUsernameValue() {
        return usernameValue;
    }
    public void setUsernameValue(String usernameValue) {
        this.usernameValue = usernameValue;
    }
    public String getPasswordValue() {
        return passwordValue;
    }
    public void setPasswordValue(String passwordValue) {
        this.passwordValue = passwordValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getEmailValue(), user.getEmailValue()) &&
                Objects.equals(getUsernameValue(), user.getUsernameValue()) &&
                Objects.equals(getPasswordValue(), user.getPasswordValue());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getEmailValue(), getUsernameValue(), getPasswordValue());
    }

    @Override
    public String toString() {
        return "User{" +
                "emailValue='" + emailValue + '\'' +
                ", usernameValue='" + usernameValue + '\'' +
                ", passwordValue='" + passwordValue + '\'' +
                '}';
    }
}
