package genspark.employeemanagement.EmpoyeeManagement.services;

import genspark.employeemanagement.EmpoyeeManagement.models.Task;
import genspark.employeemanagement.EmpoyeeManagement.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceImpTest {

    @Mock
    private TaskRepository taskRepo;

    @InjectMocks
    private TaskServiceImp taskService;

    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
        task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Task 1");
        task1.setContent("Description 1");
        task1.setApproved(true);
        task1.setAuthor("Author 1");

        task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Task 2");
        task2.setContent("Description 2");
        task2.setApproved(false);
        task2.setAuthor("Author 2");
    }

    @Test
    void testCreateTask() {
        when(taskRepo.save(any(Task.class))).thenReturn(task1);

        Task createdTask = taskService.createTask(task1);

        assertNotNull(createdTask);
        assertEquals("Task 1", createdTask.getTitle());
        assertEquals("Description 1", createdTask.getContent());
        assertEquals("Author 1", createdTask.getAuthor());
        assertTrue(createdTask.isApproved());
    }

    @Test
    void testGetAllTasks() {
        when(taskRepo.findAll()).thenReturn(Arrays.asList(task1, task2));

        List<Task> tasks = taskService.getAllTasks();

        assertNotNull(tasks);
        assertEquals(2, tasks.size());
        assertEquals("Task 1", tasks.get(0).getTitle());
        assertEquals("Task 2", tasks.get(1).getTitle());
    }

    @Test
    void testGetTaskById() {
        when(taskRepo.findById(1L)).thenReturn(Optional.of(task1));

        Optional<Task> foundTask = taskService.getTaskById(1L);

        assertTrue(foundTask.isPresent());
        assertEquals("Task 1", foundTask.get().getTitle());
        assertEquals("Description 1", foundTask.get().getContent());
        assertEquals("Author 1", foundTask.get().getAuthor());
        assertTrue(foundTask.get().isApproved());
    }

    @Test
    void testDeleteTask() {
        String response = taskService.deleteTask(1L);

        assertEquals("Task deleted", response);
    }

    @Test
    void testGetAllApprovedTasks() {
        when(taskRepo.findByApprovedTrue()).thenReturn(Arrays.asList(task1));

        List<Task> approvedTasks = taskService.getAllApprovedTasks();

        assertNotNull(approvedTasks);
        assertEquals(1, approvedTasks.size());
        assertEquals("Task 1", approvedTasks.get(0).getTitle());
        assertTrue(approvedTasks.get(0).isApproved());
    }

    @Test
    void testGetAllApprovedFalse() {
        when(taskRepo.findByApprovedFalse()).thenReturn(Arrays.asList(task2));

        List<Task> unapprovedTasks = taskService.getAllApprovedFalse();

        assertNotNull(unapprovedTasks);
        assertEquals(1, unapprovedTasks.size());
        assertEquals("Task 2", unapprovedTasks.get(0).getTitle());
        assertFalse(unapprovedTasks.get(0).isApproved());
    }
}