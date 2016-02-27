package edu.stanford.protege.metaproject.api.exception;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class OperationForChangeNotFoundException extends MetaprojectException {
    private static final long serialVersionUID = -8928067718594065835L;

    public OperationForChangeNotFoundException() {
        super();
    }

    public OperationForChangeNotFoundException(String message) {
        super(message);
    }

    public OperationForChangeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperationForChangeNotFoundException(Throwable cause) {
        super(cause);
    }

    protected OperationForChangeNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
