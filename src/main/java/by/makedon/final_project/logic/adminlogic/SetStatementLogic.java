package by.makedon.final_project.logic.adminlogic;

import by.makedon.final_project.dao.admindao.AdminDAO;
import by.makedon.final_project.dao.admindao.AdminDAOImpl;
import by.makedon.final_project.entity.EnrolleeState;
import by.makedon.final_project.exception.DAOException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SetStatementLogic {
    private static final String ENLISTED = "Зачислен";
    private static final String NOT_LISTED = "Не зачислен";

    public void doAction() throws DAOException {
        AdminDAO dao = AdminDAOImpl.getInstance();
        Set<EnrolleeState> enrolleeStateSet = dao.takeAllEnrolleeStates();
        Map<Long,Integer> specialityIdNumberOfSeatsMap = dao.takeSpecialityIdWithNumberOfSeats();
        setStatementToEnrolleeState(enrolleeStateSet, specialityIdNumberOfSeatsMap);
        dao.refreshStatement(enrolleeStateSet);
    }

    private void setStatementToEnrolleeState(Set<EnrolleeState> enrolleeStateSet, Map<Long,Integer> specialityIdNumberOfSeatsMap) {
        for (Map.Entry<Long,Integer> specIdNumOfSeatsEntry : specialityIdNumberOfSeatsMap.entrySet()) {
            long specialityIdValue = specIdNumOfSeatsEntry.getKey();
            int numberOfSeatsValue = specIdNumOfSeatsEntry.getValue();

            Iterator<EnrolleeState> stateIterator = enrolleeStateSet.iterator();
            List<EnrolleeState> listOfEnrolleeStates = createEnrolleeListBySpecialityId(stateIterator, specialityIdValue);
            if (!listOfEnrolleeStates.isEmpty()) {
                sortListOfEnrolleStatesByScore(listOfEnrolleeStates);
                setStatementToEnrolleeState(listOfEnrolleeStates, numberOfSeatsValue);
            }
        }
    }
    private List<EnrolleeState> createEnrolleeListBySpecialityId(Iterator<EnrolleeState> stateIterator, long specialityIdValue) {
        List<EnrolleeState> listOfEnrolleeStates = new ArrayList<EnrolleeState>();
        while (stateIterator.hasNext()) {
            EnrolleeState enrolleeState = stateIterator.next();
            long enrolleeStateSpecialityId = enrolleeState.getSpecialityId();
            if (enrolleeStateSpecialityId == specialityIdValue) {
                listOfEnrolleeStates.add(enrolleeState);
            }
        }
        return listOfEnrolleeStates;
    }
    private void sortListOfEnrolleStatesByScore(List<EnrolleeState> listOfEnrolleeStates) {
        listOfEnrolleeStates.sort(new Comparator<EnrolleeState>() {
            @Override
            public int compare(EnrolleeState enrolleeState1, EnrolleeState enrolleeState2) {
                return enrolleeState2.getScore() - enrolleeState1.getScore();
            }
        });
    }
    private void setStatementToEnrolleeState(List<EnrolleeState> listOfEnrolleeStates, int numberOfSeatsValue) {
        for (int index = 0; index < listOfEnrolleeStates.size(); index++) {
            EnrolleeState enrolleeState = listOfEnrolleeStates.get(index);
            if (index + 1 <= numberOfSeatsValue) {
                enrolleeState.setStatement(ENLISTED);
            } else {
                enrolleeState.setStatement(NOT_LISTED);
            }
        }
    }
}