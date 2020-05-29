package services;

import exceptions.InvalidKeyFileException;

import javax.crypto.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class KeyService {

    private static KeyService instance;

    public static KeyService getInstance() {
        if (instance == null) {
            instance = new KeyService();
        }
        return instance;
    }

    private KeyService() {
    }

    // recebe o caminho do arquivo que contem o certificado digital que
    // contem a chave publica, gera um objeto PublicKey e o retorna
    public PublicKey loadPublicKey(X509Certificate cert) {
        return cert.getPublicKey();
    }

    // recebe o caminho do arquivo que contem a chave
    // privada, gera um objeto PrivateKey e o retorna
    public PrivateKey loadPrivateKey(String path, String secret) throws IOException, InvalidKeySpecException, InvalidKeyFileException, InvalidKeyException {
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
            throw new FileNotFoundException("Arquivo " + path + " não existe.");
        } catch (IOException e) {
            throw new IOException("Arquivo " + path + " inválido.");
        } catch (InvalidKeyException e) {
            throw new InvalidKeyFileException("O conteúdo do arquivo não é compatível com um objeto chave privada");
        } catch (InvalidKeySpecException e) {
            throw new InvalidKeySpecException("O conteúdo do arquivo não é compatível com um objeto chave privada formato PKCS8 RSA");
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException e) {
            throw new InvalidKeyException("Chave privada não pode ser decriptada pois frase secreta não corresponde a chave.");
        }
    }

    public int verifyKeyPairIntegrity(PublicKey publicKey, PrivateKey privateKey) throws InvalidKeyException {
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
            return signature.verify(signed) ? 0 : 4006;
        } catch (InvalidKeyException e) {
            throw new InvalidKeyException("4005 - Uma ou ambas chaves inválidas.");
        } catch (NoSuchAlgorithmException | SignatureException e) {
            throw new InvalidKeyException("4005 - Chaves assimétricas incompatíveis.");
        }
    }
}
