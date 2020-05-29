package services;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

import javax.crypto.*;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;

public class PasswordCipherService {

    private MessageDigest md;
    private static PasswordCipherService instance;

    public static PasswordCipherService getInstance() throws NoSuchAlgorithmException {
        if (instance == null)
            instance = new PasswordCipherService();
        return instance;
    }

    private PasswordCipherService() throws NoSuchAlgorithmException {
        this.md = MessageDigest.getInstance("SHA1");
    }

    public String encryptPassword(String pwd) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        this.md.update(pwd.getBytes("UTF8"));
        byte[] digest = this.md.digest();
        return HexBin.encode(digest);
    }
}