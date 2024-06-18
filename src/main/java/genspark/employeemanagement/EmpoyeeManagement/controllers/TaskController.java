package genspark.employeemanagement.EmpoyeeManagement.controllers;

import genspark.employeemanagement.EmpoyeeManagement.models.Task;
import genspark.employeemanagement.EmpoyeeManagement.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/task")
public class TaskController {
    @Autowired
    TaskService TaskServ;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('NORMAL') or hasAuthority('ADMIN')")
    public ResponseEntity<Object> createTask(@RequestBody Task Task) {
        Task addedTask = TaskServ.createTask(Task);
        return ResponseEntity.ok(addedTask);
    }

    @GetMapping("/approved")
    @PreAuthorize("hasAuthority('NORMAL') or hasAuthority('ADMIN')")
    public ResponseEntity<Object> getAllApproved() {
        List<Task> approvedTasks = TaskServ.getAllApprovedTasks();
        return ResponseEntity.ok(approvedTasks);
    }

    @GetMapping("/unapproved")
    @PreAuthorize(" hasAuthority('ADMIN')")
    public ResponseEntity<Object> getAllUnapproved() {
        List<Task> unapprovedTasks = TaskServ.getAllApprovedFalse();
        return ResponseEntity.ok(unapprovedTasks);
    }


    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> getAllTasks() {
        return ResponseEntity.ok(TaskServ.getAllTasks());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> getTaskByID(@PathVariable Long id) {
        return ResponseEntity.ok(TaskServ.getTaskById(id));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> deleteTaskByID(@PathVariable Long id) {
        return ResponseEntity.ok(TaskServ.deleteTask(id));
    }

    @PostMapping("/approve/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Task> approveTask(@PathVariable Long id) {
        Optional<Task> TaskOp = TaskServ.getTaskById(id);
        System.out.println(TaskOp);
        if (TaskOp.isPresent()) {
            Task Task = TaskOp.get();
            System.out.println(Task);
            Task.setApproved(true);
            return ResponseEntity.ok(TaskServ.createTask(Task));
        }
        return null;
    }


}
