package com.infnet.projectservice.service.impl;

import com.infnet.projectservice.model.Project;
import com.infnet.projectservice.model.RelatedProject;
import com.infnet.projectservice.model.Task;
import com.infnet.projectservice.repository.ProjectRepository;
import com.infnet.projectservice.repository.RelatedProjectRepository;
import com.infnet.projectservice.service.ProjectService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final RelatedProjectRepository relatedProjectRepository;

    @Override
    public List<Project> findAll() throws Exception {
        log.info("Buscando projetos...");
        List<Project> projects = projectRepository.findAll();

        if (projects.isEmpty()) {
            log.error("Erro: Nenhum projeto encontrado");
            throw new Exception("Erro: Não há projetos cadastrados.");
        }

        log.info("Encontrado {} projetos.", projects.size());

        return projects;
    }

    @Override
    public Optional<Project> findById(Long id) throws Exception {
        log.info("Buscando projeto...");
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new Exception("Erro: Projeto com ID: " + id + " não encontrado."));

        log.info("Projeto encontrado com sucesso.");

        return Optional.of(existingProject);
    }

    @Override
    public List<Project> findByName(String name) throws Exception {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Erro: Insira um nome válido.");
        }
        log.info("Buscando projetos...");

        List<Project> projects = projectRepository.findByName(name);

        if (projects.isEmpty()) {
            log.error("Nenhum projeto encontrada");
            throw new Exception("Erro: Não há projetos cadastrados com esse nome.");
        }

        log.info("Encontrado {} projetos.", projects.size());

        return projects;
    }

    @Override
    public Project create(Project project) throws Exception {
        log.info("Criando projeto...");

        if (project.getName() == null || project.getName().isEmpty()) {
            throw new IllegalArgumentException("Erro: Nome inválido.");
        }

        log.info("Projeto criado com sucesso.");

        return projectRepository.save(project);
    }

    @Override
    public Optional<Project> update(Long id, Project project) throws Exception {
        Optional<Project> existingProjectOpt = projectRepository.findById(id);
        if (existingProjectOpt.isEmpty()) {
            throw new Exception("Erro: Projeto não encontrado, tente outro ID.");
        }
        Project existingProject = existingProjectOpt.get();

        if (project.getName() != null) {
            existingProject.setName(project.getName());
        }
        if (project.getDescription() != null) {
            existingProject.setDescription(project.getDescription());
        }
        if (project.getProgress() != null) {
            existingProject.setProgress(project.getProgress());
        }
        if (project.getTotalCost() != null) {
            existingProject.setTotalCost(project.getTotalCost());
        }
        if (project.getEstimatedHours() != null) {
            existingProject.setEstimatedHours(project.getEstimatedHours());
        }
        if (project.getBudget() != null) {
            existingProject.setBudget(project.getBudget());
        }
        if (project.getClient() != null) {
            existingProject.setClient(project.getClient());
        }
        if (project.getClientAddress() != null) {
            existingProject.setClientAddress(project.getClientAddress());
        }
        if (project.getActive() != null) {
            existingProject.setActive(project.getActive());
        }

        project.setId(id);
        projectRepository.save(existingProject);

        return Optional.of(existingProject);
    }

    @Override
    public Optional<Project> delete(Long id) throws Exception {
        log.info("Excluindo projeto...");

        Optional<Project> deletedProject = projectRepository.findById(id);

        if (deletedProject.isEmpty()) {
            log.error("Projeto com ID " + id + " não encontrado.");
            throw new Exception("Erro: Não foi possível encontrar projeto com ID: "+ id);
        }

        projectRepository.deleteById(id);
        log.info("Projeto excluído com sucesso.");

        return deletedProject;
    }

    @Override
    @Transactional
    public Optional<Project> addRelatedProject(Long id, Long relatedProjectId) throws Exception {
        if (Objects.equals(id, relatedProjectId)) {
            throw new IllegalArgumentException("Erro: Um projeto não pode ser relacionado a ele mesmo. Por favor, escolha um projeto diferente.");
        }

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new Exception("Erro: Não foi possível encontrar projeto com ID: " + id));
        Project relProject = projectRepository.findById(relatedProjectId)
                .orElseThrow(() -> new Exception("Erro: Não foi possível encontrar projeto para relacionar com o ID: " + relatedProjectId));

        RelatedProject relatedProject = new RelatedProject(relatedProjectId, relProject.getName());
        relatedProjectRepository.save(relatedProject);

        project.getRelatedProjects().add(relatedProject);
        projectRepository.save(project);

        return projectRepository.findById(id);
    }

    @Override
    public void addTask(Long id, Task task) throws Exception {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Erro: Não foi possível encontrar projeto com ID: " + id));

        if (project.getTasks().stream().noneMatch(a -> a.getId().equals(task.getId()))) {
            project.getTasks().add(task);
            projectRepository.save(project);
            log.info("Tarefa " + task.getName() + " adicionado ao projeto com ID: " + id + "]");
        } else {
            log.info("Tarefa com ID: " + task.getId() + " já está relacionada ao projeto." + id);
        }
    }
}
