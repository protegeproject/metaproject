package edu.stanford.protege.metaproject.api.exception;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class PolicyException extends MetaprojectException {
    private static final long serialVersionUID = -8412362008816335161L;

    public PolicyException() {
        super();
    }

    public PolicyException(String message) {
        super(message);
    }

    public PolicyException(String message, Throwable cause) {
        super(message, cause);
    }

    public PolicyException(Throwable cause) {
        super(cause);
    }

    protected PolicyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
