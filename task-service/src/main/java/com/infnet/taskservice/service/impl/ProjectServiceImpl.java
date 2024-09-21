package com.infnet.taskservice.service.impl;

import com.infnet.taskservice.model.Project;
import com.infnet.taskservice.service.ProjectService;
import com.infnet.taskservice.service.feign.ProjectClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectClient projectClient;

    public Project findById(Long id) {
        return projectClient.findById(id);
    }
}
