package com.infnet.projectservice;

import com.infnet.projectservice.model.Project;
import com.infnet.projectservice.model.Task;
import com.infnet.projectservice.repository.ProjectRepository;
import com.infnet.projectservice.repository.RelatedProjectRepository;
import com.infnet.projectservice.service.impl.ProjectServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProjectServiceTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private RelatedProjectRepository relatedProjectRepository;

    @Autowired
    private ProjectServiceImpl projectService;

    @BeforeEach
    public void setUp() {
        projectRepository.deleteAll();
        relatedProjectRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve buscar um projeto pelo ID")
    public void testFindById() throws Exception {
        Project project = new Project();

        project.setName("Projeto Teste ID");
        project.setDescription("Testando");
        project.setActive(true);
        project.setProgress(50);
        project.setTotalCost(500.0f);
        project.setEstimatedHours(10);
        project.setBudget(1000.0f);
        project.setClient("Bruno");
        project.setClientAddress("Rua Faria Lima, 897");

        Project savedProject = projectService.create(project);

        Optional<Project> foundProject = projectService.findById(savedProject.getId());
        assertTrue(foundProject.isPresent());
        assertEquals("Projeto Teste ID", foundProject.get().getName());
    }

    @Test
    @DisplayName("Deve buscar um projeto pelo nome")
    void findByNameTest() throws Exception {
        Project project = new Project();

        project.setName("Projeto Teste Nome");
        project.setDescription("Testando");
        project.setActive(true);
        project.setProgress(50);
        project.setTotalCost(500.0f);
        project.setEstimatedHours(10);
        project.setBudget(1000.0f);
        project.setClient("Bruno");
        project.setClientAddress("Rua Faria Lima, 897");

        projectService.create(project);

        List<Project> projects = projectService.findByName("Projeto Teste Nome");

        assertThat(projects).isNotNull();
        assertThat(projects.get(0).getName()).isEqualTo("Projeto Teste Nome");
    }

    @Test
    @DisplayName("Deve salvar um projeto com sucesso")
    public void createTest() throws Exception {
        Project project = new Project();

        project.setName("Projeto Teste");
        project.setDescription("Testando");
        project.setActive(true);
        project.setProgress(50);
        project.setTotalCost(500.0f);
        project.setEstimatedHours(10);
        project.setBudget(1000.0f);
        project.setClient("Bruno");
        project.setClientAddress("Rua Faria Lima, 897");

        Project savedProject = projectService.create(project);

        assertThat(savedProject.getId()).isNotNull();
        assertThat(savedProject.getName()).isEqualTo("Projeto Teste");
        assertThat(savedProject.getDescription()).isEqualTo("Testando");
        assertThat(savedProject.getActive()).isTrue();
        assertThat(savedProject.getProgress()).isEqualTo(50);
        assertThat(savedProject.getTotalCost()).isEqualTo(500.0f);
        assertThat(savedProject.getEstimatedHours()).isEqualTo(10);
        assertThat(savedProject.getBudget()).isEqualTo(1000.0f);
        assertThat(savedProject.getClient()).isEqualTo("Bruno");
        assertThat(savedProject.getClientAddress()).isEqualTo("Rua Faria Lima, 897");
    }

    @Test
    @DisplayName("Deve atualizar um projeto existente")
    void updateTest() throws Exception {
        Project project = new Project();

        project.setName("Projeto Teste");
        project.setDescription("Testando");
        project.setActive(true);
        project.setProgress(50);
        project.setTotalCost(500.0f);
        project.setEstimatedHours(10);
        project.setBudget(1000.0f);
        project.setClient("Bruno");
        project.setClientAddress("Rua Faria Lima, 897");

        Project savedProject = projectService.create(project);
        savedProject.setName("Projeto Atualizado");
        savedProject.setBudget(2500.0f);

        Project updatedProject = projectService.create(savedProject);

        assertThat(updatedProject.getName()).isEqualTo("Projeto Atualizado");
        assertThat(updatedProject.getBudget()).isEqualTo(2500.0f);
    }

    @Test
    @DisplayName("Deve deletar um projeto com sucesso")
    void deleteTest() throws Exception {
        Project project = new Project();

        project.setName("Projeto Teste Delete");
        project.setDescription("Testando");
        project.setActive(true);
        project.setProgress(50);
        project.setTotalCost(500.0f);
        project.setEstimatedHours(10);
        project.setBudget(1000.0f);
        project.setClient("Bruno");
        project.setClientAddress("Rua Faria Lima, 897");

        Project savedProject = projectService.create(project);

        projectService.delete(savedProject.getId());

        try {
            projectService.findById(savedProject.getId());
            fail("Esperava uma exceção por projeto não encontrado, mas o projeto foi encontrado.");
        } catch (Exception e) {
            assertThat(e.getMessage()).isEqualTo("Erro: Projeto com ID: " + savedProject.getId() + " não encontrado.");
        }
    }

    @Test
    public void addRelatedProjectTest() throws Exception {
        /* Criando projeto */
        Project project = new Project();

        project.setName("Projeto Teste");
        project.setDescription("Testando");
        project.setActive(true);
        project.setProgress(50);
        project.setTotalCost(500.0f);
        project.setEstimatedHours(10);
        project.setBudget(1000.0f);
        project.setClient("Bruno");
        project.setClientAddress("Rua Faria Lima, 897");

        Project savedProject = projectService.create(project);
        /* Criando projeto */

        /* Criando projeto relacionado */
        Project relatedProject = new Project();

        relatedProject.setName("Projeto Teste de relacionamento");
        relatedProject.setDescription("Testando");
        relatedProject.setActive(true);
        relatedProject.setProgress(50);
        relatedProject.setTotalCost(500.0f);
        relatedProject.setEstimatedHours(10);
        relatedProject.setBudget(1000.0f);
        relatedProject.setClient("Bruno");
        relatedProject.setClientAddress("Rua Faria Lima, 897");

        Project savedRelatedProject = projectService.create(relatedProject);
        /* Criando projeto relacionado */

        Optional<Project> updatedProject = projectService.addRelatedProject(savedProject.getId(), savedRelatedProject.getId());

        assertTrue(updatedProject.isPresent());
        assertEquals(1, updatedProject.get().getRelatedProjects().size());
        assertEquals("Projeto Teste de relacionamento", updatedProject.get().getRelatedProjects().get(0).getName());
    }

    @Test
    public void addTaskTest() throws Exception {
        /* Criando projeto */
        Project project = new Project();

        project.setName("Projeto Teste");
        project.setDescription("Testando");
        project.setActive(true);
        project.setProgress(50);
        project.setTotalCost(500.0f);
        project.setEstimatedHours(10);
        project.setBudget(1000.0f);
        project.setClient("Bruno");
        project.setClientAddress("Rua Faria Lima, 897");

        Project savedProject = projectService.create(project);
        /* Criando projeto */

        /* Criando tarefa */
        Task task = new Task();

        task.setName("Tarefa Teste");
        task.setDescription("Descrição tarefa");
        task.setHourlyRate(20.0f);
        task.setBudget(2000.0f);
        task.setEstimatedHours(100);
        task.setActive(true);
        task.setProjectId(1L);
        /* Criando tarefa */

        projectService.addTask(savedProject.getId(), task);

        Optional<Project> updatedProject = projectService.findById(savedProject.getId());
        assertTrue(updatedProject.isPresent());
        assertEquals(1, updatedProject.get().getTasks().size());
        assertEquals("Tarefa Teste", updatedProject.get().getTasks().get(0).getName());
        assertEquals("Descrição tarefa", updatedProject.get().getTasks().get(0).getDescription());
        assertEquals(20.0f, updatedProject.get().getTasks().get(0).getHourlyRate());
        assertEquals(2000.0f, updatedProject.get().getTasks().get(0).getBudget());
        assertEquals(100, updatedProject.get().getTasks().get(0).getEstimatedHours());
        assertTrue(updatedProject.get().getTasks().get(0).getActive());
    }
}
