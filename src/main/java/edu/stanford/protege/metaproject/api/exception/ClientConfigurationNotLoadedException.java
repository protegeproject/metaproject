package edu.stanford.protege.metaproject.api.exception;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ClientConfigurationNotLoadedException extends MetaprojectException {
    private static final long serialVersionUID = 7779497156715378594L;

    public ClientConfigurationNotLoadedException() {
        super();
    }

    public ClientConfigurationNotLoadedException(String message) {
        super(message);
    }

    public ClientConfigurationNotLoadedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientConfigurationNotLoadedException(Throwable cause) {
        super(cause);
    }

    protected ClientConfigurationNotLoadedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
