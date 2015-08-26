package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.AccessControlObjectNotFoundException;
import edu.stanford.protege.metaproject.api.exception.ProjectNotFoundException;

import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface ProjectManager extends Manager {

    void addProject(Project... project);

    void removeProject(Project... project) throws AccessControlObjectNotFoundException;

    Set<Project> getProjects();

    Project getProject(ProjectId projectId) throws ProjectNotFoundException;

    Set<Project> getProjects(Name projectName);

    void changeProjectName(ProjectId projectId, Name projectName) throws ProjectNotFoundException;

    void changeProjectDescription(ProjectId projectId, Description projectDescription) throws ProjectNotFoundException;

    void changeOwner(ProjectId projectId, UserId userId) throws ProjectNotFoundException;

    void changeLocation(ProjectId projectId, Address projectAddress) throws ProjectNotFoundException;

    void addAdministrator(ProjectId projectId, UserId userId) throws ProjectNotFoundException;

    void addAdministrators(ProjectId projectId, Set<UserId> users) throws ProjectNotFoundException;

    void removeAdministrator(ProjectId projectId, UserId userId) throws ProjectNotFoundException;

    void removeAdministrators(ProjectId projectId, Set<UserId> users) throws ProjectNotFoundException;

}
