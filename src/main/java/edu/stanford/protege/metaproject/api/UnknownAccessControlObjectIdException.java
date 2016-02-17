package edu.stanford.protege.metaproject.api;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class UnknownAccessControlObjectIdException extends MetaprojectException {
    private static final long serialVersionUID = -3281791412479904786L;

    public UnknownAccessControlObjectIdException() {
        super();
    }

    public UnknownAccessControlObjectIdException(String message) {
        super(message);
    }

    public UnknownAccessControlObjectIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownAccessControlObjectIdException(Throwable cause) {
        super(cause);
    }

    protected UnknownAccessControlObjectIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
