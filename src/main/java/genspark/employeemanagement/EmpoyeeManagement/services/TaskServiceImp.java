package genspark.employeemanagement.EmpoyeeManagement.services;

import genspark.employeemanagement.EmpoyeeManagement.models.Task;
import genspark.employeemanagement.EmpoyeeManagement.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TaskServiceImp implements TaskService {
    @Autowired
    TaskRepository taskRepo;

    @Override
    public Task createTask(Task task) {
        return this.taskRepo.save(task);
    }

    @Override
    public List<Task> getAllTasks() {
        return this.taskRepo.findAll();
    }

    @Override
    public Optional<Task> getTaskById(Long id) {
        return this.taskRepo.findById(id);
    }

    @Override
    public String deleteTask(Long id) {
        return "Task deleted";
    }

    @Override
    public List<Task> getAllApprovedTasks() {
        return this.taskRepo.findByApprovedTrue();
    }

    @Override
    public List<Task> getAllApprovedFalse() {
        return this.taskRepo.findByApprovedFalse();
    }

    @Override
    public void approveTask(Long id) {

    }
}
