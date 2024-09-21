package com.infnet.projectservice.controller;

import com.infnet.projectservice.model.Project;
import com.infnet.projectservice.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/projects")
@Tag(name = "Projetos", description = "API para gerenciamento de projetos")
public class ProjectController {

    final ProjectService projectService;

    @Operation(summary = "Busca todos os projetos", description = "Retorna a lista de todos os projetos cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de projetos encontrada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Project.class))),
            @ApiResponse(responseCode = "400", description = "Erro na busca dos projetos", content = @Content)
    })
    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            return new ResponseEntity<>(projectService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Busca o projeto pelo ID", description = "Busca um projeto pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projeto encontrado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Project.class))),
            @ApiResponse(responseCode = "400", description = "Erro na busca do projeto", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(projectService.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Busca projetos pelo nome", description = "Busca todos os projetos que correspondem ao nome fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projetos encontrados com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Project.class))),
            @ApiResponse(responseCode = "400", description = "Erro na busca dos projetos", content = @Content)
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name) {
        try {
            return new ResponseEntity<>(projectService.findByName(name), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Cria um novo projeto", description = "Cria um novo projeto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projeto criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Project.class))),
            @ApiResponse(responseCode = "400", description = "Erro na criação do projeto", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody Project project) {
        try {
            return new ResponseEntity<>(projectService.create(project), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Atualiza um projeto existente", description = "Atualiza as informações de um projeto pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projeto atualizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Project.class))),
            @ApiResponse(responseCode = "400", description = "Erro na atualização do projeto", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Project project) {
        try {
            return new ResponseEntity<>(projectService.update(id, project), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Deleta um projeto", description = "Remove um projeto pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projeto deletado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Project.class))),
            @ApiResponse(responseCode = "400", description = "Erro ao deletar o projeto", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(projectService.delete(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Relacionar projetos", description = "Relaciona um projeto ao outro com base nos seus IDs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projeto relacionado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Project.class))),
            @ApiResponse(responseCode = "400", description = "Erro ao relacionar o projeto", content = @Content)
    })
    @PutMapping("{id}/relatedProject/{relatedProjectId}")
    public ResponseEntity<?> addRelatedProject (@PathVariable Long id, @PathVariable Long relatedProjectId) {
        try {
            return new ResponseEntity<>(projectService.addRelatedProject(id, relatedProjectId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
