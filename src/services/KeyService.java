package services;

import exceptions.InvalidKeyFileException;

import javax.crypto.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class KeyService {

    // recebe o caminho do arquivo que contem o certificado digital que
    // contem a chave publica, gera um objeto PublicKey e o retorna
    public PublicKey loadPublicKey(X509Certificate cert) throws CertificateException, FileNotFoundException {
        return cert.getPublicKey();
    }

    // recebe o caminho do arquivo que contem a chave
    // privada, gera um objeto PrivateKey e o retorna
    public PrivateKey loadPrivateKey(String path, String secret) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, IOException, InvalidKeySpecException, InvalidKeyFileException {
        byte[] buffer = null;
        try {
            FileInputStream file = new FileInputStream((new File(path)));
            buffer = new byte[file.available()];
            file.read(buffer);

            KeyGenerator generator = KeyGenerator.getInstance("DES");
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed(secret.getBytes("UTF8"));

            generator.init(56, sr);
            Key k = generator.generateKey();
            cipher.init(Cipher.DECRYPT_MODE, k);
            byte[] decripted = cipher.doFinal(buffer);
            String keyString = new String(decripted, StandardCharsets.UTF_8);

            keyString = keyString.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\n", "");

            byte[] keyDecriptedAndDecoded = Base64.getDecoder().decode(keyString);
            return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(keyDecriptedAndDecoded));
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File " + path + " doesn't exists.");
        } catch (IOException e) {
            throw new IOException("File " + path + " is invalid.");
        } catch (InvalidKeyException e) {
            throw new InvalidKeyFileException("File content doesn't match to a valid private key");
        } catch (InvalidKeySpecException e) {
            throw new InvalidKeySpecException("File content doesn't match to a valid PKCS8 RSA private key");
        }
    }

    public boolean verifyKeyPairIntegrity(PublicKey publicKey, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        try {
            SecureRandom random = new SecureRandom();
            byte[] randomBytes = new byte[2048];
            random.nextBytes(randomBytes);

            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(randomBytes);
            byte[] digest = md.digest();

            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initSign(privateKey);
            for (int i = 0; i < (digest.length / 256); i += 256) {
                signature.update(digest, i, 256);
            }
            byte[] signed = signature.sign();
            signature.initVerify(publicKey);
            return signature.verify(signed);
        } catch (InvalidKeyException e) {
            throw new InvalidKeyException("One or both keys are invalid.");
        }
    }
}
