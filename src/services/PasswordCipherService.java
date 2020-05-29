package services;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

import javax.crypto.*;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;

public class PasswordCipherService {

    private MessageDigest md;
    private static PasswordCipherService instance;

    public static PasswordCipherService getInstance() throws Exception {
        if (instance == null)
            instance = new PasswordCipherService();
        return instance;
    }

    private PasswordCipherService() throws Exception {
        try {
            this.md = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e){
            throw new Exception("Ocorreu um erro ao encriptar a senha: " + e.getMessage());
        }
    }

    public String encryptPassword(String pwd) throws Exception {
        try{
            this.md.update(pwd.getBytes("UTF8"));
            byte[] digest = this.md.digest();
            return HexBin.encode(digest);
        } catch(UnsupportedEncodingException e){
            throw new Exception("Ocorreu um erro ao encriptar a senha: " + e.getMessage());
        }
    }
}