package by.makedon.selectioncommittee.databasepopulator.service;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Yahor Makedon
 */
@Slf4j
public class EnrolleeDatabasePopulatorFacadeService {
    private static final int NUMBER_OF_ENROLLEES_PER_PROCESS_THRESHOLD = 100;

    public void populateDatabaseWith(int numberOfEnrollees) {
        List<Integer> numberOfEnrolleesPerProcessList = splitNumberOfEnrolleesForProcessing(numberOfEnrollees);
        processPopulatingDatabaseWithEnrollees(numberOfEnrolleesPerProcessList);
    }

    private List<Integer> splitNumberOfEnrolleesForProcessing(int numberOfEnrollees) {
        if (numberOfEnrollees <= NUMBER_OF_ENROLLEES_PER_PROCESS_THRESHOLD) {
            return Collections.singletonList(numberOfEnrollees);
        }
        List<Integer> result = new ArrayList<>();
        do {
            result.add(NUMBER_OF_ENROLLEES_PER_PROCESS_THRESHOLD);
            numberOfEnrollees -= NUMBER_OF_ENROLLEES_PER_PROCESS_THRESHOLD;
        } while (numberOfEnrollees > NUMBER_OF_ENROLLEES_PER_PROCESS_THRESHOLD);
        result.add(numberOfEnrollees);
        return result;
    }

    private void processPopulatingDatabaseWithEnrollees(List<Integer> numberOfEnrolleesPerProcessList) {
        try (TaskService taskService = new TaskService()) {
            List<CompletableFuture<Void>> tasksInProgress = new ArrayList<>();
            for (int numberOfEnrolleesPerProcess : numberOfEnrolleesPerProcessList) {
                CompletableFuture<Void> task = taskService.runEnrolleeAndUserCreationTask(numberOfEnrolleesPerProcess);
                tasksInProgress.add(task);
                log.debug("Task-{} [runEnrolleeAndUserCreationTask] is being processed", tasksInProgress.size());
            }
            CompletableFuture.allOf(tasksInProgress.toArray(new CompletableFuture[0])).join();
            log.info("All tasks ({}) [runEnrolleeAndUserCreationTask] have been finished", tasksInProgress.size());
        }
    }
}
