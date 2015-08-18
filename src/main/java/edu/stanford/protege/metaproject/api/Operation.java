package edu.stanford.protege.metaproject.api;

import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface Operation extends AccessControlObject, HasName, HasDescription {

    OperationId getId();

    Set<OperationPrerequisite> getPrerequisites();

}
