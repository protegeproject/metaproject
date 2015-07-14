package edu.stanford.protege.metaproject.api;

/**
 * A representation of a commit operation, which involves a message, a change set, and a target project to apply the changes to
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface Commit {

    /**
     * Get the commit message
     *
     * @return Commit message
     */
    CommitMessage getMessage();

    /**
     * Get the changes involved in this commit
     *
     * @return Change set instance
     */
    ChangeSet getChanges();

    /**
     * Get the identifier of the project the commit will be applied to
     *
     * @return Project identifier
     */
    ProjectId getTargetProject();

}
