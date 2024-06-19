package genspark.employeemanagement.EmpoyeeManagement;

import genspark.employeemanagement.EmpoyeeManagement.controllers.TaskController;
import genspark.employeemanagement.EmpoyeeManagement.models.Task;
import genspark.employeemanagement.EmpoyeeManagement.services.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class EmpoyeeManagementApplicationTests {

	@Test
	public void test_task_is_successfully_created_with_valid_data() {
		// Arrange
		TaskService taskServiceMock = mock(TaskService.class);
		TaskController taskController = new TaskController();
		ReflectionTestUtils.setField(taskController, "TaskServ", taskServiceMock);

		Task task = new Task();
		task.setTitle("Test Task");
		task.setContent("This is a test task.");
		task.setAuthor("Author");

		Task createdTask = new Task();
		createdTask.setId(1L);
		createdTask.setTitle("Test Task");
		createdTask.setContent("This is a test task.");
		createdTask.setAuthor("Author");
		createdTask.setApproved(false);

		when(taskServiceMock.createTask(task)).thenReturn(createdTask);

		// Act
		ResponseEntity<Object> response = taskController.createTask(task);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(createdTask, response.getBody());
	}

	@Test
	public void test_returns_list_of_approved_tasks_when_there_are_approved_tasks_in_database() {
		TaskService taskService = mock(TaskService.class);
		TaskController taskController = new TaskController();
		ReflectionTestUtils.setField(taskController, "TaskServ", taskService);

		Task task1 = new Task();
		task1.setId(1L);
		task1.setTitle("Task 1");
		task1.setContent("Content 1");
		task1.setApproved(true);

		Task task2 = new Task();
		task2.setId(2L);
		task2.setTitle("Task 2");
		task2.setContent("Content 2");
		task2.setApproved(true);

		List<Task> approvedTasks = Arrays.asList(task1, task2);
		when(taskService.getAllApprovedTasks()).thenReturn(approvedTasks);

		ResponseEntity<Object> response = taskController.getAllApproved();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(approvedTasks, response.getBody());
	}

	@Test
	public void test_approve_existing_task_successfully() {
		// Arrange
		TaskService taskServiceMock = Mockito.mock(TaskService.class);
		TaskController taskController = new TaskController();
		ReflectionTestUtils.setField(taskController, "TaskServ", taskServiceMock);

		Task task = new Task();
		task.setId(1L);
		task.setTitle("Test Task");
		task.setContent("Test Content");
		task.setApproved(false);

		Mockito.when(taskServiceMock.getTaskById(1L)).thenReturn(Optional.of(task));
		Mockito.when(taskServiceMock.createTask(Mockito.any(Task.class))).thenReturn(task);

		// Act
		ResponseEntity<Task> response = taskController.approveTask(1L);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().isApproved());
	}

	@Test
	public void test_returns_list_of_unapproved_tasks_when_there_are_unapproved_tasks() {
		TaskService taskService = mock(TaskService.class);
		TaskController taskController = new TaskController();
		ReflectionTestUtils.setField(taskController, "TaskServ", taskService);

		List<Task> unapprovedTasks = Arrays.asList(
				new Task(1L, "Task 1", "Content 1", false, "Author 1"),
				new Task(2L, "Task 2", "Content 2", false, "Author 2")
		);

		when(taskService.getAllApprovedFalse()).thenReturn(unapprovedTasks);

		ResponseEntity<Object> response = taskController.getAllUnapproved();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(unapprovedTasks, response.getBody());
	}

}
