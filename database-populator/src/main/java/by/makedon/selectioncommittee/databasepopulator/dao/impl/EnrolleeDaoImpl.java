package by.makedon.selectioncommittee.databasepopulator.dao.impl;

import by.makedon.selectioncommittee.common.dao.DaoException;
import by.makedon.selectioncommittee.databasepopulator.dao.EnrolleeDao;
import by.makedon.selectioncommittee.databasepopulator.entity.Enrollee;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * @author Yahor Makedon
 */
@Slf4j
public class EnrolleeDaoImpl implements EnrolleeDao {
    @Override
    public void batchCreate(List<Enrollee> enrollees) {
        executeTransactionMode(Connection.TRANSACTION_READ_COMMITTED, connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT)) {
                for (List<Enrollee> enrolleesBatch : Lists.partition(enrollees, BATCH_SIZE)) {
                    for (Enrollee enrollee : enrolleesBatch) {
                        appendEnrolleeToCreationBatch(preparedStatement, enrollee);
                    }
                    preparedStatement.executeBatch();
                    log.debug("Successfully has inserted enrollees batch:\n{}", enrolleesBatch);
                }
                connection.commit();
                return Optional.empty();
            } catch (SQLException e) {
                rollback(connection);
                throw new DaoException(e.getMessage(), e);
            }
        });
        log.info("Successfully has inserted list of enrollees:\n{}", enrollees);
    }

    private static void appendEnrolleeToCreationBatch(PreparedStatement preparedStatement, Enrollee enrollee) throws SQLException {
        preparedStatement.setString(1, enrollee.getPassportId());
        preparedStatement.setString(2, enrollee.getCountryDomain());
        preparedStatement.setInt(3, enrollee.getSpecialityId());
        preparedStatement.setString(4, enrollee.getSurname());
        preparedStatement.setString(5, enrollee.getName());
        preparedStatement.setString(6, enrollee.getSecondName());
        preparedStatement.setString(7, enrollee.getPhoneNumber());
        preparedStatement.setInt(8, enrollee.getRussianLang());
        preparedStatement.setInt(9, enrollee.getBelorussianLang());
        preparedStatement.setInt(10, enrollee.getPhysics());
        preparedStatement.setInt(11, enrollee.getMath());
        preparedStatement.setInt(12, enrollee.getChemistry());
        preparedStatement.setInt(13, enrollee.getBiology());
        preparedStatement.setInt(14, enrollee.getForeignLang());
        preparedStatement.setInt(15, enrollee.getHistoryOfBelarus());
        preparedStatement.setInt(16, enrollee.getSocialStudies());
        preparedStatement.setInt(17, enrollee.getGeography());
        preparedStatement.setInt(18, enrollee.getHistory());
        preparedStatement.setInt(19, enrollee.getCertificate());
        preparedStatement.setString(20, enrollee.getDate());
        preparedStatement.addBatch();
    }

    @Override
    public Logger getLogger() {
        return log;
    }
}
