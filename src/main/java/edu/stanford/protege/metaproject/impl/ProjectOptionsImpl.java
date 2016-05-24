package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.ProjectOptions;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class ProjectOptionsImpl implements ProjectOptions, Serializable {
    private static final long serialVersionUID = -4019008663618078530L;
    private final Map<String,Set<String>> options;

    /**
     * Constructor
     *
     * @param options   Project options map
     */
    public ProjectOptionsImpl(Map<String,Set<String>> options) {
        this.options = checkNotNull(options);
    }

    @Override
    public Map<String,Set<String>> getOptions() {
        return options;
    }

    @Override
    public Set<String> getOption(String key) {
        return options.get(key);
    }

    @Override
    public String getSingletonOption(String key) {
        return options.get(key).iterator().next();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("options", options)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectOptions)) {
            return false;
        }
        ProjectOptions that = (ProjectOptions) o;
        return Objects.equal(options, that.getOptions());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(options);
    }
}
