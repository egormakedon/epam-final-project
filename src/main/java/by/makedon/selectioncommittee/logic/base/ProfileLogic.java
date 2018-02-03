package by.makedon.selectioncommittee.logic.base;

import by.makedon.selectioncommittee.constant.Page;
import by.makedon.selectioncommittee.exception.LogicException;
import by.makedon.selectioncommittee.logic.Logic;
import com.sun.istack.internal.NotNull;

import java.util.List;

public class ProfileLogic implements Logic {
    private static final int LIST_SIZE = 2;

    private static final String USER = "user";
    private static final String FALSE = "false";

    private String pagePath;

    @Override
    public void doAction(@NotNull List<String> parameters) throws LogicException {
        if (parameters.size() != LIST_SIZE) {
            throw new LogicException("wrong number of parameters");
        }

        String loginValue = parameters.get(0);
        String typeValue = parameters.get(1);

        if (loginValue.equals(FALSE)) {
            pagePath = Page.LOGIN;
        } else {
            if (typeValue.equals(USER)) {
                pagePath = Page.USER;
            } else {
                pagePath = Page.ADMIN;
            }
        }
    }

    public String getPagePath() {
        return pagePath;
    }
}
