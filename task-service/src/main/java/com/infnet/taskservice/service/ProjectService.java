package com.infnet.taskservice.service;

import com.infnet.taskservice.model.Project;

public interface ProjectService {
    Project findById(Long id);
}
