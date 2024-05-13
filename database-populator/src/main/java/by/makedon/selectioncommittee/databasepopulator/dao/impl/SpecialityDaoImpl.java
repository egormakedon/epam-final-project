package by.makedon.selectioncommittee.databasepopulator.dao.impl;

import by.makedon.selectioncommittee.common.dao.DaoException;
import by.makedon.selectioncommittee.databasepopulator.dao.SpecialityDao;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author Yahor Makedon
 */
@Slf4j
public class SpecialityDaoImpl implements SpecialityDao {
    @Override
    public Set<Integer> getAllSpecialityId() {
        Optional<Set<Integer>> resultOptional = executeReadOnlyMode(connection -> {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_SPECIALITY_ID);
                log.debug("Successfully has selected all specialityId");
                if (resultSet.next()) {
                    return Optional.of(extractSpecialityIdsStartingFromFirst(resultSet));
                }
                return Optional.empty();
            } catch (SQLException e) {
                throw new DaoException(e.getMessage(), e);
            }
        });
        Set<Integer> result = resultOptional.orElse(Collections.emptySet());
        log.info("Successfully has returned set of all specialityId:\n{}", result);
        return result;
    }

    private static Set<Integer> extractSpecialityIdsStartingFromFirst(ResultSet resultSet) throws SQLException {
        Set<Integer> specialityIds = new HashSet<>();
        do {
            specialityIds.add(resultSet.getInt(FIELD_SPECIALITY_ID));
        } while (resultSet.next());
        return specialityIds;
    }

    @Override
    public Logger getLogger() {
        return log;
    }
}
