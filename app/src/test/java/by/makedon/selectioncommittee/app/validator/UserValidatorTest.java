package by.makedon.selectioncommittee.app.validator;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UserValidatorTest {
    UserValidator userValidator;

    @BeforeClass
    void init() {
        userValidator = new UserValidator();
    }

    @Test
    public void validateEmailTrueTest1() {
        String email = "mail@gmail.com";
        Assert.assertTrue(userValidator.validateEmail(email));
    }

    @Test
    public void validateEmailTrueTest2() {
        String email = "mail_11S@gmail.com";
        Assert.assertTrue(userValidator.validateEmail(email));
    }

    @Test
    public void validateEmailTrueTest3() {
        String email = "mail.egor.Asss@gmail.com";
        Assert.assertTrue(userValidator.validateEmail(email));
    }

    @Test
    public void validateEmailTrueTest4() {
        String email = "Email.egor.$Asss@gmail.com";
        Assert.assertTrue(userValidator.validateEmail(email));
    }

    @Test
    public void validateEmailFalseTest1() {
        String email = "mail";
        Assert.assertFalse(userValidator.validateEmail(email));
    }

    @Test
    public void validateEmailFalseTest2() {
        String email = "";
        Assert.assertFalse(userValidator.validateEmail(email));
    }

    @Test
    public void validateEmailFalseTest3() {
        String email = "123";
        Assert.assertFalse(userValidator.validateEmail(email));
    }

    @Test
    public void validateEmailFalseTest4() {
        String email = "mail.@gmail";
        Assert.assertFalse(userValidator.validateEmail(email));
    }

    @Test
    public void validateEmailFalseTest5() {
        String email = "mail.@gmail.com@";
        Assert.assertFalse(userValidator.validateEmail(email));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void validateEmailNullTest() {
        Assert.assertFalse(userValidator.validateEmail(null));
    }

    @Test
    public void validateUsernameTrueTest1() {
        String username = "egor";
        Assert.assertTrue(userValidator.validateUsername(username));
    }

    @Test
    public void validateUsernameTrueTest2() {
        String username = "Egor123";
        Assert.assertTrue(userValidator.validateUsername(username));
    }

    @Test
    public void validateUsernameTrueTest3() {
        String username = "Egor123_211";
        Assert.assertTrue(userValidator.validateUsername(username));
    }

    @Test
    public void validateUsernameFalseTest1() {
        String username = "Ego";
        Assert.assertFalse(userValidator.validateUsername(username));
    }

    @Test
    public void validateUsernameFalseTest2() {
        String username = "Egowwwwwwwwwwwwwwwwwwwwwww";
        Assert.assertFalse(userValidator.validateUsername(username));
    }

    @Test
    public void validateUsernameFalseTest3() {
        String username = "Egowww_,.wwwww";
        Assert.assertFalse(userValidator.validateUsername(username));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void validateUsernameNullTest() {
        Assert.assertFalse(userValidator.validateUsername(null));
    }

    @Test
    public void validatePasswordTrueTest1() {
        String password = "Egormakedon1";
        Assert.assertTrue(userValidator.validatePassword(password));
    }

    @Test
    public void validatePasswordTrueTest2() {
        String password = "egorMakedon12345";
        Assert.assertTrue(userValidator.validatePassword(password));
    }

    @Test
    public void validatePasswordTrueTest3() {
        String password = "egorMadon12345";
        Assert.assertTrue(userValidator.validatePassword(password));
    }

    @Test
    public void validatePasswordFalseTest1() {
        String password = "egorMadon1234512312312_";
        Assert.assertFalse(userValidator.validatePassword(password));
    }

    @Test
    public void validatePasswordFalseTest2() {
        String password = "1sadaMdd_";
        Assert.assertFalse(userValidator.validatePassword(password));
    }

    @Test
    public void validatePasswordFalseTest3() {
        String password = "Ss1";
        Assert.assertFalse(userValidator.validatePassword(password));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void validatePasswordNullTest() {
        Assert.assertFalse(userValidator.validatePassword(null));
    }
}
