package by.makedon.selectioncommittee.databasepopulator.dao;

import by.makedon.selectioncommittee.common.dao.Dao;
import by.makedon.selectioncommittee.databasepopulator.entity.Enrollee;

import java.util.List;

/**
 * @author Yahor Makedon
 */
public interface EnrolleeDao extends Dao<Enrollee, Integer> {
    int BATCH_SIZE = 20;
    String AUTO_GENERATED_KEY_ENROLLEE_ID = "e_id";
    String[] AUTO_GENERATED_KEYS = new String[] { AUTO_GENERATED_KEY_ENROLLEE_ID };
    String SQL_INSERT = "INSERT INTO enrollee(passport_id,country_domen,s_id,surname,name,second_name,phone,russian_lang,belorussian_lang,physics,math,chemistry,biology,foreign_lang,history_of_belarus,social_studies,geography,history,certificate,date) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    void batchCreate(List<Enrollee> enrollees);
}
