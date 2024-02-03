package by.makedon.selectioncommittee.databasepopulator.util;

import com.beust.jcommander.ParameterException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Yahor Makedon
 */
class ArgsUtilTest {
    @Test
    void test_isHelp() {
        //given
        String[] inputArgs = { "--help" };

        //when
        ArgsUtil argsUtil = new ArgsUtil(inputArgs);
        boolean actual = argsUtil.isHelp();

        //then
        assertTrue(actual);
    }

    @ParameterizedTest(name = "Should return number of enrollees: [inputArgs=`{0}`, expected=`{1}`]")
    @MethodSource("given_getNumberOfEnrollees")
    void parameterizedTest_getNumberOfEnrollees(String[] inputArgs, int expected) {
        //when
        ArgsUtil argsUtil = new ArgsUtil(inputArgs);
        int actual = argsUtil.getNumberOfEnrollees();

        //then
        assertEquals(expected, actual);
    }

    static Stream<Arguments> given_getNumberOfEnrollees() {
        //given
        return Stream.of(
            Arguments.of(new String[]{ "--generate", "10" }, 10),
            Arguments.of(new String[]{ "-g", "0" }, 0),
            Arguments.of(new String[]{ "-g", "1" }, 1),
            Arguments.of(new String[]{ "-g", "25" }, 25),
            Arguments.of(new String[]{ "-g", "100" }, 100),
            Arguments.of(new String[]{ "-g", "1000" }, 1000)
        );
    }

    @ParameterizedTest(name = "Should throw ParameterException: [inputArgs=`{0}`, expected=`{1}`]")
    @MethodSource("given_invalidInputArgs")
    void parameterizedTest_invalidInputArgs(String[] inputArgs, String expected) {
        //when & then
        ParameterException actual = assertThrows(ParameterException.class, () -> new ArgsUtil(inputArgs));
        assertEquals(expected, actual.getMessage());
    }

    static Stream<Arguments> given_invalidInputArgs() {
        //given
        return Stream.of(
            Arguments.of(new String[]{}, "The following option is required: [--generate | -g]"),
            Arguments.of(new String[]{ "" }, "Was passed main parameter '' but no main parameter was defined in your arg class"),
            Arguments.of(new String[]{ " " }, "Was passed main parameter ' ' but no main parameter was defined in your arg class"),
            Arguments.of(new String[]{ "     " }, "Was passed main parameter '     ' but no main parameter was defined in your arg class"),
            Arguments.of(new String[]{ "null" }, "Was passed main parameter 'null' but no main parameter was defined in your arg class"),
            Arguments.of(new String[]{ "--g", "0" }, "Was passed main parameter '--g' but no main parameter was defined in your arg class"),
            Arguments.of(new String[]{ "-h" }, "Was passed main parameter '-h' but no main parameter was defined in your arg class"),
            Arguments.of(new String[]{ "-g", "0", "0" }, "Was passed main parameter '0' but no main parameter was defined in your arg class"),
            Arguments.of(new String[]{ "-g", "str" }, "Provided invalid number of enrollees - str"),
            Arguments.of(new String[]{ "-g", "-1" }, "Provided invalid number of enrollees - -1"),
            Arguments.of(new String[]{ "-g", "-1.5" }, "Provided invalid number of enrollees - -1.5"),
            Arguments.of(new String[]{ "-g", "0.5" }, "Provided invalid number of enrollees - 0.5"),
            Arguments.of(new String[]{ "-g", "10.123" }, "Provided invalid number of enrollees - 10.123"),
            Arguments.of(new String[]{ "-g", "true" }, "Provided invalid number of enrollees - true"),
            Arguments.of(new String[]{ "-g", "false" }, "Provided invalid number of enrollees - false"),
            Arguments.of(new String[]{ "-g", "" }, "Provided invalid number of enrollees - "),
            Arguments.of(new String[]{ "-g", "q" }, "Provided invalid number of enrollees - q"),
            Arguments.of(new String[]{ "1", "2", "3" }, "Was passed main parameter '1' but no main parameter was defined in your arg class"),
            Arguments.of(new String[]{ "1", "-g", "3", "--help" }, "Was passed main parameter '1' but no main parameter was defined in your arg class")
        );
    }
}
