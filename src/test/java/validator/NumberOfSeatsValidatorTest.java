package validator;

import by.makedon.selectioncommittee.validator.NumberOfSeatsValidator;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NumberOfSeatsValidatorTest {

    @Test
    public void numberOfSeatsTrueTest() {
        String number = "100";
        Assert.assertTrue(NumberOfSeatsValidator.validate(number));
    }

    @Test
    public void numberOfSeatsFalseTest1() {
        String number = "10s0";
        Assert.assertFalse(NumberOfSeatsValidator.validate(number));
    }

    @Test
    public void numberOfSeatsFalseTest2() {
        String number = "";
        Assert.assertFalse(NumberOfSeatsValidator.validate(number));
    }

    @Test
    public void numberOfSeatsNullTest() {
        Assert.assertFalse(NumberOfSeatsValidator.validate(null));
    }
}
