package com.infnet.taskservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infnet.taskservice.model.Task;
import com.infnet.taskservice.repository.TaskRepository;
import com.infnet.taskservice.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TaskServiceTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskServiceImpl taskService;

    @BeforeEach
    public void setUp() {
        taskRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve buscar uma tarefa pelo ID")
    public void findByIdTest() throws Exception {
        Task task = new Task();

        task.setName("Tarefa Teste");
        task.setDescription("Descrição tarefa");
        task.setHourlyRate(20.0f);
        task.setBudget(2000.0f);
        task.setEstimatedHours(100);
        task.setActive(true);

        Task savedTask = taskService.create(task);
        Optional<Task> foundTask = taskService.findById(savedTask.getId());

        assertTrue(foundTask.isPresent());
        assertEquals(savedTask.getId(), foundTask.get().getId());
    }

    @Test
    @DisplayName("Deve buscar uma tarefa pelo nome")
    void findByNameTest() throws Exception {
        Task task = new Task();

        task.setName("Tarefa Teste");
        task.setDescription("Descrição tarefa");
        task.setHourlyRate(20.0f);
        task.setBudget(2000.0f);
        task.setEstimatedHours(100);
        task.setActive(true);

        taskService.create(task);

        List<Task> foundTasks = taskService.findByName("Tarefa Teste");

        assertThat(foundTasks).isNotNull();
        assertThat(foundTasks.get(0).getName()).isEqualTo("Tarefa Teste");
    }

    @Test
    @DisplayName("Deve salvar uma tarefa com sucesso")
    void createTest() throws JsonProcessingException {
        Task task = new Task();

        task.setName("Tarefa Teste");
        task.setDescription("Descrição tarefa");
        task.setHourlyRate(20.0f);
        task.setBudget(2000.0f);
        task.setEstimatedHours(100);
        task.setActive(true);

        Task savedTask = taskService.create(task);

        assertThat(savedTask.getId()).isNotNull();
        assertThat(savedTask.getName()).isEqualTo("Tarefa Teste");
        assertThat(savedTask.getDescription()).isEqualTo("Descrição tarefa");
        assertThat(savedTask.getHourlyRate()).isEqualTo(20.0f);
        assertThat(savedTask.getBudget()).isEqualTo(2000.0f);
        assertThat(savedTask.getEstimatedHours()).isEqualTo(100);
        assertThat(savedTask.getActive()).isEqualTo(true);
    }

    @Test
    @DisplayName("Deve atualizar uma tarefa existente")
    void updateTest() throws Exception {
        Task task = new Task();

        task.setName("Tarefa Teste");
        task.setDescription("Descrição tarefa");
        task.setHourlyRate(20.0f);
        task.setBudget(2000.0f);
        task.setEstimatedHours(100);
        task.setActive(true);

        Task savedTask = taskService.create(task);
        savedTask.setName("Tarefa Atualizada");
        savedTask.setBudget(2500.0f);

        Optional<Task> updatedTaskOpt = taskService.update(savedTask.getId(), savedTask);

        assertThat(updatedTaskOpt.isPresent()).isTrue();

        Task updatedTask = updatedTaskOpt.get();

        assertThat(updatedTask.getName()).isEqualTo("Tarefa Atualizada");
        assertThat(updatedTask.getBudget()).isEqualTo(2500.0f);
    }

    @Test
    @DisplayName("Deve deletar uma tarefa com sucesso")
    void deleteTest() {
        Task task = Task.builder()
                .name("Tarefa Teste")
                .description("Testando")
                .observation("Obs")
                .hourlyRate(100.0f)
                .budget(1000.0f)
                .estimatedHours(10)
                .active(true)
                .build();

        Task savedTask = taskRepository.save(task);

        taskRepository.deleteById(savedTask.getId());

        Optional<Task> deletedTask = taskRepository.findById(savedTask.getId());

        assertThat(deletedTask).isEmpty();
    }
}
