package com.enos.ppmtool.services;

import com.enos.ppmtool.model.Project;
import com.enos.ppmtool.exceptions.ProjectIdException;
import com.enos.ppmtool.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project saveOrUpdateProject(Project project) {

        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdException("Project Id '" + project.getProjectIdentifier().toUpperCase() + "' already exists.");
        }
    }

    public Project findProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if (project == null) {
            throw new ProjectIdException("Project Id '" + projectId + "' does not exist.");
        }

        return project;
    }

    public Iterable<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String identifier) {
        Project project = findProjectByIdentifier(identifier.toUpperCase());
        if (project == null) {
            throw new ProjectIdException("Project Id '" + identifier + "' does not exist." + "Can't delete project");
        }
        projectRepository.delete(project);
    }
}
