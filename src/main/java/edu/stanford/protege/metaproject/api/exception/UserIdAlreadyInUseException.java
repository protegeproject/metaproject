package edu.stanford.protege.metaproject.api.exception;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class UserIdAlreadyInUseException extends MetaprojectException {
    private static final long serialVersionUID = 4536497963997821921L;

    public UserIdAlreadyInUseException() {
        super();
    }

    public UserIdAlreadyInUseException(String message) {
        super(message);
    }

    public UserIdAlreadyInUseException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserIdAlreadyInUseException(Throwable cause) {
        super(cause);
    }

    protected UserIdAlreadyInUseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
