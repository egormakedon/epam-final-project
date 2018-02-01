package validator;

import by.makedon.selectioncommittee.validator.EnrolleeValidator;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class EnrolleeValidatorTest {
    @Test
    public void validateTrueTest1() {
        List<String> parameters = new ArrayList<String>();
        parameters.add("Egor");
        parameters.add("Makedon");
        parameters.add("");
        parameters.add("ABC12345");
        parameters.add("375296434537");
        parameters.add(null);
        parameters.add(null);
        parameters.add(null);
        parameters.add("20");
        parameters.add(null);
        parameters.add("30");
        parameters.add(null);
        parameters.add("10");
        parameters.add(null);
        parameters.add(null);
        parameters.add(null);
        parameters.add("100");
        Assert.assertTrue(EnrolleeValidator.validate(parameters));
    }

    @Test
    public void validateTrueTest2() {
        List<String> parameters = new ArrayList<String>();
        parameters.add("Егор");
        parameters.add("Македон");
        parameters.add("Alexandrovich");
        parameters.add("ABC12345");
        parameters.add("375296434537");
        parameters.add(null);
        parameters.add(null);
        parameters.add(null);
        parameters.add("20");
        parameters.add(null);
        parameters.add("30");
        parameters.add(null);
        parameters.add("10");
        parameters.add(null);
        parameters.add(null);
        parameters.add(null);
        parameters.add("100");
        Assert.assertTrue(EnrolleeValidator.validate(parameters));
    }

    @Test
    public void validateTrueTest3() {
        List<String> parameters = new ArrayList<String>();
        parameters.add("Егор");
        parameters.add("Македон");
        parameters.add("Alexandrovich");
        parameters.add("12312345");
        parameters.add("375296434537");
        parameters.add(null);
        parameters.add("10");
        parameters.add(null);
        parameters.add("20");
        parameters.add(null);
        parameters.add(null);
        parameters.add(null);
        parameters.add("10");
        parameters.add(null);
        parameters.add(null);
        parameters.add(null);
        parameters.add("100");
        Assert.assertTrue(EnrolleeValidator.validate(parameters));
    }

    @Test
    public void validateFalseTest1() {
        List<String> parameters = new ArrayList<String>();
        parameters.add("egor");
        parameters.add("Makedon");
        parameters.add("Alexandrovich");
        parameters.add("12312345");
        parameters.add("375296434537");
        parameters.add(null);
        parameters.add("10");
        parameters.add(null);
        parameters.add("20");
        parameters.add(null);
        parameters.add(null);
        parameters.add(null);
        parameters.add("10");
        parameters.add(null);
        parameters.add(null);
        parameters.add(null);
        parameters.add("100");
        Assert.assertFalse(EnrolleeValidator.validate(parameters));
    }

    @Test
    public void validateFalseTest2() {
        List<String> parameters = new ArrayList<String>();
        parameters.add("Egor");
        parameters.add("121");
        parameters.add("Alexandrovich");
        parameters.add("12312345");
        parameters.add("375296434537");
        parameters.add(null);
        parameters.add("10");
        parameters.add(null);
        parameters.add("20");
        parameters.add(null);
        parameters.add(null);
        parameters.add(null);
        parameters.add("10");
        parameters.add(null);
        parameters.add(null);
        parameters.add(null);
        parameters.add("100");
        Assert.assertFalse(EnrolleeValidator.validate(parameters));
    }

    @Test
    public void validateFalseTest3() {
        List<String> parameters = new ArrayList<String>();
        parameters.add("Egor");
        parameters.add("121");
        parameters.add("");
        parameters.add("-az111111");
        parameters.add("1");
        parameters.add(null);
        parameters.add("10");
        parameters.add(null);
        parameters.add("20");
        parameters.add(null);
        parameters.add(null);
        parameters.add(null);
        parameters.add("10");
        parameters.add(null);
        parameters.add(null);
        parameters.add(null);
        parameters.add("-3");
        Assert.assertFalse(EnrolleeValidator.validate(parameters));
    }

    @Test
    public void validateFalseTest4() {
        List<String> parameters = new ArrayList<String>();
        parameters.add("Egor");
        parameters.add("121");
        parameters.add("");
        parameters.add("-az111111");
        parameters.add("1");
        parameters.add(null);
        Assert.assertFalse(EnrolleeValidator.validate(parameters));
    }

    @Test
    public void validateFalseTest5() {
        List<String> parameters = new ArrayList<String>();
        parameters.add(null);
        parameters.add(null);
        parameters.add(null);
        parameters.add(null);
        parameters.add(null);
        parameters.add(null);
        parameters.add(null);
        parameters.add(null);
        parameters.add(null);
        parameters.add(null);
        parameters.add(null);
        parameters.add(null);
        parameters.add(null);
        parameters.add(null);
        parameters.add(null);
        parameters.add(null);
        parameters.add(null);
        Assert.assertFalse(EnrolleeValidator.validate(parameters));
    }
}
