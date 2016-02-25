package edu.stanford.protege.metaproject.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import edu.stanford.protege.metaproject.api.*;
import edu.stanford.protege.metaproject.api.exception.UserNotRegisteredException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Rafael Gonçalves <br>
 * Stanford Center for Biomedical Informatics Research
 */
public final class ServerAuthorizedUserAuthenticator implements UserAuthenticator {
    private final AuthenticationManager authenticationManager;
    private final PasswordHasher passwordHasher;

    /**
     * Constructor
     *
     * @param authenticationManager Authentication manager
     * @param passwordHasher    Password hasher
     */
    public ServerAuthorizedUserAuthenticator(AuthenticationManager authenticationManager, PasswordHasher passwordHasher) {
        this.authenticationManager = checkNotNull(authenticationManager);
        this.passwordHasher = checkNotNull(passwordHasher);
    }

    @Override
    public boolean hasValidCredentials(UserId userId, PlainPassword password) throws UserNotRegisteredException {
        SaltedPasswordDigest correctPassword = authenticationManager.getAuthenticationDetails(userId).getPassword();

        // hash given password with the same parameters as the server-stored password
        SaltedPasswordDigest saltedPasswordDigest = hash(password, correctPassword);
        return authenticationManager.hasValidCredentials(userId, saltedPasswordDigest);
    }

    /**
     * Hash the given password
     *
     * @param password  Given password
     * @param correctPassword   Password stored for the user
     * @return Hashed password
     */
    private SaltedPasswordDigest hash(PlainPassword password, SaltedPasswordDigest correctPassword) {
        Salt salt = correctPassword.getSalt();
        byte[] saltBytes = salt.getBytes();
        byte[] hash = fromHex(correctPassword.getPassword());
        byte[] testHash = passwordHasher.hash(password.getPassword(), saltBytes, passwordHasher.getNumberOfIterations(), hash.length);
        return new SaltedPasswordDigestImpl(Hex.encodeHexString(testHash), salt);
    }

    /**
     * Converts a string of hexadecimal characters into a byte array
     *
     * @param hex   Hex string
     * @return Byte array of decoded hex string
     */
    private byte[] fromHex(String hex) {
        byte[] binary = new byte[0];
        try {
            binary = Hex.decodeHex(hex.toCharArray());
        } catch (DecoderException e) {
            e.printStackTrace();
        }
        return binary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerAuthorizedUserAuthenticator that = (ServerAuthorizedUserAuthenticator) o;
        return Objects.equal(authenticationManager, that.authenticationManager) &&
                Objects.equal(passwordHasher, that.passwordHasher);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(authenticationManager, passwordHasher);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("authenticationManager", authenticationManager)
                .add("passwordHasher", passwordHasher)
                .toString();
    }
}
