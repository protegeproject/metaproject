package edu.stanford.protege.metaproject.api;

import edu.stanford.protege.metaproject.Utils;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class GuiRestrictionTest {
    private static final String componentName = "buttonX1", diffComponentName = "buttonXY3", toStringHead = "GuiRestriction";
    private static final GuiRestriction.Visibility visibility = GuiRestriction.Visibility.HIDDEN;

    private GuiRestriction restriction, otherRestriction, diffRestriction;

    @Before
    public void setUp() {
        restriction = Utils.getGUIRestriction(componentName, visibility);
        otherRestriction = Utils.getGUIRestriction(componentName, visibility);
        diffRestriction = Utils.getGUIRestriction(diffComponentName, visibility);
    }

    @Test
    public void testNotNull() {
        assertThat(restriction, is(not(equalTo(null))));
    }

    @Test
    public void testGetGUIComponentName() {
        assertThat(restriction.getGuiComponentName(), is(componentName));
    }

    @Test
    public void testGetVisibility() {
        assertThat(restriction.getVisibility(), is(visibility));
    }

    @Test
    public void testEqualToSelf() {
        assertThat(restriction, is(restriction));
    }

    @Test
    public void testEquals() {
        assertThat(restriction, is(otherRestriction));
    }

    @Test
    public void testNotEquals() {
        assertThat(restriction, is(not(diffRestriction)));
    }

    @Test
    public void testHashCode() {
        assertThat(restriction.hashCode(), is(otherRestriction.hashCode()));
    }

    @Test
    public void testToString() {
        assertThat(restriction.toString(), startsWith(toStringHead));
    }
}
