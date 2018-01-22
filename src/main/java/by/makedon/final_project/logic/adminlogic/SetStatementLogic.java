package by.makedon.final_project.logic.adminlogic;

import by.makedon.final_project.dao.admindao.AdminDAO;
import by.makedon.final_project.dao.admindao.AdminDAOImpl;
import by.makedon.final_project.entity.EnrolleeState;
import by.makedon.final_project.exception.DAOException;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SetStatementLogic {
    public void doAction() throws DAOException {
        AdminDAO dao = AdminDAOImpl.getInstance();
        Set<EnrolleeState> stateSet = dao.takeAllEnrolleeStates();
        Set<Long> specialityIdSet = createSpecialityIdSet(stateSet);

    }

    private Set<Long> createSpecialityIdSet(Set<EnrolleeState> stateSet) {
        Iterator<EnrolleeState> iterator = stateSet.iterator();
        Set<Long> specialityIdSet = new HashSet<Long>();
        while (iterator.hasNext()) {
            EnrolleeState enrolleeState = iterator.next();
            long specialityId = enrolleeState.getSpecialityId();
            specialityIdSet.add(specialityId);
        }
        return specialityIdSet;
    }
}
