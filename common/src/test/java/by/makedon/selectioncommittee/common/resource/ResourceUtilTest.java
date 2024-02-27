package by.makedon.selectioncommittee.common.resource;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Yahor Makedon
 */
public class ResourceUtilTest {
    @BeforeAll
    static void initAll() {
        ResourceUtil.resource("database");
        ResourceUtil.resource("test");
    }

    @ParameterizedTest(name = "Should return propertyValue by provided resourceName and propertyKey: [resourceName=`{0}`, propertyKey=`{1}`, expected=`{2}`]")
    @MethodSource("given_resource_propertyExists")
    void parameterizedTest_resource_propertyExists(String resourceName, String propertyKey, String expected) {
        //when
        Optional<String> actual = ResourceUtil.resource(resourceName).property(propertyKey);

        //then
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    static Stream<Arguments> given_resource_propertyExists() {
        //given
        return Stream.of(
            Arguments.of("database", "database.url", "www.testurl.com"),
            Arguments.of("database", "database.user", "testdbusername"),
            Arguments.of("database", "database.password", "testdbpassword"),
            Arguments.of("test", "test1", "qwerty"),
            Arguments.of("test", "test2", "abc"),
            Arguments.of("test", "test3", "42")
        );
    }

    @ParameterizedTest(name = "Should return Optional.empty(), because provided property does not exist: [resourceName=`{0}`, propertyKey=`{1}`]")
    @MethodSource("given_resource_propertyNotExists")
    void parameterizedTest_resource_propertyNotExists(String resourceName, String propertyKey) {
        //when & then
        Optional<String> actual = ResourceUtil.resource(resourceName).property(propertyKey);
        assertFalse(actual.isPresent());
    }

    static Stream<Arguments> given_resource_propertyNotExists() {
        //given
        return Stream.of(
            Arguments.of("database", "database.url1"),
            Arguments.of("database", ""),
            Arguments.of("database", "   "),
            Arguments.of("test", "-qs"),
            Arguments.of("test", "wqe"),
            Arguments.of("test", "1")
        );
    }

    @Test
    void test_resourceNotExists() {
        //when & then
        ResourceMissingException exception = assertThrows(ResourceMissingException.class, () -> ResourceUtil.resource("unknown"));
        assertEquals("Requested resource `unknown` does not exist", exception.getMessage());
    }
}
