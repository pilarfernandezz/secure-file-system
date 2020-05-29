package services;

import exceptions.InvalidDigitalEnvelopeException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

public class DigitalEnvelopeService {

    private static DigitalEnvelopeService instance;

    public static DigitalEnvelopeService getInstance() {
        if (instance == null)
            instance = new DigitalEnvelopeService();
        return instance;
    }

    private DigitalEnvelopeService(){}

    // recebe o caminho de um arquivo contendo um certificado
    // digital e retorna o seu conteudo como objeto
    public byte[] loadDigitalEnvelope(PrivateKey privateKey, String path) throws NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidDigitalEnvelopeException {
        try {
            FileInputStream file = new FileInputStream((new File(path)));
            byte[] buffer = new byte[file.available()];
            file.read(buffer);

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            byte[] decripted = cipher.doFinal(buffer);

            if (decripted != null && decripted.length > 0)
                return decripted;
            else
                throw new InvalidDigitalEnvelopeException("Conteúdo do arquivo " + path + " não é compatível com um envelope digital válido.");
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Arquivo " + path + " não existe.");
        } catch (IOException e) {
            throw new IOException("Arquivo " + path + " inválido.");
        } catch (InvalidKeyException e) {
            throw new InvalidKeyException("Chave privada fornecida inválida.");
        } catch (BadPaddingException | NoSuchPaddingException | IllegalBlockSizeException e) {
            throw new InvalidDigitalEnvelopeException("Conteúdo do arquivo " + path + " não é compatível com um envelope digital válido.");
        }
    }
}
