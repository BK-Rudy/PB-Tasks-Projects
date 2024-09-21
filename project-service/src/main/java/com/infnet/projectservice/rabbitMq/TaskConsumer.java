package com.infnet.projectservice.rabbitMq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infnet.projectservice.model.Project;
import com.infnet.projectservice.model.RelatedProject;
import com.infnet.projectservice.model.Task;
import com.infnet.projectservice.service.ProjectService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
public class TaskConsumer {
    private final ProjectService projectService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = { "task-queue" })
    @Transactional
    public void receive(@Payload String message) throws Exception {
        try {
            log.info("Receptando mensagem da tarefa...");

            Task task = objectMapper.readValue(message, Task.class);
            Long actProjectId = task.getProjectId();

            Project actProject = projectService.findById(actProjectId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found: " + actProjectId));

            List<RelatedProject> relatedProjects = actProject.getRelatedProjects();
            List<Project> projects = projectService.findAll();

            for (Project project : projects) {
                boolean haveAsRelatedProject = project.getRelatedProjects().stream().anyMatch(f -> f.getProjectId().equals(actProjectId));
                if (haveAsRelatedProject) {
                    projectService.addTask(project.getId(), task);
                }
            }

            log.info("Mensagem recebida com sucesso.");
        } catch (Exception e) {
            log.error("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
