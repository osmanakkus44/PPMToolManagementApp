package com.enos.ppmtool.controllers;

import com.enos.ppmtool.model.Message;
import com.enos.ppmtool.model.Project;
import com.enos.ppmtool.services.MapValidationErrorService;
import com.enos.ppmtool.services.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    private final ProjectService projectService;
    private final MapValidationErrorService validationErrorService;

    public ProjectController(ProjectService projectService, MapValidationErrorService validationErrorService) {
        this.projectService = projectService;
        this.validationErrorService = validationErrorService;
    }

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult bindingResult) {
        ResponseEntity<?> result = validationErrorService.mapValidationCheck(bindingResult);
        if(result != null) {
            return result;
        }

        Project returnedProject = projectService.saveOrUpdateProject(project);
        return new ResponseEntity<>(returnedProject, HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId) {
        Project project = projectService.findProjectByIdentifier(projectId);
        return new ResponseEntity<>(project,HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects() {
        return projectService.findAllProjects();
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteById(@PathVariable String projectId) {
        projectService.deleteProjectByIdentifier(projectId);
        return new ResponseEntity<>(new Message("Project with ID " + projectId.toUpperCase() + " deleted succesfully."),HttpStatus.OK);
    }
}
