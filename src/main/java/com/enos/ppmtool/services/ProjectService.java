package com.enos.ppmtool.services;

import com.enos.ppmtool.domain.Project;
import com.enos.ppmtool.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project saveOrUpdateProject(Project project) {
        // There can be a lot of logic
        return projectRepository.save(project);
    }
}
