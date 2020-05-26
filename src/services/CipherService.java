package services;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.X509Certificate;

public class CipherService {
    private static CipherService instance;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private X509Certificate certificate;
    private Cipher cipher;
    private byte[] fileSeed;
    private String fileRootPath = "";
    private String keysRootPath = "";

    public String getFileRootPath() {
        return fileRootPath;
    }

    public void setFileRootPath(String rootPath) {
        this.fileRootPath = rootPath;
    }

    public String getKeysRootPath() {
        return keysRootPath;
    }

    public void setKeysRootPath(String rootPath) {
        this.keysRootPath = rootPath;
    }

    public static CipherService getInstance() throws NoSuchAlgorithmException, NoSuchPaddingException {
        if (instance == null) instance = new CipherService();
        return instance;
    }

    private CipherService() throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public X509Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(X509Certificate certificate) {
        this.certificate = certificate;
    }

    public byte[] getFileSeed() {
        return fileSeed;
    }

    public void setFileSeed(byte[] fileSeed) {
        this.fileSeed = fileSeed;
    }

    public byte[] decryptFileContent(String userName, String fileName, boolean save, String newFileName) throws Exception {
        try {
            //TODO: TRATAR EXCEPTION!!!

            //carreguei o certificado do user em memoria
            System.out.println("Obtendo certificado digital do usuario " + userName + "...");
            this.setCertificate(DigitalCertificateService.getInstance().loadCertificate(this.getKeysRootPath().concat(userName.concat("-x509.crt"))));
            System.out.println("Certificado digital do usuario " + userName + "  obtido com sucesso.");

            //carreguei a chave publica do admin em memoria
            System.out.println("Obtendo chave publica do usuario " + userName + "...");
            this.setPublicKey(KeyService.getInstance().loadPublicKey(this.getCertificate()));
            System.out.println("Chave publica do usuario " + userName + " obtida com sucesso.");

            //carreguei a chave privada do admin em memoria
            System.out.println("Obtendo chave privada do usuario " + userName + "...");
            this.setPrivateKey(KeyService.getInstance().loadPrivateKey(this.getKeysRootPath().concat(userName.concat("-pkcs8-des.key")), userName));
            System.out.println("Chave privada do usuario " + userName + " obtida com sucesso.");

            System.out.println("Verificando integridade e autenticidade do par de chaves do usuario " + userName + "...");
            if (KeyService.getInstance().verifyKeyPairIntegrity(this.getPublicKey(), this.getPrivateKey())) {
                System.out.println("Par de chaves do usuario " + userName + " verificado, integro e autentico");

                //carrego o envelope digital e encontro a semente do PRNG da chave simetrica do arquivo em memoria
                System.out.println("Obtendo semente a partir do envelope digital do arquivo " + fileName + " para geracao de chave simetrica...");
                this.setFileSeed(DigitalEnvelopeService.getInstance().loadDigitalEnvelope(this.getPrivateKey(), this.getFileRootPath().concat(fileName.concat(".env"))));
                System.out.println("Semente do arquivo " + fileName + " obtida com sucesso.");

                System.out.println("Decriptando arquivo " + fileName + "...");
                byte[] decripted = this.decrypt(this.getFileRootPath().concat(fileName.concat(".enc")), this.getFileRootPath().concat(fileName.concat(".asd")));

                if (decripted != null) {
                    System.out.println("Arquivo " + fileName + " decriptado com sucesso.");

                    if (save && newFileName != null && newFileName.trim().length() > 0) {
                        System.out.println("Salvando arquivo " + fileName + " decriptado...");
                        this.saveDecryptedFile(decripted, this.getFileRootPath().concat(newFileName));
                        System.out.println("Arquivo  " + fileName + " decriptado salvo com sucesso.");
                    }

                    return decripted;
                } else {
                    //TODO TRATAR EXCEPTION!!
                    throw new Exception("");
                }
            } else {
                //TODO TRATAR EXCEPTION!!
                throw new Exception("");
            }
        } catch (Exception e) {
            throw e;
        }
    }

    // recebe um arquivo criptografado, decripta e retorna seu conteúdo em um novo arquivo
    private byte[] decrypt(String path, String signPath) throws Exception {
        try {
            FileInputStream file = new FileInputStream((new File(path)));
            byte[] buffer = new byte[file.available()];
            file.read(buffer);

            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed(this.fileSeed);

            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            keyGenerator.init(56, sr);

            cipher.init(Cipher.DECRYPT_MODE, keyGenerator.generateKey());
            byte[] decripted = cipher.doFinal(buffer);
            if (decripted != null && verifyFileContentIntegrity(decripted, signPath)) {
                return decripted;
            } else {
                //TODO: TRATAR EXCEPTION!!!
                throw new Exception();
            }
        } catch (Exception e) {
            //TODO: TRATAR EXCEPTION!!!
            throw e;
        }
    }

    private void saveDecryptedFile(byte[] fileContent, String fileName) throws IOException {
        try {
            File file = new File(fileName);
            if (file != null) {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                if (fileOutputStream != null) {
                    fileOutputStream.write(fileContent);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    file.createNewFile();
                }
            }
        } catch (Exception e) {
            //TODO: TRATAR EXCEPTION!!!
            throw e;
        }
    }

    private boolean verifyFileContentIntegrity(byte[] fileContent, String signPath) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(fileContent);
            byte[] digest = md.digest();

            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initSign(this.getPrivateKey());
            for (int i = 0; i < (digest.length / 256); i += 256) {
                signature.update(digest, i, 256);
            }
            //TODO VER ONDE ENTRA A ASSINATURA VINDA DO ARQUIVO
            byte[] signed = signature.sign();
            signature.initVerify(this.getPublicKey());
            return signature.verify(signed);
        } catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException e) {
            //TODO: TRATAR EXCEPTION!!!
            throw e;
        }
    }
}
