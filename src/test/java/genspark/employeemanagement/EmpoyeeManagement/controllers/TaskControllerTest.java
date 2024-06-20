package genspark.employeemanagement.EmpoyeeManagement.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import genspark.employeemanagement.EmpoyeeManagement.models.Task;
import genspark.employeemanagement.EmpoyeeManagement.services.JwtService;
import genspark.employeemanagement.EmpoyeeManagement.services.TaskServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskServiceImp taskService;

    @MockBean
    private JwtService jwtService;

    @InjectMocks
    private TaskController taskController;

    private Task task1;
    private Task task2;
    private List<Task> allTasks;
    private List<Task> approvedTasks;
    private List<Task> unapprovedTasks;

    @BeforeEach
    void setUp() {
        task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Task 1");
        task1.setContent("Content 1");
        task1.setApproved(true);
        task1.setAuthor("Author 1");

        task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Task 2");
        task2.setContent("Content 2");
        task2.setApproved(false);
        task2.setAuthor("Author 2");

        allTasks = Arrays.asList(task1, task2);
        approvedTasks = Arrays.asList(task1);
        unapprovedTasks = Arrays.asList(task2);
    }

    @Test
    @WithMockUser(authorities = {"NORMAL", "ADMIN"})
    void testGetAllApprovedTasks() throws Exception {
        when(taskService.getAllApprovedTasks()).thenReturn(approvedTasks);

        mockMvc.perform(get("/task/approved")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Task 1"));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void testGetAllUnapprovedTasks() throws Exception {
        when(taskService.getAllApprovedFalse()).thenReturn(unapprovedTasks);

        mockMvc.perform(get("/task/unapproved")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Task 2"));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void testGetAllTasks() throws Exception {
        when(taskService.getAllTasks()).thenReturn(allTasks);

        mockMvc.perform(get("/task/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Task 1"))
                .andExpect(jsonPath("$[1].title").value("Task 2"));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void testGetTaskById() throws Exception {
        when(taskService.getTaskById(anyLong())).thenReturn(Optional.of(task1));

        mockMvc.perform(get("/task/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Task 1"));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void testApproveTask() throws Exception {
        task1.setApproved(true);
        when(taskService.getTaskById(anyLong())).thenReturn(Optional.of(task1));
        when(taskService.createTask(any(Task.class))).thenReturn(task1);

        mockMvc.perform(post("/task/approve/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.approved").value(true));
    }
}