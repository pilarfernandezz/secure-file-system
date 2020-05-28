package services;

import models.User;

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
    private Cipher cipher;
    private byte[] fileSeed;

    public static CipherService getInstance() throws NoSuchAlgorithmException, NoSuchPaddingException {
        if (instance == null) instance = new CipherService();
        return instance;
    }

    private CipherService() throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
    }

    public byte[] getFileSeed() {
        return fileSeed;
    }

    public void setFileSeed(byte[] fileSeed) {
        this.fileSeed = fileSeed;
    }

    public byte[] decryptFileContent(User user,  String fileName, boolean save, String newFileName) throws Exception {
        try {
            //TODO: TRATAR EXCEPTION!!!

            System.out.println("Verificando integridade e autenticidade do par de chaves do usuario " + user.getName() + "...");
            if (KeyService.getInstance().verifyKeyPairIntegrity(user.getPbcKey(), user.getPvtKey())) {
                System.out.println("Par de chaves do usuario " + user.getName() + " verificado, integro e autentico");

                //carrego o envelope digital e encontro a semente do PRNG da chave simetrica do arquivo em memoria
                System.out.println("Obtendo semente a partir do envelope digital do arquivo " + fileName + " para geracao de chave simetrica...");
                this.setFileSeed(DigitalEnvelopeService.getInstance().loadDigitalEnvelope(user.getPvtKey(), fileName.concat(".env")));
                System.out.println("Semente do arquivo " + fileName + " obtida com sucesso.");

                System.out.println("Decriptando arquivo " + fileName + "...");
                byte[] decripted = this.decrypt(user.getPbcKey(),fileName.concat(".enc"), fileName.concat(".asd"));

                if (decripted != null) {
                    System.out.println("Arquivo " + fileName + " decriptado com sucesso.");

                    if (save && newFileName != null && newFileName.trim().length() > 0) {
                        System.out.println("Salvando arquivo " + fileName + " decriptado...");
                        String fileRootPath = fileName.substring(0,fileName.lastIndexOf("/"));
                        this.saveDecryptedFile(decripted, fileRootPath.concat(newFileName));
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
    private byte[] decrypt(PublicKey publicKey,String path, String signPath) throws Exception {
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
            if (decripted != null && verifyFileContentIntegrity(publicKey,decripted, signPath)) {
                return decripted;
            } else {
                System.out.println();
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

    private boolean verifyFileContentIntegrity(PublicKey publicKey,byte[] fileContent, String signPath) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
        try {
            FileInputStream file = new FileInputStream((new File(signPath)));
            byte[] buffer = new byte[file.available()];
            file.read(buffer);

            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initVerify(publicKey);
            signature.update(fileContent);

            return signature.verify(buffer);
        } catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException e) {
            //TODO: TRATAR EXCEPTION!!!
            throw e;
        }
    }
}
