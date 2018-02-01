package by.makedon.selectioncommittee.logic;

import by.makedon.selectioncommittee.exception.LogicException;
import com.sun.istack.internal.NotNull;

import java.util.List;

public interface Logic {
    void doAction(@NotNull List<String> parameters) throws LogicException;
}
