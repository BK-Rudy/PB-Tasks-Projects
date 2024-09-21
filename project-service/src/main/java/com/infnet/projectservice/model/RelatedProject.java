package com.infnet.projectservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "\"related_project\"")
public class RelatedProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "ID do projeto é obrigatório")
    @Column(name = "project_id")
    private Long projectId;

    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @Builder
    public RelatedProject(Long projectId, String name) {
        this.projectId = projectId;
        this.name = name;
    }
}
