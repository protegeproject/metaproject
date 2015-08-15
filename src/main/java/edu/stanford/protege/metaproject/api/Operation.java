package edu.stanford.protege.metaproject.api;

import java.util.Set;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface Operation<T> extends AccessControlObject, HasDescription {

    Set<OperationPrerequisite<T>> getPrerequisites();

}
