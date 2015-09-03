package edu.stanford.protege.metaproject.serialization;

import com.google.gson.Gson;
import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.OntologyTermIdStatus;
import edu.stanford.protege.metaproject.api.OntologyTermIdSuffix;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class OntologyTermIdStatusSerializerTest {
    private static final OntologyTermIdSuffix classId = Utils.getOntologyTermIdSuffix("43"), objPropId = Utils.getOntologyTermIdSuffix("16"),
            dataPropId = Utils.getOntologyTermIdSuffix("7"), annPropId = Utils.getOntologyTermIdSuffix("3"), indId = Utils.getOntologyTermIdSuffix("65");

    private String jsonOntologyTermIdStatus, jsonOtherOntologyTermIdStatus, jsonDiffOntologyTermIdStatus;
    private OntologyTermIdStatus termStatus, otherTermStatus, diffTermStatus;
    private Gson gson;

    @Before
    public void setUp() {
        gson = new SimpleGsonSerializer().getDefaultSerializer();

        termStatus = Utils.getOntologyTermIdStatus(classId, objPropId, dataPropId, annPropId, indId);
        otherTermStatus = Utils.getOntologyTermIdStatus(classId, objPropId, dataPropId, annPropId, indId);
        diffTermStatus = Utils.getOntologyTermIdStatus();

        jsonOntologyTermIdStatus = gson.toJson(termStatus);
        jsonOtherOntologyTermIdStatus = gson.toJson(otherTermStatus);
        jsonDiffOntologyTermIdStatus = gson.toJson(diffTermStatus);
    }

    @Test
    public void testNotNullSerialization() {
        assertThat(jsonOntologyTermIdStatus, is(not(equalTo(null))));
    }

    @Test
    public void testNotNullDeserialization() {
        assertThat(gson.fromJson(jsonOntologyTermIdStatus, OntologyTermIdStatus.class), is(not(equalTo(null))));
    }

    @Test
    public void testRoundTrip() {
        assertThat(termStatus, is(gson.fromJson(jsonOntologyTermIdStatus, OntologyTermIdStatus.class)));
    }

    @Test
    public void testSerializationOfEqualObjects() {
        assertThat(termStatus, is(otherTermStatus));
        assertThat(jsonOntologyTermIdStatus, is(jsonOtherOntologyTermIdStatus));
    }

    @Test
    public void testSerializationOfDifferentObjects() {
        assertThat(termStatus, is(not(diffTermStatus)));
        assertThat(jsonOntologyTermIdStatus, is(not(gson.toJson(diffTermStatus))));
    }

    @Test
    public void testDeserializationOfEqualObjects() {
        assertThat(gson.fromJson(jsonOntologyTermIdStatus, OntologyTermIdStatus.class), is(gson.fromJson(jsonOtherOntologyTermIdStatus, OntologyTermIdStatus.class)));
    }

    @Test
    public void testDeserializationOfDifferentObjects() {
        assertThat(gson.fromJson(jsonOntologyTermIdStatus, OntologyTermIdStatus.class), is(not(gson.fromJson(jsonDiffOntologyTermIdStatus, OntologyTermIdStatus.class))));
    }

    @Test
    public void testGetClassIdSuffix() {
        assertThat(gson.fromJson(jsonOntologyTermIdStatus, OntologyTermIdStatus.class).getClassIdSuffix().get(), is(classId));
    }

    @Test
    public void testGetObjectPropertyIdSuffix() {
        assertThat(gson.fromJson(jsonOntologyTermIdStatus, OntologyTermIdStatus.class).getObjectPropertyIdSuffix().get(), is(objPropId));
    }

    @Test
    public void testGetAnnotationPropertyIdPrefix() {
        assertThat(gson.fromJson(jsonOntologyTermIdStatus, OntologyTermIdStatus.class).getAnnotationPropertyIdPrefix().isPresent(), is(false));
    }
}