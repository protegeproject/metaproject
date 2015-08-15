package edu.stanford.protege.metaproject.api.impl;

import edu.stanford.protege.metaproject.api.Salt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A password hash generator based on the MD5 hashing technique
 *
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class MD5PasswordHashGenerator {

    /**
     * Constructor
     */
    public MD5PasswordHashGenerator() { }

    /**
     * Get a secure (salted) password
     *
     * @param password  Password
     * @param salt  Salt
     * @return Salted password
     * @throws NoSuchAlgorithmException No such algorithm exception
     */
    public SaltedPassword getSaltedPasswordHash(String password, Salt salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(salt.getBytes());
        byte[] bytes = md.digest(password.getBytes());
        return new SaltedPassword(bytes);
    }
}
