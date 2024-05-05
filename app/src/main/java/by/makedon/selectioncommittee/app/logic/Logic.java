package by.makedon.selectioncommittee.app.logic;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Logic {
    void doAction(@NotNull List<String> parameters) throws LogicException;
}
