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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "\"task\"")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, message = "Nome deve ter pelo menos 3 caracteres")
    private String name;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(min = 5, message = "Nome deve ter pelo menos 5 caracteres")
    private String description;

    private String observation;

    @NotNull(message = "Taxa horária é obrigatória")
    @Column(name = "hourly_rate")
    private Float hourlyRate;

    @NotNull(message = "Orçamento é obrigatório")
    private Float budget;

    @NotNull(message = "Horas estimadas é obrigatório")
    @Column(name = "estimated_hours")
    private Integer estimatedHours;

    @NotNull(message = "Ativo é obrigatório, insira 0 ou 1")
    private Boolean active;

    @NotNull(message = "ID do projeto é obrigatório")
    @Column(name = "project_id")
    private Long projectId;

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
}
