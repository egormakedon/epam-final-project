package validator;

import by.makedon.selectioncommittee.validator.UserValidator;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserValidatorTest {

    @Test
    public void validateEmailTrueTest1() {
        String email = "mail@gmail.com";
        Assert.assertTrue(UserValidator.validateEmail(email));
    }

    @Test
    public void validateEmailTrueTest2() {
        String email = "mail_11S@gmail.com";
        Assert.assertTrue(UserValidator.validateEmail(email));
    }

    @Test
    public void validateEmailTrueTest3() {
        String email = "mail.egor.Asss@gmail.com";
        Assert.assertTrue(UserValidator.validateEmail(email));
    }

    @Test
    public void validateEmailTrueTest4() {
        String email = "Email.egor.$Asss@gmail.com";
        Assert.assertTrue(UserValidator.validateEmail(email));
    }

    @Test
    public void validateEmailFalseTest1() {
        String email = "mail";
        Assert.assertFalse(UserValidator.validateEmail(email));
    }

    @Test
    public void validateEmailFalseTest2() {
        String email = "";
        Assert.assertFalse(UserValidator.validateEmail(email));
    }

    @Test
    public void validateEmailFalseTest3() {
        String email = "123";
        Assert.assertFalse(UserValidator.validateEmail(email));
    }

    @Test
    public void validateEmailFalseTest4() {
        String email = "mail.@gmail";
        Assert.assertFalse(UserValidator.validateEmail(email));
    }

    @Test
    public void validateEmailFalseTest5() {
        String email = "mail.@gmail.com@";
        Assert.assertFalse(UserValidator.validateEmail(email));
    }

    @Test
    public void validateEmailNullTest() {
        Assert.assertFalse(UserValidator.validateEmail(null));
    }

    @Test
    public void validateUsernameTrueTest1() {
        String username = "egor";
        Assert.assertTrue(UserValidator.validateUsername(username));
    }

    @Test
    public void validateUsernameTrueTest2() {
        String username = "Egor123";
        Assert.assertTrue(UserValidator.validateUsername(username));
    }

    @Test
    public void validateUsernameTrueTest3() {
        String username = "Egor123_211";
        Assert.assertTrue(UserValidator.validateUsername(username));
    }

    @Test
    public void validateUsernameFalseTest1() {
        String username = "Ego";
        Assert.assertFalse(UserValidator.validateUsername(username));
    }

    @Test
    public void validateUsernameFalseTest2() {
        String username = "Egowwwwwwwwwwwwwwwwwwwwwww";
        Assert.assertFalse(UserValidator.validateUsername(username));
    }

    @Test
    public void validateUsernameFalseTest3() {
        String username = "Egowww_,.wwwww";
        Assert.assertFalse(UserValidator.validateUsername(username));
    }

    @Test
    public void validateUsernameNullTest() {
        Assert.assertFalse(UserValidator.validateUsername(null));
    }

    @Test
    public void validatePasswordTrueTest1() {
        String password = "Egormakedon1";
        Assert.assertTrue(UserValidator.validatePassword(password));
    }

    @Test
    public void validatePasswordTrueTest2() {
        String password = "egorMakedon12345";
        Assert.assertTrue(UserValidator.validatePassword(password));
    }

    @Test
    public void validatePasswordTrueTest3() {
        String password = "egorMadon12345";
        Assert.assertTrue(UserValidator.validatePassword(password));
    }

    @Test
    public void validatePasswordFalseTest1() {
        String password = "egorMadon1234512312312_";
        Assert.assertFalse(UserValidator.validatePassword(password));
    }

    @Test
    public void validatePasswordFalseTest2() {
        String password = "1sadaMdd_";
        Assert.assertFalse(UserValidator.validatePassword(password));
    }

    @Test
    public void validatePasswordFalseTest3() {
        String password = "Ss1";
        Assert.assertFalse(UserValidator.validatePassword(password));
    }

    @Test
    public void validatePasswordNullTest() {
        Assert.assertFalse(UserValidator.validatePassword(null));
    }
}