package edu.stanford.protege.metaproject.api.exception;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class EmailAddressAlreadyInUseException extends MetaprojectException {
    private static final long serialVersionUID = -8717161864473248393L;

    public EmailAddressAlreadyInUseException() {
        super();
    }

    public EmailAddressAlreadyInUseException(String message) {
        super(message);
    }

    public EmailAddressAlreadyInUseException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailAddressAlreadyInUseException(Throwable cause) {
        super(cause);
    }

    protected EmailAddressAlreadyInUseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
