package edu.stanford.protege.metaproject.serialization;

import com.google.gson.Gson;
import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.Modality;
import edu.stanford.protege.metaproject.api.OperationRestriction;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.AxiomType;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class OperationRestrictionSerializerTest {
    private static final AxiomType axType = AxiomType.ANNOTATION_ASSERTION, diffAxType = AxiomType.CLASS_ASSERTION;
    private static final Modality
            modifier = Modality.AxiomChange.ADDITION,
            diffModifier = Modality.AxiomChange.REMOVAL;

    private String jsonRestriction, jsonOtherRestriction, jsonDiffRestriction;
    private OperationRestriction restriction, otherRestriction, diffRestriction;
    private Gson gson;

    @Before
    public void setUp() {
        gson = new DefaultJsonSerializer().getInstance();

        restriction = Utils.getOperationRestriction(axType, modifier);
        otherRestriction = Utils.getOperationRestriction(axType, modifier);
        diffRestriction = Utils.getOperationRestriction(diffAxType, diffModifier);

        jsonRestriction = gson.toJson(restriction, OperationRestriction.class);
        jsonOtherRestriction = gson.toJson(otherRestriction, OperationRestriction.class);
        jsonDiffRestriction = gson.toJson(diffRestriction, OperationRestriction.class);
    }

    @Test
    public void testNotNullSerialization() {
        assertThat(jsonRestriction, is(not(equalTo(null))));
    }

    @Test
    public void testNotNullDeserialization() {
        assertThat(gson.fromJson(jsonRestriction, OperationRestriction.class), is(not(equalTo(null))));
    }

    @Test
    public void testRoundTrip() {
        assertThat(restriction, is(gson.fromJson(jsonRestriction, OperationRestriction.class)));
    }

    @Test
    public void testSerializationOfEqualObjects() {
        assertThat(restriction, is(otherRestriction));
        assertThat(jsonRestriction, is(jsonOtherRestriction));
    }

    @Test
    public void testSerializationOfDifferentObjects() {
        assertThat(restriction, is(not(diffRestriction)));
        assertThat(jsonRestriction, is(not(jsonDiffRestriction)));
    }

    @Test
    public void testDeserializationOfEqualObjects() {
        assertThat(gson.fromJson(jsonRestriction, OperationRestriction.class), is(gson.fromJson(jsonOtherRestriction, OperationRestriction.class)));
    }

    @Test
    public void testDeserializationOfDifferentObjects() {
        assertThat(gson.fromJson(jsonRestriction, OperationRestriction.class), is(not(gson.fromJson(jsonDiffRestriction, OperationRestriction.class))));
    }

    @Test
    public void testGetRestriction() {
        assertThat(gson.fromJson(jsonRestriction, OperationRestriction.class).getRestriction(), is(axType));
    }

    @Test
    public void testGetModifier() {
        assertThat(gson.fromJson(jsonRestriction, OperationRestriction.class).getModality(), is(modifier));
    }
}