package by.makedon.final_project.logic.userlogic;

import by.makedon.final_project.entity.Enrollee;
import by.makedon.final_project.entity.EnrolleeParameter;

import java.util.Map;

public class SendFillFormLogic {
    public void doAction(Map<EnrolleeParameter, String> parameters) {
        Enrollee enrollee = new Enrollee(parameters);

    }
}
