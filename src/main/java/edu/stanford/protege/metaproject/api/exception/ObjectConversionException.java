package edu.stanford.protege.metaproject.api.exception;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public class ObjectConversionException extends ConfigurationException {
    private static final long serialVersionUID = -1254493584694046996L;

    public ObjectConversionException() {
        super();
    }

    public ObjectConversionException(String message) {
        super(message);
    }

    public ObjectConversionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectConversionException(Throwable cause) {
        super(cause);
    }

    protected ObjectConversionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
