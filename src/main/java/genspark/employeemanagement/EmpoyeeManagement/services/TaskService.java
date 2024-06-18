package genspark.employeemanagement.EmpoyeeManagement.services;

import genspark.employeemanagement.EmpoyeeManagement.models.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task createTask(Task task);

    List<Task> getAllTasks();

    Optional<Task> getTaskById(Long id);

    String deleteTask(Long id);

    List<Task> getAllApprovedTasks();

    List<Task> getAllApprovedFalse();

    void approveTask(Long id);
}
