package edu.stanford.protege.metaproject.api.exception;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class UserNotRegisteredException extends MetaprojectException {
    private static final long serialVersionUID = -5990668314311315326L;

    public UserNotRegisteredException() {
        super();
    }

    public UserNotRegisteredException(String message) {
        super(message);
    }

    public UserNotRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotRegisteredException(Throwable cause) {
        super(cause);
    }

    protected UserNotRegisteredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
