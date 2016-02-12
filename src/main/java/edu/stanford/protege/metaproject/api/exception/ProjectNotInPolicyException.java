package edu.stanford.protege.metaproject.api.exception;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ProjectNotInPolicyException extends PolicyException {
    private static final long serialVersionUID = 8242604398315348850L;

    public ProjectNotInPolicyException() {
        super();
    }

    public ProjectNotInPolicyException(String message) {
        super(message);
    }

    public ProjectNotInPolicyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProjectNotInPolicyException(Throwable cause) {
        super(cause);
    }

    protected ProjectNotInPolicyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
