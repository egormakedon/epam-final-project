package by.makedon.selectioncommittee.app.validator;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class NumberOfSeatsValidatorTest {
    NumberOfSeatsValidator numberOfSeatsValidator;

    @BeforeClass
    void init() {
        numberOfSeatsValidator = new NumberOfSeatsValidator();
    }

    @Test
    public void numberOfSeatsTrueTest() {
        String number = "100";
        Assert.assertTrue(numberOfSeatsValidator.validate(number));
    }

    @Test
    public void numberOfSeatsFalseTest1() {
        String number = "10s0";
        Assert.assertFalse(numberOfSeatsValidator.validate(number));
    }

    @Test
    public void numberOfSeatsFalseTest2() {
        String number = "";
        Assert.assertFalse(numberOfSeatsValidator.validate(number));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void numberOfSeatsNullTest() {
        Assert.assertFalse(numberOfSeatsValidator.validate(null));
    }
}
