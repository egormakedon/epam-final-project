package by.makedon.selectioncommittee.logic.admin;

import by.makedon.selectioncommittee.dao.admin.AdminDAO;
import by.makedon.selectioncommittee.dao.admin.AdminDAOImpl;
import by.makedon.selectioncommittee.entity.enrollee.EnrolleeState;
import by.makedon.selectioncommittee.exception.DAOException;
import by.makedon.selectioncommittee.exception.LogicException;
import by.makedon.selectioncommittee.logic.Logic;
import com.sun.istack.internal.NotNull;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SetStatementLogic implements Logic {
    private static final String ENLISTED = "Зачислен";
    private static final String NOT_LISTED = "Не зачислен";

    @Override
    public void doAction(@NotNull List<String> parameters) throws LogicException {
        if (!parameters.isEmpty()) {
            throw new LogicException("wrong number of parameters");
        }

        AdminDAO dao = AdminDAOImpl.getInstance();
        try {
            Set<EnrolleeState> enrolleeStateSet = dao.takeEnrolleeStateSet();
            Map<Long,Integer> specialityIdNumberOfSeatsMap = dao.takeSpecialityIdNumberOfSeatsMap();
            setStatement(enrolleeStateSet, specialityIdNumberOfSeatsMap);
            dao.refreshStatement(enrolleeStateSet);
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
                enrolleeState.setStatement(ENLISTED);
            } else {
                enrolleeState.setStatement(NOT_LISTED);
            }
        }
    }
}