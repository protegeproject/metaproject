package edu.stanford.protege.metaproject.serialization;

import com.google.gson.Gson;
import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.OperationPrerequisite;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.IRI;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class OperationPrerequisiteSerializerTest {
    private static final IRI iri = Utils.getIRI(), diffIri = Utils.getIRI();
    private static final OperationPrerequisite.Modifier modifier = OperationPrerequisite.Modifier.PRESENT, diffModifier = OperationPrerequisite.Modifier.ABSENT;

    private String jsonPrerequisite, jsonOtherPrerequisite, jsonDiffPrerequisite;
    private OperationPrerequisite prerequisite, otherPrerequisite, diffPrerequisite;
    private Gson gson;

    @Before
    public void setUp() {
        gson = new DefaultGsonSerializer().getDefaultSerializer();

        prerequisite = Utils.getOperationPrerequisite(iri, modifier);
        otherPrerequisite = Utils.getOperationPrerequisite(iri, modifier);
        diffPrerequisite = Utils.getOperationPrerequisite(diffIri, diffModifier);

        jsonPrerequisite = gson.toJson(prerequisite, OperationPrerequisite.class);
        jsonOtherPrerequisite = gson.toJson(otherPrerequisite, OperationPrerequisite.class);
        jsonDiffPrerequisite = gson.toJson(diffPrerequisite, OperationPrerequisite.class);
    }

    @Test
    public void testNotNullSerialization() {
        assertThat(jsonPrerequisite, is(not(equalTo(null))));
    }

    @Test
    public void testNotNullDeserialization() {
        assertThat(gson.fromJson(jsonPrerequisite, OperationPrerequisite.class), is(not(equalTo(null))));
    }

    @Test
    public void testRoundTrip() {
        assertThat(prerequisite, is(gson.fromJson(jsonPrerequisite, OperationPrerequisite.class)));
    }

    @Test
    public void testSerializationOfEqualObjects() {
        assertThat(prerequisite, is(otherPrerequisite));
        assertThat(jsonPrerequisite, is(jsonOtherPrerequisite));
    }

    @Test
    public void testSerializationOfDifferentObjects() {
        assertThat(prerequisite, is(not(diffPrerequisite)));
        assertThat(jsonPrerequisite, is(not(gson.toJson(diffPrerequisite))));
    }

    @Test
    public void testDeserializationOfEqualObjects() {
        assertThat(gson.fromJson(jsonPrerequisite, OperationPrerequisite.class), is(gson.fromJson(jsonOtherPrerequisite, OperationPrerequisite.class)));
    }

    @Test
    public void testDeserializationOfDifferentObjects() {
        assertThat(gson.fromJson(jsonPrerequisite, OperationPrerequisite.class), is(not(gson.fromJson(jsonDiffPrerequisite, OperationPrerequisite.class))));
    }

    @Test
    public void testGetEntityIRI() {
        assertThat(gson.fromJson(jsonPrerequisite, OperationPrerequisite.class).getPrerequisiteIri(), is(iri));
    }

    @Test
    public void testGetModifier() {
        assertThat(gson.fromJson(jsonPrerequisite, OperationPrerequisite.class).getModifier(), is(modifier));
    }
}