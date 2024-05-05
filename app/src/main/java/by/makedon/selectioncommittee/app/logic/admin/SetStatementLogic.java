package by.makedon.selectioncommittee.app.logic.admin;

import by.makedon.selectioncommittee.app.dao.AdminDao;
import by.makedon.selectioncommittee.app.dao.impl.AdminDaoImpl;
import by.makedon.selectioncommittee.app.entity.EnrolleeState;
import by.makedon.selectioncommittee.app.dao.DAOException;
import by.makedon.selectioncommittee.app.logic.LogicException;
import by.makedon.selectioncommittee.app.logic.Logic;
import by.makedon.selectioncommittee.app.mail.MailBuilder;
import by.makedon.selectioncommittee.app.mail.MailProperty;
import by.makedon.selectioncommittee.app.mail.MailTemplateType;
import by.makedon.selectioncommittee.app.mail.MailSendTask;
import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SetStatementLogic implements Logic {


    private static final String CHANGED_STATEMENT_NOTICE = "changed statement notice";

    @Override
    public void doAction(@NotNull List<String> parameters) throws LogicException {
        if (!parameters.isEmpty()) {
            throw new LogicException("wrong number of parameters");
        }

        AdminDao dao = AdminDaoImpl.getInstance();
        try {
            Set<EnrolleeState> enrolleeStateSet = dao.getEnrolleeStates();
            Map<Long,Integer> specialityIdNumberOfSeatsMap = dao.getSpecialityIdToNumberOfSeatsMap();
            setStatement(enrolleeStateSet, specialityIdNumberOfSeatsMap);
            dao.refreshStatement(enrolleeStateSet);

            List<String> emailList = dao.getUserEmailList();
            for (String emailValue : emailList) {
                String templatePath = MailTemplateType.SET_STATEMENT.getTemplatePath();
                MailBuilder mailBuilder = new MailBuilder(templatePath);
                String mailText = mailBuilder.takeMailTemplate();

                MailSendTask mail = new MailSendTask(emailValue, CHANGED_STATEMENT_NOTICE, mailText, MailProperty.getInstance().getProperties());
                mail.start();
            }
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    private void setStatement(Set<EnrolleeState> enrolleeStateSet, Map<Long,Integer> specialityIdNumberOfSeatsMap) {
        for (Map.Entry<Long,Integer> entry : specialityIdNumberOfSeatsMap.entrySet()) {
            long specialityIdValue = entry.getKey();
            int numberOfSeatsValue = entry.getValue();

            Iterator<EnrolleeState> iterator = enrolleeStateSet.iterator();
            List<EnrolleeState> enrolleeStateList = createEnrolleeStateListBySpecialityId(iterator, specialityIdValue);

            if (!enrolleeStateList.isEmpty()) {
                sortEnrolleeStateListByScoreDesc(enrolleeStateList);
                refreshStatement(enrolleeStateList, numberOfSeatsValue);
            }
        }
    }

    private List<EnrolleeState> createEnrolleeStateListBySpecialityId(Iterator<EnrolleeState> iterator, long specialityIdValue) {
        List<EnrolleeState> enrolleeStateList = new ArrayList<EnrolleeState>();
        while (iterator.hasNext()) {
            EnrolleeState enrolleeState = iterator.next();
            if (enrolleeState.getSpecialityId() == specialityIdValue) {
                enrolleeStateList.add(enrolleeState);
            }
        }
        return enrolleeStateList;
    }

    private void sortEnrolleeStateListByScoreDesc(List<EnrolleeState> enrolleeStateList) {
        enrolleeStateList.sort(new Comparator<EnrolleeState>() {
            @Override
            public int compare(EnrolleeState enrolleeState1, EnrolleeState enrolleeState2) {
                if (enrolleeState2.getScore() == enrolleeState1.getScore()) {
                    Date date1 = Date.valueOf(enrolleeState1.getDate());
                    Date date2 = Date.valueOf(enrolleeState2.getDate());
                    return (int)(date1.getTime() - date2.getTime());
                }
                return enrolleeState2.getScore() - enrolleeState1.getScore();
            }
        });
    }

    private void refreshStatement(List<EnrolleeState> enrolleeStateList, int numberOfSeatsValue) {
        for (int index = 0; index < enrolleeStateList.size(); index++) {
            EnrolleeState enrolleeState = enrolleeStateList.get(index);

            if (index + 1 <= numberOfSeatsValue) {
                enrolleeState.setStatementEnlisted();
            } else {
                enrolleeState.setStatementNotListed();
            }
        }
    }
}
