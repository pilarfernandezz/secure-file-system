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

    // HEX(HASH_SHA1(senha_texto_plano + SALT))

    public String encryptPassword(String pwd) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        this.md.update(pwd.getBytes("UTF8"));
        byte[] digest = this.md.digest();
        return HexBin.encode(digest);
    }

    public String decryptPassword(String pwd, String secret) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchProviderException {

//        Cipher cipher = Cipher.getInstance("RSA");
//        cipher.init(Cipher.DECRYPT_MODE, publicKey);
//        return new String(cipher.doFinal(digest), "UTF8");

        //TODO: CORRIGIR, NÃO FUNCIONA!

        byte[] digest = HexBin.decode(pwd);
        KeyGenerator generator = KeyGenerator.getInstance("DES");
        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");

        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed(secret.getBytes("UTF8"));

        generator.init(56, sr);
        Key k = generator.generateKey();
        cipher.init(Cipher.DECRYPT_MODE, k);
        byte[] decripted = cipher.doFinal(digest);
        return new String(decripted, StandardCharsets.UTF_8);
    }
}