package examples;

import com.google.gson.Gson;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.EmailAddressAlreadyInUseException;
import edu.stanford.protege.metaproject.api.exception.UserIdAlreadyInUseException;
import edu.stanford.protege.metaproject.api.impl.*;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class ServerConfigurationExample {

    public static void main(String[] args) throws FileNotFoundException, EmailAddressAlreadyInUseException, UserIdAlreadyInUseException {
        Metaproject metaproject = Utils.getMetaproject();
        Gson gson = Utils.getGson();


        /*   Authentication   */

        // create an empty authentication manager
        AuthenticationManager authenticationManager = new AuthenticationManagerImpl();

        // register test users
        authenticationManager.add(new UserIdImpl("user1"), new PlainPasswordImpl("password1"));
        authenticationManager.add(new UserIdImpl("user2"), new PlainPasswordImpl("password2"));


        /*   Host   */

        Host host = new HostImpl(new AddressImpl("test_address"), 8080);


        /*   Ontology term identifiers   */

        // (optional) create identifier generator, in this case a prefixed sequential id generator.
        //  Note that not all suffixes are initialized; the missing ones are assumed to be 0.
        SequentialPrefixedNameEntityIriGenerator generator = new SequentialPrefixedNameEntityIriGenerator.Builder()
                .setEntityIriPrefix(new EntityIriPrefixImpl("http://protege.stanford.edu/"))
                .setClassNamePrefix(new EntityNamePrefixImpl("class-"))
                .setObjectPropertyNamePrefix(new EntityNamePrefixImpl("oprop-"))
                .setDataPropertyNamePrefix(new EntityNamePrefixImpl("dprop-"))
                .setAnnotationPropertyNamePrefix(new EntityNamePrefixImpl("aprop-"))
                .setIndividualNamePrefix(new EntityNamePrefixImpl("ind-"))
                .setObjectPropertyNameSuffix(new EntityNameSuffixImpl("9"))
                .setClassNameSuffix(new EntityNameSuffixImpl("3"))
                .createSequentialPrefixedNameEntityIriGenerator();

        EntityIriStatus idStatus = generator.getEntityIriStatus().get();


        // (optional) create map of properties
        Map<String,String> map = new HashMap<>();
        map.put("property1", "value1");
        map.put("property2", "value2");


        /*   Create server configuration   */

        // create new server configuration instance with the above parameters
        ServerConfiguration config = new ServerConfigurationImpl.Builder()
                .setHost(host)
                .setMetaproject(metaproject)
                .setPropertyMap(map)
                .setAuthenticationManager(authenticationManager)
                .setEntityIriStatus(idStatus)
                .createServerConfiguration();


        /*   Serialize server configuration   */

        // convert server configuration to its JSON representation
        String config_string = gson.toJson(config, ServerConfiguration.class);

        // print JSON representation
        Utils.println(Utils.sep + "\nJSON representation of sample server configuration\n" + Utils.sep + "\n\n" + config_string);
    }
}