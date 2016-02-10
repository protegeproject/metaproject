package edu.stanford.protege.metaproject.examples;

import com.google.gson.Gson;
import edu.stanford.protege.metaproject.api.AccessControlPolicy;
import edu.stanford.protege.metaproject.serialization.SimpleGsonSerializer;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public class Utils {
    public static final String sep = "------------------------------------------------";

    public static Gson getGson() {
        return new SimpleGsonSerializer().getDefaultSerializer();
    }

    public static AccessControlPolicy getAccessControlPolicy() {
        return new ExamplePolicy().getPolicy();
    }

    public static void print() {
        print("");
    }

    public static void print(String string) {
        System.out.print(string);
    }

    public static void println() {
        println("");
    }

    public static void println(String string) {
        System.out.println(string);
    }
}
