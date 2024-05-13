package by.makedon.selectioncommittee.app.logic.admin;

import by.makedon.selectioncommittee.app.dao.AdminDao;
import by.makedon.selectioncommittee.app.dao.impl.AdminDaoImpl;
import by.makedon.selectioncommittee.app.entity.EnrolleeState;
import by.makedon.selectioncommittee.app.logic.Logic;
import by.makedon.selectioncommittee.app.logic.LogicException;
import by.makedon.selectioncommittee.app.mail.Mail;
import by.makedon.selectioncommittee.app.mail.MailSendTask;
import by.makedon.selectioncommittee.app.mail.MailTemplateFactory;
import by.makedon.selectioncommittee.app.mail.MailTemplateType;
import by.makedon.selectioncommittee.app.validator.ValidationException;
import by.makedon.selectioncommittee.common.dao.DaoException;
import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SetStatementLogic implements Logic {
    private static final int VALID_PARAMETERS_SIZE = 0;
    private static final String MAIL_SUBJECT_CHANGED_STATEMENT_NOTIFICATION = "Changed statement notification";

    private final AdminDao adminDao = AdminDaoImpl.getInstance();

    private final Comparator<EnrolleeState> enrolleeStateScoreComparator = createEnrolleeStateScoreComparator();
    private final Comparator<EnrolleeState> enrolleeStateDateComparator = createEnrolleeStateDateComparator();
    private final Comparator<EnrolleeState> enrolleeStateScoreAndDateComparator =
        enrolleeStateScoreComparator.reversed().thenComparing(enrolleeStateDateComparator);

    private static Comparator<EnrolleeState> createEnrolleeStateScoreComparator() {
        return Comparator.comparingInt(EnrolleeState::getScore);
    }

    private static Comparator<EnrolleeState> createEnrolleeStateDateComparator() {
        return (es1, es2) -> {
            Date date1 = Date.valueOf(es1.getDate());
            Date date2 = Date.valueOf(es2.getDate());
            return date1.compareTo(date2);
        };
    }

    @Override
    public int getValidParametersSize() {
        return VALID_PARAMETERS_SIZE;
    }

    @Override
    public void validate(@NotNull List<String> parameters) throws ValidationException {
        //empty
    }

    @Override
    public void action(@NotNull List<String> parameters) throws DaoException, LogicException {
        Set<EnrolleeState> enrolleeStates = adminDao.getEnrolleeStates();
        enrolleeStates = updateEnrolleeStateStatementFor(enrolleeStates);
        adminDao.refreshStatement(enrolleeStates);

        List<String> emails = adminDao.getUserEmailList();
        sendNotificationEmails(emails);
    }

    private Set<EnrolleeState> updateEnrolleeStateStatementFor(@NotNull Set<EnrolleeState> enrolleeStates) {
        Objects.requireNonNull(enrolleeStates);

        Map<Long, List<EnrolleeState>> specialityIdToEnrolleeStatesMap =
            createSpecialityIdToEnrolleeStatesMapFrom(enrolleeStates);
        Map<Long, Integer> specialityIdToNumberOfSeatsMap = adminDao.getSpecialityIdToNumberOfSeatsMap();

        return updateStatement(specialityIdToEnrolleeStatesMap, specialityIdToNumberOfSeatsMap);
    }

    private Map<Long, List<EnrolleeState>> createSpecialityIdToEnrolleeStatesMapFrom(Set<EnrolleeState> enrolleeStates) {
        return enrolleeStates
            .stream()
            .collect(Collectors.groupingBy(EnrolleeState::getSpecialityId));
    }

    private Set<EnrolleeState> updateStatement(Map<Long, List<EnrolleeState>> specialityIdToEnrolleeStatesMap,
                                               Map<Long, Integer> specialityIdToNumberOfSeatsMap) {
        return specialityIdToEnrolleeStatesMap
            .entrySet()
            .stream()
            .flatMap(entry -> {
                List<EnrolleeState> enrolleeStates = entry.getValue();
                int numberOfSeats = specialityIdToNumberOfSeatsMap.getOrDefault(entry.getKey(), 0);
                return getStreamOfUpdatedEnrolleeStatesFrom(enrolleeStates, numberOfSeats);
            })
            .collect(Collectors.toSet());
    }

    private Stream<EnrolleeState> getStreamOfUpdatedEnrolleeStatesFrom(List<EnrolleeState> enrolleeStates, int numberOfSeats) {
        enrolleeStates.sort(enrolleeStateScoreAndDateComparator);

        int separator = Math.min(numberOfSeats, enrolleeStates.size());
        Stream<EnrolleeState> streamOfEnlistedEnrolleeStates = enrolleeStates
            .subList(0, separator)
            .stream()
            .peek(EnrolleeState::setStatementEnlisted);
        Stream<EnrolleeState> streamOfNotListedEnrolleeStates = enrolleeStates
            .subList(separator, enrolleeStates.size())
            .stream()
            .peek(EnrolleeState::setStatementNotListed);

        return Stream.concat(streamOfEnlistedEnrolleeStates, streamOfNotListedEnrolleeStates);
    }

    private void sendNotificationEmails(List<String> emails) {
        emails.forEach(email -> {
            String mailTemplate = MailTemplateFactory.getMailTemplateBy(MailTemplateType.SET_STATEMENT);

            Mail mail = new Mail(email, MAIL_SUBJECT_CHANGED_STATEMENT_NOTIFICATION, mailTemplate);
            MailSendTask mailSendTask = new MailSendTask(mail);
            CompletableFuture.runAsync(mailSendTask);
        });
    }
}
