package edu.stanford.protege.metaproject.api;

/**
 * A representation of a restriction to the graphical user interface. It relies on the assumption that swing
 * components have been (uniquely) named, allowing one to fetch a component and change its visibility based
 * on the unique name alone. The visibility of the component is set according to the visibility type.
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public interface GuiRestriction {

    /**
     * Get the GUI JComponent name that is being restricted
     *
     * @return JComponent
     */
    String getGuiComponentName();

    /**
     * Get the type of restriction of the GUI component
     *
     * @return Type of restriction
     */
    Visibility getVisibility();

    /**
     * Types of user interface component restrictions
     */
    enum Visibility {
        HIDDEN, VISIBLE
    }
}
