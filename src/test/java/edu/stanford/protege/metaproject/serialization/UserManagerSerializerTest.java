package edu.stanford.protege.metaproject.serialization;

import com.google.gson.Gson;
import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.User;
import edu.stanford.protege.metaproject.api.UserManager;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class UserManagerSerializerTest {
    private static final Set<User> userSet = Utils.getUserSet(5), diffUserSet = Utils.getUserSet(3);

    private String jsonUserManager, jsonOtherUserManager, jsonDiffUserManager;
    private UserManager userManager, otherUserManager, diffUserManager;
    private Gson gson;

    @Before
    public void setUp() {
        gson = new DefaultGsonSerializer().getDefaultSerializer();

        userManager = Utils.getUserManager(userSet);
        otherUserManager = Utils.getUserManager(userSet);
        diffUserManager = Utils.getUserManager(diffUserSet);

        jsonUserManager = gson.toJson(userManager, UserManager.class);
        jsonOtherUserManager = gson.toJson(otherUserManager, UserManager.class);
        jsonDiffUserManager = gson.toJson(diffUserManager, UserManager.class);
    }

    @Test
    public void testNotNullSerialization() {
        assertThat(jsonUserManager, is(not(equalTo(null))));
    }

    @Test
    public void testNotNullDeserialization() {
        assertThat(gson.fromJson(jsonUserManager, UserManager.class), is(not(equalTo(null))));
    }

    @Test
    public void testRoundTrip() {
        assertThat(userManager, is(gson.fromJson(jsonUserManager, UserManager.class)));
    }

    @Test
    public void testSerializationOfEqualObjects() {
        assertThat(userManager, is(otherUserManager));
        assertThat(jsonUserManager, is(jsonOtherUserManager));
    }

    @Test
    public void testSerializationOfDifferentObjects() {
        assertThat(userManager, is(not(diffUserManager)));
        assertThat(jsonUserManager, is(not(gson.toJson(diffUserManager, UserManager.class))));
    }

    @Test
    public void testDeserializationOfEqualObjects() {
        assertThat(gson.fromJson(jsonUserManager, UserManager.class), is(gson.fromJson(jsonOtherUserManager, UserManager.class)));
    }

    @Test
    public void testDeserializationOfDifferentObjects() {
        assertThat(gson.fromJson(jsonUserManager, UserManager.class), is(not(gson.fromJson(jsonDiffUserManager, UserManager.class))));
    }

    @Test
    public void testGetUsersNotNull() {
        assertThat(gson.fromJson(jsonUserManager, UserManager.class).getUsers(), is(not(equalTo(null))));
    }

    @Test
    public void testGetUsers() {
        assertThat(gson.fromJson(jsonUserManager, UserManager.class).getUsers(), is(userSet));
    }

    @Test
    public void testGetGuestUser() {
        assertThat(gson.fromJson(jsonUserManager, UserManager.class).getGuestUser(), is(userManager.getGuestUser()));
    }
}