package com.infnet.taskservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infnet.taskservice.model.Task;
import com.infnet.taskservice.rabbitMq.TaskProducer;
import com.infnet.taskservice.repository.TaskRepository;
import com.infnet.taskservice.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskProducer taskProducer;

    @Override
    public List<Task> findAll() throws Exception {
        log.info("Buscando tarefas...");
        List<Task> tasks = taskRepository.findAll();

        if (tasks.isEmpty()) {
            log.error("Erro: Nenhuma tarefa encontrada");
            throw new Exception("Erro: Não há tarefas cadastradas.");
        }

        log.info("Encontrado {} tarefas.", tasks.size());

        return tasks;
    }

    @Override
    public Optional<Task> findById(Long id) throws Exception {
        log.info("Buscando tarefa...");
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new Exception("Erro: Tarefa com ID: " + id + " não encontrada."));

        log.info("Tarefa encontrada com sucesso.");

        return Optional.of(task);
    }

    @Override
    public List<Task> findByName(String name) throws Exception {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Erro: Insira um nome válido.");
        }
        log.info("Buscando tarefas...");

        List<Task> tasks = taskRepository.findByName(name);

        if (tasks.isEmpty()) {
            log.error("Erro: Nenhuma tarefa encontrada");
            throw new Exception("Erro: Não há tarefas cadastradas com esse nome.");
        }

        log.info("Encontrado {} tarefas.", tasks.size());

        return tasks;
    }

    @Override
    public Task create(Task task) throws JsonProcessingException {
        log.info("Criando tarefa...");

        Task taskSaved = taskRepository.save(task);
        taskProducer.send(taskSaved);

        log.info("Tarefa criada com sucesso.");

        return taskSaved;
    }

    @Override
    public Optional<Task> update(Long id, Task task) throws Exception {
        log.info("Atualizando tarefa...");

        Optional<Task> existingTaskOpt = taskRepository.findById(id);
        if (existingTaskOpt.isEmpty()) {
            throw new Exception("Erro: Tarefa não encontrada, tente outro ID.");
        }
        Task existingTask = existingTaskOpt.get();

        if (task.getName() != null) {
            existingTask.setName(task.getName());
        }
        if (task.getDescription() != null) {
            existingTask.setDescription(task.getDescription());
        }
        if (task.getObservation() != null) {
            existingTask.setObservation(task.getObservation());
        }
        if (task.getHourlyRate() != null) {
            existingTask.setHourlyRate(task.getHourlyRate());
        }
        if (task.getBudget() != null) {
            existingTask.setBudget(task.getBudget());
        }
        if (task.getEstimatedHours() != null) {
            existingTask.setEstimatedHours(task.getEstimatedHours());
        }
        if (task.getActive() != null) {
            existingTask.setActive(task.getActive());
        }

        taskRepository.save(existingTask);
        log.info("Tarefa atualizada com sucesso.");

        return Optional.of(existingTask);
    }

    @Override
    public Optional<Task> delete(Long id) throws Exception {
        log.info("Excluindo tarefa...");

        Optional<Task> deletedTask = taskRepository.findById(id);

        if (deletedTask.isEmpty()) {
            log.error("Erro: Tarefa com ID: {} não encontrada.", id);
            throw new Exception("Erro: Não foi possível encontrar tarefa com ID: "+ id);
        }

        taskRepository.deleteById(id);
        log.info("Tarefa excluída com sucesso.");

        return deletedTask;
    }
}
