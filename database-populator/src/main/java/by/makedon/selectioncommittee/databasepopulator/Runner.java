package by.makedon.selectioncommittee.databasepopulator;

import by.makedon.selectioncommittee.common.connectionpool.ConnectionPool;
import by.makedon.selectioncommittee.databasepopulator.service.EnrolleeDatabasePopulatorFacadeService;
import by.makedon.selectioncommittee.databasepopulator.util.ArgsUtil;
import com.beust.jcommander.ParameterException;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @author Yahor Makedon
 */
@Slf4j
public class Runner {
    public static void main(String[] args) {
        ArgsUtil argsUtil = null;
        try {
            argsUtil = new ArgsUtil(args);
            run(argsUtil);
        } catch (ParameterException e) {
            log.error(e.getMessage(), e);
            if (Objects.nonNull(argsUtil)) {
                argsUtil.callHelp();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            ConnectionPool.getInstance().destroy();
        }
    }

    private static void run(ArgsUtil argsUtil) {
        if (argsUtil.isHelp()) {
            argsUtil.callHelp();
        }
        populateDatabase(argsUtil.getNumberOfEnrollees());
    }

    private static void populateDatabase(int numberOfEnrollees) {
        EnrolleeDatabasePopulatorFacadeService service = new EnrolleeDatabasePopulatorFacadeService();
        service.populateDatabaseWith(numberOfEnrollees);
    }
}
