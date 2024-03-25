package by.makedon.selectioncommittee.databasepopulator.service;

import by.makedon.selectioncommittee.databasepopulator.dao.EnrolleeDao;
import by.makedon.selectioncommittee.databasepopulator.dao.UserDao;
import by.makedon.selectioncommittee.databasepopulator.dao.impl.EnrolleeDaoImpl;
import by.makedon.selectioncommittee.databasepopulator.dao.impl.UserDaoImpl;
import by.makedon.selectioncommittee.databasepopulator.entity.Enrollee;
import by.makedon.selectioncommittee.databasepopulator.entity.User;
import by.makedon.selectioncommittee.databasepopulator.entity.factory.EnrolleeFactory;
import by.makedon.selectioncommittee.databasepopulator.entity.factory.UserFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Yahor Makedon
 */
@Slf4j
public class TaskService implements AutoCloseable {
    private static final int THREAD_POOL_SIZE = 5;

    private final ExecutorService executorService;
    private final EnrolleeDao enrolleeDao;
    private final UserDao userDao;

    public TaskService() {
        executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        enrolleeDao = new EnrolleeDaoImpl();
        userDao = new UserDaoImpl();
        log.debug("TaskService[{}] has been initialized", this);
    }

    public CompletableFuture<Void> runEnrolleeAndUserCreationTask(int numberOfEnrollees) {
        return CompletableFuture
            .supplyAsync(createEnrolleesSupplier(numberOfEnrollees), executorService)
            .thenComposeAsync(enrollees -> CompletableFuture.supplyAsync(saveEnrolleesSupplier(enrollees), executorService), executorService)
            .thenComposeAsync(enrollees -> CompletableFuture.supplyAsync(createUsersSupplier(enrollees), executorService), executorService)
            .thenComposeAsync(users -> CompletableFuture.runAsync(saveUsersRunnable(users), executorService), executorService)
            .exceptionally(e -> {
                log.error(e.getMessage(), e);
                return null;
            });
    }

    private Supplier<List<Enrollee>> createEnrolleesSupplier(int numberOfEnrollees) {
        return () -> IntStream.range(0, numberOfEnrollees)
            .mapToObj(i -> EnrolleeFactory.getRandomEnrollee())
            .collect(Collectors.toList());
    }

    private Supplier<List<Enrollee>> saveEnrolleesSupplier(List<Enrollee> enrollees) {
        return () -> {
            enrolleeDao.batchCreate(enrollees);
            return enrollees;
        };
    }

    private Supplier<List<User>> createUsersSupplier(List<Enrollee> enrollees) {
        return () -> enrollees.stream().map(UserFactory::getUserBy).collect(Collectors.toList());
    }

    private Runnable saveUsersRunnable(List<User> users) {
        return () -> userDao.batchCreate(users);
    }

    @Override
    public void close() {
        executorService.shutdown();
        log.debug("TaskService[{}] has been closed", this);
    }
}
