package edu.stanford.protege.metaproject.serialization;

import com.google.gson.Gson;
import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.User;
import edu.stanford.protege.metaproject.api.UserRegistry;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class UserRegistrySerializerTest {
    private static final Set<User> userSet = Utils.getUserSet(5), diffUserSet = Utils.getUserSet(3);

    private String jsonUserManager, jsonOtherUserManager, jsonDiffUserManager;
    private UserRegistry userRegistry, otherUserRegistry, diffUserRegistry;
    private Gson gson;

    @Before
    public void setUp() {
        gson = new DefaultJsonSerializer().getInstance();

        userRegistry = Utils.getUserRegistry(userSet);
        otherUserRegistry = Utils.getUserRegistry(userSet);
        diffUserRegistry = Utils.getUserRegistry(diffUserSet);

        jsonUserManager = gson.toJson(userRegistry, UserRegistry.class);
        jsonOtherUserManager = gson.toJson(otherUserRegistry, UserRegistry.class);
        jsonDiffUserManager = gson.toJson(diffUserRegistry, UserRegistry.class);
    }

    @Test
    public void testNotNullSerialization() {
        assertThat(jsonUserManager, is(not(equalTo(null))));
    }

    @Test
    public void testNotNullDeserialization() {
        assertThat(gson.fromJson(jsonUserManager, UserRegistry.class), is(not(equalTo(null))));
    }

    @Test
    public void testRoundTrip() {
        assertThat(userRegistry, is(gson.fromJson(jsonUserManager, UserRegistry.class)));
    }

    @Test
    public void testSerializationOfEqualObjects() {
        assertThat(userRegistry, is(otherUserRegistry));
        assertThat(jsonUserManager, is(jsonOtherUserManager));
    }

    @Test
    public void testSerializationOfDifferentObjects() {
        assertThat(userRegistry, is(not(diffUserRegistry)));
        assertThat(jsonUserManager, is(not(gson.toJson(diffUserRegistry, UserRegistry.class))));
    }

    @Test
    public void testDeserializationOfEqualObjects() {
        assertThat(gson.fromJson(jsonUserManager, UserRegistry.class), is(gson.fromJson(jsonOtherUserManager, UserRegistry.class)));
    }

    @Test
    public void testDeserializationOfDifferentObjects() {
        assertThat(gson.fromJson(jsonUserManager, UserRegistry.class), is(not(gson.fromJson(jsonDiffUserManager, UserRegistry.class))));
    }

    @Test
    public void testGetUsersNotNull() {
        assertThat(gson.fromJson(jsonUserManager, UserRegistry.class).getUsers(), is(not(equalTo(null))));
    }

    @Test
    public void testGetUsers() {
        assertThat(gson.fromJson(jsonUserManager, UserRegistry.class).getUsers(), is(userSet));
    }

    @Test
    public void testGetGuestUser() {
        assertThat(gson.fromJson(jsonUserManager, UserRegistry.class).getGuestUser(), is(userRegistry.getGuestUser()));
    }
}