package edu.stanford.protege.metaproject.api;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class UnknownAccessControlObjectTypeException extends MetaprojectException {
    private static final long serialVersionUID = 1910017836918295783L;

    public UnknownAccessControlObjectTypeException() {
        super();
    }

    public UnknownAccessControlObjectTypeException(String message) {
        super(message);
    }

    public UnknownAccessControlObjectTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownAccessControlObjectTypeException(Throwable cause) {
        super(cause);
    }

    protected UnknownAccessControlObjectTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
