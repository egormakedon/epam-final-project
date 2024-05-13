package by.makedon.selectioncommittee.app.dao;

import by.makedon.selectioncommittee.app.entity.User;
import by.makedon.selectioncommittee.common.dao.Dao;
import by.makedon.selectioncommittee.common.dao.DaoException;
import org.jetbrains.annotations.NotNull;

public interface BaseDao extends Dao<Object, Object> {
    String FIELD_USERNAME = "username";
    String FIELD_TYPE = "type";

    String SQL_UPDATE_PASSWORD = "UPDATE user SET password=SHA1(?) WHERE username=?";
    String SQL_SELECT_USERNAME_BY_EMAIL = "SELECT username FROM user WHERE email=?";
    String SQL_SELECT_USER_ID_BY_USERNAME_PASSWORD = "SELECT user_id FROM user WHERE username=? AND password=SHA1(?)";
    String SQL_SELECT_USER_ID_BY_USERNAME = "SELECT user_id FROM user WHERE username=?";
    String SQL_SELECT_TYPE_BY_USERNAME = "SELECT type FROM user WHERE username=?";
    String SQL_SELECT_USER_ID_BY_EMAIL_USERNAME = "SELECT user_id FROM user WHERE email=? OR username=?";
    String SQL_INSERT_USER = "INSERT INTO user(email,username,password) VALUES(?,?,SHA1(?))";

    void insertUser(@NotNull User user) throws DaoException;
    boolean matchUsernameAndPassword(@NotNull String usernameValue, @NotNull String passwordValue) throws DaoException;
    boolean matchEmailAndUsername(@NotNull String emailValue, @NotNull String usernameValue) throws DaoException;
    String getTypeByUsername(@NotNull String usernameValue) throws DaoException;
    String getUsernameByEmail(@NotNull String emailValue) throws DaoException;
    void updatePasswordByUsername(@NotNull String usernameValue, @NotNull String passwordValue) throws DaoException;
    boolean matchUsername(@NotNull String usernameValue) throws DaoException;
}
