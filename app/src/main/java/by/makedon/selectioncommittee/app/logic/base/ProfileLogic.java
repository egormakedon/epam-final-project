package by.makedon.selectioncommittee.app.logic.base;

import by.makedon.selectioncommittee.app.configuration.util.Page;
import by.makedon.selectioncommittee.app.entity.UserType;
import by.makedon.selectioncommittee.app.logic.Logic;
import by.makedon.selectioncommittee.app.logic.LogicException;
import by.makedon.selectioncommittee.app.validator.ValidationException;
import by.makedon.selectioncommittee.common.dao.DaoException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ProfileLogic implements Logic {
    private static final int VALID_PARAMETERS_SIZE = 2;

    private final Map<UserType, String> userTypeToPagePathMap;

    {
        userTypeToPagePathMap = new HashMap<>();
        userTypeToPagePathMap.put(UserType.ADMIN, Page.ADMIN);
        userTypeToPagePathMap.put(UserType.USER, Page.USER);
    }

    @Getter
    private String pagePath = "";

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
        pagePath = "";

        String isLoggedIn = parameters.get(0);
        String type = parameters.get(1);

        if (Boolean.TRUE.toString().equals(isLoggedIn)) {
            UserType userType = UserType.of(type);
            pagePath = userTypeToPagePathMap.getOrDefault(userType, "");
            if (pagePath.isEmpty()) {
                log.warn(String.format("Unknown userType=`%s`", userType));
            }
        } else if (Boolean.FALSE.toString().equals(isLoggedIn)) {
            pagePath = Page.LOGIN;
        } else {
            log.warn("Incorrect login parameter: `{}`", isLoggedIn);
        }
    }
}
