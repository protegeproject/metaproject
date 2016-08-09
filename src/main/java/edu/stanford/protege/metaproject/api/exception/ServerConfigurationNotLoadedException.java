package edu.stanford.protege.metaproject.api.exception;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
public class ServerConfigurationNotLoadedException extends ConfigurationException {
    private static final long serialVersionUID = 7837200857652435514L;

    public ServerConfigurationNotLoadedException() {
        super();
    }

    public ServerConfigurationNotLoadedException(String message) {
        super(message);
    }

    public ServerConfigurationNotLoadedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerConfigurationNotLoadedException(Throwable cause) {
        super(cause);
    }

    protected ServerConfigurationNotLoadedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
