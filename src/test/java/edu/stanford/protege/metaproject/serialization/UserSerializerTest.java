package edu.stanford.protege.metaproject.serialization;

import com.google.gson.Gson;
import edu.stanford.protege.metaproject.Utils;
import edu.stanford.protege.metaproject.api.EmailAddress;
import edu.stanford.protege.metaproject.api.Name;
import edu.stanford.protege.metaproject.api.User;
import edu.stanford.protege.metaproject.api.UserId;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class UserSerializerTest {
    private static final String
            userIdStr = "testUserId1",
            diffIdStr = "testUserId2";

    private static final UserId userId = Utils.getUserId(userIdStr), diffUserId = Utils.getUserId(diffIdStr);
    private static final Name userName = Utils.getName();
    private static final EmailAddress userEmail = Utils.getEmailAddress();

    private String jsonUser, jsonOtherUser, jsonDiffUser;
    private User user, otherUser, diffUser;
    private Gson gson;

    @Before
    public void setUp() {
        gson = new DefaultJsonSerializer().getInstance();

        user = Utils.getUser(userId, userName, userEmail);
        otherUser = Utils.getUser(userId, userName, userEmail);
        diffUser = Utils.getUser(diffUserId, userName, userEmail);

        jsonUser = gson.toJson(user);
        jsonOtherUser = gson.toJson(otherUser);
        jsonDiffUser = gson.toJson(diffUser);
    }

    @Test
    public void testNotNullSerialization() {
        assertThat(jsonUser, is(not(equalTo(null))));
    }

    @Test
    public void testNotNullDeserialization() {
        assertThat(gson.fromJson(jsonUser, User.class), is(not(equalTo(null))));
    }

    @Test
    public void testRoundTrip() {
        assertThat(user, is(gson.fromJson(jsonUser, User.class)));
    }

    @Test
    public void testSerializationOfEqualObjects() {
        assertThat(user, is(otherUser));
        assertThat(jsonUser, is(jsonOtherUser));
    }

    @Test
    public void testSerializationOfDifferentObjects() {
        assertThat(user, is(not(diffUser)));
        assertThat(jsonUser, is(not(gson.toJson(diffUser))));
    }

    @Test
    public void testDeserializationOfEqualObjects() {
        assertThat(gson.fromJson(jsonUser, User.class), is(gson.fromJson(jsonOtherUser, User.class)));
    }

    @Test
    public void testDeserializationOfDifferentObjects() {
        assertThat(gson.fromJson(jsonUser, User.class), is(not(gson.fromJson(jsonDiffUser, User.class))));
    }

    @Test
    public void testGetId() {
        assertThat(gson.fromJson(jsonUser, User.class).getId(), is(userId));
    }

    @Test
    public void testGetName() {
        assertThat(gson.fromJson(jsonUser, User.class).getName(), is(userName));
    }

    @Test
    public void testGetAddress() {
        assertThat(gson.fromJson(jsonUser, User.class).getEmailAddress(), is(userEmail));
    }
}