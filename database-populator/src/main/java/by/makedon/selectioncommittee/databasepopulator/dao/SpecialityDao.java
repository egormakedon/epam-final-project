package by.makedon.selectioncommittee.databasepopulator.dao;

import by.makedon.selectioncommittee.common.dao.Dao;

import java.util.Set;

/**
 * @author Yahor Makedon
 */
public interface SpecialityDao extends Dao<Object, Integer> {
    String FIELD_SPECIALITY_ID = "specialityId";
    String SQL_SELECT_ALL_SPECIALITY_ID = "SELECT s_id AS specialityId FROM speciality";

    Set<Integer> getAllSpecialityId();
}
