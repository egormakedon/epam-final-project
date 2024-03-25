package by.makedon.selectioncommittee.databasepopulator.dao;

import by.makedon.selectioncommittee.common.dao.Dao;
import by.makedon.selectioncommittee.databasepopulator.entity.User;

import java.util.List;

/**
 * @author Yahor Makedon
 */
public interface UserDao extends Dao<User, Integer> {
    int BATCH_SIZE = 20;
    String SQL_INSERT = "INSERT INTO user(email,username,password,e_id) VALUES(?,?,SHA1(?),?)";

    void batchCreate(List<User> users);
}
