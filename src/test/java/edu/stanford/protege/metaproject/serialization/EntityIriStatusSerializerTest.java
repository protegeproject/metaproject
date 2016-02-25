package edu.stanford.protege.metaproject.serialization;

import com.google.gson.Gson;
import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.EntityIriPrefix;
import edu.stanford.protege.metaproject.api.EntityIriStatus;
import edu.stanford.protege.metaproject.api.EntityNameSuffix;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class EntityIriStatusSerializerTest {
    private static final EntityNameSuffix classId = Utils.getEntityNameSuffix("43"), objPropId = Utils.getEntityNameSuffix("16"),
            dataPropId = Utils.getEntityNameSuffix("7"), annPropId = Utils.getEntityNameSuffix("3"), indId = Utils.getEntityNameSuffix("65");
    private static final EntityIriPrefix iriPrefix = Utils.getEntityIriPrefix();
    private String jsonOntologyTermIdStatus, jsonOtherOntologyTermIdStatus, jsonDiffOntologyTermIdStatus;
    private EntityIriStatus termStatus, otherTermStatus, diffTermStatus;
    private Gson gson;

    @Before
    public void setUp() {
        gson = new DefaultJsonSerializer().getInstance();

        termStatus = Utils.getEntityIriStatus(iriPrefix, classId, objPropId, dataPropId, annPropId, indId);
        otherTermStatus = Utils.getEntityIriStatus(iriPrefix, classId, objPropId, dataPropId, annPropId, indId);
        diffTermStatus = Utils.getEntityIriStatus();

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
        assertThat(gson.fromJson(jsonOntologyTermIdStatus, EntityIriStatus.class), is(not(equalTo(null))));
    }

    @Test
    public void testRoundTrip() {
        assertThat(termStatus, is(gson.fromJson(jsonOntologyTermIdStatus, EntityIriStatus.class)));
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
        assertThat(gson.fromJson(jsonOntologyTermIdStatus, EntityIriStatus.class), is(gson.fromJson(jsonOtherOntologyTermIdStatus, EntityIriStatus.class)));
    }

    @Test
    public void testDeserializationOfDifferentObjects() {
        assertThat(gson.fromJson(jsonOntologyTermIdStatus, EntityIriStatus.class), is(not(gson.fromJson(jsonDiffOntologyTermIdStatus, EntityIriStatus.class))));
    }

    @Test
    public void testGetClassIdSuffix() {
        assertThat(gson.fromJson(jsonOntologyTermIdStatus, EntityIriStatus.class).getClassNameSuffix().get(), is(classId));
    }

    @Test
    public void testGetObjectPropertyIdSuffix() {
        assertThat(gson.fromJson(jsonOntologyTermIdStatus, EntityIriStatus.class).getObjectPropertyNameSuffix().get(), is(objPropId));
    }

    @Test
    public void testGetAnnotationPropertyIdPrefix() {
        assertThat(gson.fromJson(jsonOntologyTermIdStatus, EntityIriStatus.class).getAnnotationPropertyNamePrefix().isPresent(), is(false));
    }
}