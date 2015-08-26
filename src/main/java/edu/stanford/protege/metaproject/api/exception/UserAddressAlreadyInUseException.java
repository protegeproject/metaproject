package edu.stanford.protege.metaproject.api.exception;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class UserAddressAlreadyInUseException extends MetaprojectException {
    private static final long serialVersionUID = 2796482484224208149L;

    public UserAddressAlreadyInUseException() {
        super();
    }

    public UserAddressAlreadyInUseException(String message) {
        super(message);
    }

    public UserAddressAlreadyInUseException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAddressAlreadyInUseException(Throwable cause) {
        super(cause);
    }

    protected UserAddressAlreadyInUseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
