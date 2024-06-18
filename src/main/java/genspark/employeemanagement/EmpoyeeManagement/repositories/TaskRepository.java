package genspark.employeemanagement.EmpoyeeManagement.repositories;

import genspark.employeemanagement.EmpoyeeManagement.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByApprovedTrue();
    List<Task> findByApprovedFalse();
}
