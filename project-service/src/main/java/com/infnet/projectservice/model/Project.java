package com.infnet.projectservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "\"project\"")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, message = "Nome deve ter pelo menos 3 caracteres")
    private String name;

    @NotBlank(message = "Descrição é obrigatório")
    @Size(min = 5, message = "Descrição deve ter pelo menos 5 caracteres")
    private String description;

    private Integer progress;

    @NotNull(message = "Custo total é obrigatório")
    private Float totalCost;

    @NotNull(message = "Horas estimadas é obrigatório")
    @Column(name = "estimated_hours")
    private Integer estimatedHours;

    @NotNull(message = "Orçamento é obrigatório")
    private Float budget;

    private String client;

    private String clientAddress;

    @NotNull(message = "Ativo é obrigatório, insira 0 ou 1")
    private Boolean active;

    @Column(name = "created_at", updatable = false)
    public LocalDateTime createdAt;

    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

    @PrePersist
    void createdAt() {
        this.createdAt = this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = LocalDateTime.now();
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Task> tasks = new ArrayList<>();

    @Column(name = "related_projects")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RelatedProject> relatedProjects = new ArrayList<>();

    @Builder
    public Project(String name, String description, Integer progress, Float totalCost, Integer estimatedHours, Float budget, String client, String clientAddress, Boolean active) {
        this.name = name;
        this.description = description;
        this.progress = progress;
        this.totalCost = totalCost;
        this.estimatedHours = estimatedHours;
        this.budget = budget;
        this.client = client;
        this.clientAddress = clientAddress;
        this.active = active;
    }
}
