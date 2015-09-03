package edu.stanford.protege.metaproject.api.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.GUIRestriction;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class GUIRestrictionImpl implements GUIRestriction, Serializable {
    private static final long serialVersionUID = -2926753038288857138L;
    private final String component;
    private final Visibility visibility;

    /**
     * Constructor
     *
     * @param component Java swing component name
     * @param visibility  Type of restriction
     */
    public GUIRestrictionImpl(String component, Visibility visibility) {
        this.component = checkNotNull(component);
        this.visibility = checkNotNull(visibility);
    }

    @Override
    public String getGUIComponentName() {
        return component;
    }

    @Override
    public Visibility getVisibility() {
        return visibility;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GUIRestrictionImpl that = (GUIRestrictionImpl) o;
        return Objects.equal(component, that.component) &&
                Objects.equal(visibility, that.visibility);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(component, visibility);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("component", component)
                .add("visibility", visibility)
                .toString();
    }
}
