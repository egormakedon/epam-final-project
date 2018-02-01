package by.makedon.selectioncommittee.logic;

import by.makedon.selectioncommittee.exception.LogicException;

import java.util.List;

public interface Logic {
    void doAction(List<String> parameters) throws LogicException;
}
