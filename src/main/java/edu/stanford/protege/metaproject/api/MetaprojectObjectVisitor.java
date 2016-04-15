package edu.stanford.protege.metaproject.api;

import javax.annotation.Nonnull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface MetaprojectObjectVisitor {

    void visit(@Nonnull User user);

    void visit(@Nonnull Role role);

    void visit(@Nonnull Operation operation);

    void visit(@Nonnull Project project);

}
