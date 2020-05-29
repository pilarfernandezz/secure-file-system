package services;

import exceptions.InvalidDecryptFileException;
import exceptions.InvalidDigitalEnvelopeException;
import exceptions.InvalidFileContentException;
import exceptions.InvalidKeyPairException;
import facade.Facade;
import models.User;

import javax.crypto.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.time.LocalDateTime;

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

    public byte[] decryptFileContent(User user, String fileName, boolean save, String newFileName) throws InvalidKeyException, InvalidDecryptFileException {
        try {
            System.out.println("Verificando integridade e autenticidade do par de chaves do usuario " + user.getName() + "...");
            if (KeyService.getInstance().verifyKeyPairIntegrity(user.getPbcKey(), user.getPvtKey())) {
                System.out.println("Par de chaves do usuario " + user.getName() + " verificado, integro e autentico");

                //carrego o envelope digital e encontro a semente do PRNG da chave simetrica do arquivo em memoria
                System.out.println("Obtendo semente a partir do envelope digital do arquivo " + fileName + " para geracao de chave simetrica...");
                this.setFileSeed(DigitalEnvelopeService.getInstance().loadDigitalEnvelope(user.getPvtKey(), fileName.concat(".env")));
                System.out.println("Semente do arquivo " + fileName + " obtida com sucesso.");

                System.out.println("Decriptando arquivo " + fileName + "...");
                byte[] decripted = this.decrypt(user.getPbcKey(), fileName.concat(".enc"), fileName.concat(".asd"), save);

                if (decripted != null) {
                    System.out.println("Arquivo " + fileName + " decriptado com sucesso.");

                    if (save && newFileName != null && newFileName.trim().length() > 0) {
                        System.out.println("Salvando arquivo " + fileName + " decriptado...");
                        String fileRootPath = fileName.substring(0, fileName.lastIndexOf("/"));
                        this.saveDecryptedFile(decripted, fileRootPath.concat(newFileName));
                        System.out.println("Arquivo  " + fileName + " decriptado salvo com sucesso.");
                    }

                    return decripted;
                } else {
                    throw new InvalidDecryptFileException("Falha na decriptação do arquivo " + fileName + ".");
                }
            } else {
                throw new InvalidKeyPairException("Par de chaves é inválido.");
            }
        } catch (InvalidKeyException | InvalidKeyPairException | InvalidDigitalEnvelopeException e) {
            throw new InvalidKeyException("Ocorreu um erro ao validar as chaves: " + e.getMessage());
        } catch (InvalidDecryptFileException | InvalidFileContentException | IOException e) {
            throw new InvalidDecryptFileException("Ocorreu um erro ao decriptar o arquivo: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new InvalidDecryptFileException("Arquivo não pode ser decriptado pois não pertence ao dono das credenciais fornecidas.");
        }
    }

    // recebe um arquivo criptografado, decripta e retorna seu conteúdo em um novo arquivo
    private byte[] decrypt(PublicKey publicKey, String path, String signPath, boolean save) throws InvalidFileContentException, InvalidDecryptFileException {
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
            if (decripted != null) {
                Facade.registerLogMessage(!save ? 8005 : 8013, Facade.getLoggedUser().getEmail(), null, LocalDateTime.now());
                if (verifyFileContentIntegrity(publicKey, decripted, signPath)) {
                    Facade.registerLogMessage(!save ? 8006 : 8014, Facade.getLoggedUser().getEmail(), null, LocalDateTime.now());
                    return decripted;
                } else {
                    Facade.registerLogMessage(!save ? 8008 : 8016, Facade.getLoggedUser().getEmail(), null, LocalDateTime.now());
                }
            } else {
                Facade.registerLogMessage(!save ? 8007 : 8015, Facade.getLoggedUser().getEmail(), null, LocalDateTime.now());
                throw new InvalidFileContentException("Arquivo " + path + " inválido ou corrompido.");
            }
            return null;
        } catch (InvalidFileContentException | IOException | InvalidKeyException e) {
            throw new InvalidFileContentException("Ocorreu um erro ao decriptar o arquivo: " + e.getMessage());
        } catch (NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException e) {
            throw new InvalidDecryptFileException("Arquivo não pode ser decriptado pois não pertence ao dono das chaves fornecidas.");
        }
    }

    private void saveDecryptedFile(byte[] fileContent, String fileName) {
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
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao salvar o arquivo decriptado: " + e.getMessage());
        }
    }

    private boolean verifyFileContentIntegrity(PublicKey publicKey, byte[] fileContent, String signPath) throws IOException, InvalidFileContentException {
        try {
            FileInputStream file = new FileInputStream((new File(signPath)));
            byte[] buffer = new byte[file.available()];
            file.read(buffer);

            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initVerify(publicKey);
            signature.update(fileContent);

            return signature.verify(buffer);
        } catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException e) {
            System.out.println("Ocorreu um erro ao verificar a integridade do arquivo: " + e.getMessage());
            throw new InvalidFileContentException("Arquivo corrompido ou inválido");
        } catch (IOException e) {
            throw new IOException("Arquivo de assinatura digital " + signPath + " é inválido");
        }
    }
}
