package by.makedon.selectioncommittee.databasepopulator.util;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Yahor Makedon
 */
@Slf4j
public final class ArgsUtil {
    private final Args jArgs;
    private final JCommander jCommander;

    public ArgsUtil(String[] inputArgs) {
        jArgs = new Args();
        jCommander = JCommander.newBuilder()
            .addObject(jArgs)
            .build();
        parseInputArgs(inputArgs);
    }
    private void parseInputArgs(String[] inputArgs) {
        log.info("Parse input args: `{}`", StringUtils.join(inputArgs, " "));
        jCommander.parse(inputArgs);
        log.info("Input args are parsed successfully");
    }

    public boolean isHelp() {
        return jArgs.isHelp();
    }
    public void callHelp() {
        log.info("Call help");
        jCommander.usage();
    }
    public int getNumberOfEnrollees() {
        return jArgs.getNumberOfEnrollees();
    }

    private class Args {
        @Parameter(
            names = {"--generate", "-g"},
            description = "Generate number of enrollees. Number must be >= 0.",
            required = true,
            validateWith = NumberOfEnrolleesValidator.class
        )
        @Setter
        @Getter
        private int numberOfEnrollees;

        @Parameter(names = "--help", help = true)
        @Setter
        @Getter
        private boolean help;
    }
    public static class NumberOfEnrolleesValidator implements IParameterValidator {
        private static final String NUMBER_REGEX = "^(\\d+)$";

        @Override
        public void validate(String name, String value) throws ParameterException {
            if (isInvalidNumber(value)) {
                throw new ParameterException(String.format("Provided invalid number of enrollees - %s", value));
            }
        }

        private boolean isValidNumber(String value) {
            return value.matches(NUMBER_REGEX);
        }

        private boolean isInvalidNumber(String value) {
            return !isValidNumber(value);
        }
    }
}
