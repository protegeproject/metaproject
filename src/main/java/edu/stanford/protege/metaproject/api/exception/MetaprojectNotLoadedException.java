package edu.stanford.protege.metaproject.api.exception;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class MetaprojectNotLoadedException extends MetaprojectException {
    private static final long serialVersionUID = 2872996229527184420L;

    public MetaprojectNotLoadedException() {
        super();
    }

    public MetaprojectNotLoadedException(String message) {
        super(message);
    }

    public MetaprojectNotLoadedException(String message, Throwable cause) {
        super(message, cause);
    }

    public MetaprojectNotLoadedException(Throwable cause) {
        super(cause);
    }

    protected MetaprojectNotLoadedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
