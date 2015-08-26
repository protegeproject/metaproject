package edu.stanford.protege.metaproject.api.exception;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class AccessControlObjectNotFoundException extends MetaprojectException {
    private static final long serialVersionUID = -6412728795664734638L;

    public AccessControlObjectNotFoundException() {
        super();
    }

    public AccessControlObjectNotFoundException(String message) {
        super(message);
    }

    public AccessControlObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessControlObjectNotFoundException(Throwable cause) {
        super(cause);
    }

    protected AccessControlObjectNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
