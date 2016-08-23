package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import edu.stanford.protege.metaproject.api.ProjectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Center for Biomedical Informatics Research <br>
 * Stanford University
 */
@Immutable
@ThreadSafe
public final class ProjectOptionsImpl implements ProjectOptions, Serializable {
    private static final Logger logger = LoggerFactory.getLogger(ProjectOptionsImpl.class.getName());
    private static final long serialVersionUID = -6551389683971976515L;
    @Nonnull private final ImmutableMap<String,Set<String>> options;

    /**
     * Constructor
     *
     * @param options   Project options map
     */
    public ProjectOptionsImpl(@Nonnull Map<String,Set<String>> options) {
        this.options = ImmutableMap.copyOf(checkNotNull(options));
    }

    @Override
    @Nonnull
    public ImmutableMap<String,Set<String>> getOptions() {
        return options;
    }

    @Override
    @Nonnull
    public ImmutableSet<String> getValues(@Nonnull String key) {
        if(options.get(key) == null) {
            logger.info("There are no values for the project option: " + key);
            return ImmutableSet.of();
        }
        return ImmutableSet.copyOf(options.get(key));
    }

    @Override
    @Nullable
    public String getValue(@Nonnull String key) {
        if(options.get(key) == null) {
            logger.info("There are no values for the project option: " + key);
            return null;
        }
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
