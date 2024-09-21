package com.infnet.taskservice.service.feign;

import com.infnet.taskservice.model.Project;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("PROJECT-SERVICE")
public interface ProjectClient {
    @GetMapping("/api/projects/{id}")
    Project findById(@PathVariable("id") Long id);
}
