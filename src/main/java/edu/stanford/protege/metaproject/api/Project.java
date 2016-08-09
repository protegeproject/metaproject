package edu.stanford.protege.metaproject.api;

import java.io.File;
import java.util.Optional;

/**
 * A representation of a project, consisting of a unique (internal) identifier, a (display) name, and a
 * description. A project is owned by a single user; unless altered, the owner is the creator. A project may
 * also have some options associated with it.
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface Project extends PolicyObject<ProjectId>, HasDescription, Comparable<Project> {

    /**
     * Get the project file
     *
     * @return Project file
     */
    File getFile();

    /**
     * Get the owner user of the project
     *
     * @return Project owner user
     */
    UserId getOwner();

    /**
     * Get the options for this project
     *
     * @return Project options
     */
    Optional<ProjectOptions> getOptions();

}
