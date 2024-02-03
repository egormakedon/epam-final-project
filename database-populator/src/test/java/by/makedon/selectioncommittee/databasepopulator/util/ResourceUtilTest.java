package by.makedon.selectioncommittee.databasepopulator.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Yahor Makedon
 */
class ResourceUtilTest {
    ResourceUtil resourceUtil = new ResourceUtil();

    @ParameterizedTest(name = "Should return property value by provided key: [key=`{0}`, expected=`{1}`]")
    @MethodSource("given_getPropertyByKey")
    void parameterizedTest_getPropertyByKey(String key, String expected) {
        //when
        String actual = resourceUtil.getPropertyByKey(key);

        //then
        assertEquals(expected, actual);
    }

    static Stream<Arguments> given_getPropertyByKey() {
        //given
        return Stream.of(
            Arguments.of(ResourceUtil.DATABASE_URL_KEY, "www.testurl.com"),
            Arguments.of(ResourceUtil.DATABASE_USER_KEY, "testdbusername"),
            Arguments.of(ResourceUtil.DATABASE_PASSWORD_KEY, "testdbpassword"),
            Arguments.of(null, ""),
            Arguments.of("", ""),
            Arguments.of(" ", ""),
            Arguments.of("   ", ""),
            Arguments.of("-1", ""),
            Arguments.of("qwerty", "")
        );
    }
}
