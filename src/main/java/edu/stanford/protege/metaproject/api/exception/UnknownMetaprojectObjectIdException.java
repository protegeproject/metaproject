package edu.stanford.protege.metaproject.api.exception;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class UnknownMetaprojectObjectIdException extends MetaprojectException {
    private static final long serialVersionUID = -3371031224832939317L;

    public UnknownMetaprojectObjectIdException() {
        super();
    }

    public UnknownMetaprojectObjectIdException(String message) {
        super(message);
    }

    public UnknownMetaprojectObjectIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownMetaprojectObjectIdException(Throwable cause) {
        super(cause);
    }

    protected UnknownMetaprojectObjectIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
