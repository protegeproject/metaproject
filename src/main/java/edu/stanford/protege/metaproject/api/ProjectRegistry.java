package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.api.exception.UnknownMetaprojectObjectIdException;

import java.io.File;
import java.util.Set;

/**
 * A manager for accessing, adding, removing or editing existing projects.
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface ProjectRegistry extends Registry<Project> {

    /**
     * Get the set of projects with the specified project name
     *
     * @param projectName   Project name
     * @return Set of projects
     */
    Set<Project> getEntries(Name projectName);

    /**
     * Change the name of the given project
     *
     * @param projectId   Project identifier
     * @param projectName   New project name
     * @throws UnknownMetaprojectObjectIdException    Project identifier is not recognized
     */
    void setName(ProjectId projectId, Name projectName) throws UnknownMetaprojectObjectIdException;

    /**
     * Change the description of the given project
     *
     * @param projectId   Project identifier
     * @param projectDescription    New project description
     * @throws UnknownMetaprojectObjectIdException    Project identifier is not recognized
     */
    void setDescription(ProjectId projectId, Description projectDescription) throws UnknownMetaprojectObjectIdException;

    /**
     * Change the owner of the specified project
     *
     * @param projectId   Project identifier
     * @param userId    New owner user identifier
     * @throws UnknownMetaprojectObjectIdException    Project identifier is not recognized
     */
    void setOwner(ProjectId projectId, UserId userId) throws UnknownMetaprojectObjectIdException;

    /**
     * Change the file location of the specified project
     *
     * @param projectId Project identifier
     * @param file   Project file
     * @throws UnknownMetaprojectObjectIdException    Project identifier is not recognized
     */
    void setFile(ProjectId projectId, File file) throws UnknownMetaprojectObjectIdException;

    /**
     * Set the options for this project
     *
     * @param projectId Project identifier
     * @param projectOptions    Project options
     * @throws UnknownMetaprojectObjectIdException    Project identifier is not recognized
     */
    void setOptions(ProjectId projectId, ProjectOptions projectOptions) throws UnknownMetaprojectObjectIdException;

}
