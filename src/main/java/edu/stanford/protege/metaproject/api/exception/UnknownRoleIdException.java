package edu.stanford.protege.metaproject.api.exception;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class UnknownRoleIdException extends UnknownAccessControlObjectIdException {
    private static final long serialVersionUID = -965133698079338400L;

    public UnknownRoleIdException() {
        super();
    }

    public UnknownRoleIdException(String message) {
        super(message);
    }

    public UnknownRoleIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownRoleIdException(Throwable cause) {
        super(cause);
    }

    protected UnknownRoleIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
