package edu.stanford.protege.metaproject.serialization;

import com.google.gson.Gson;
import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.GuiRestriction;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class GuiRestrictionSerializerTest {
    private static final String componentName = "buttonX1", diffComponentName = "buttonXY3";
    private static final GuiRestriction.Visibility visibility = GuiRestriction.Visibility.HIDDEN;

    private String jsonRestriction, jsonOtherRestriction, jsonDiffRestriction;
    private GuiRestriction restriction, otherRestriction, diffRestriction;
    private Gson gson;

    @Before
    public void setUp() {
        gson = new SimpleGsonSerializer().getDefaultSerializer();

        restriction = Utils.getGUIRestriction(componentName, visibility);
        otherRestriction = Utils.getGUIRestriction(componentName, visibility);
        diffRestriction = Utils.getGUIRestriction(diffComponentName, visibility);

        jsonRestriction = gson.toJson(restriction);
        jsonOtherRestriction = gson.toJson(otherRestriction);
        jsonDiffRestriction = gson.toJson(diffRestriction);
    }

    @Test
    public void testNotNullSerialization() {
        assertThat(jsonRestriction, is(not(equalTo(null))));
    }

    @Test
    public void testNotNullDeserialization() {
        assertThat(gson.fromJson(jsonRestriction, GuiRestriction.class), is(not(equalTo(null))));
    }

    @Test
    public void testRoundTrip() {
        assertThat(restriction, is(gson.fromJson(jsonRestriction, GuiRestriction.class)));
    }

    @Test
    public void testSerializationOfEqualObjects() {
        assertThat(restriction, is(otherRestriction));
        assertThat(jsonRestriction, is(jsonOtherRestriction));
    }

    @Test
    public void testSerializationOfDifferentObjects() {
        assertThat(restriction, is(not(diffRestriction)));
        assertThat(jsonRestriction, is(not(gson.toJson(diffRestriction))));
    }

    @Test
    public void testDeserializationOfEqualObjects() {
        assertThat(gson.fromJson(jsonRestriction, GuiRestriction.class), is(gson.fromJson(jsonOtherRestriction, GuiRestriction.class)));
    }

    @Test
    public void testDeserializationOfDifferentObjects() {
        assertThat(gson.fromJson(jsonRestriction, GuiRestriction.class), is(not(gson.fromJson(jsonDiffRestriction, GuiRestriction.class))));
    }

    @Test
    public void testGetGUIComponentName() {
        assertThat(gson.fromJson(jsonRestriction, GuiRestriction.class).getGuiComponentName(), is(componentName));
    }

    @Test
    public void testGetVisibility() {
        assertThat(gson.fromJson(jsonRestriction, GuiRestriction.class).getVisibility(), is(visibility));
    }
}
