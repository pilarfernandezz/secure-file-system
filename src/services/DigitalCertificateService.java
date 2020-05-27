package services;

import exceptions.InvalidCertificateException;
import exceptions.InvalidExtractionCertificateOwnerInfoException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Principal;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

public class DigitalCertificateService {
    private String certificateHex;

    private static DigitalCertificateService instance;

    public static DigitalCertificateService getInstance() {
        if (instance == null)
            instance = new DigitalCertificateService();
        return instance;
    }

    // recebe o caminho de um arquivo contendo um certificado
    // digital e retorna o seu conteudo como objeto X509Certificate
    public X509Certificate loadCertificate(String path) throws FileNotFoundException, InvalidCertificateException {
        try {
            return (X509Certificate) CertificateFactory.getInstance("X509")
                    .generateCertificate(new FileInputStream(new File(path)));
        } catch (CertificateException | IOException e) {
            throw new InvalidCertificateException("File doesn't match to valid X509 certificate. Please, check the information and try again.");
        }
    }

    // recebe um certificado digital e retorna um objeto
    // que contem as informacoes do dono do certificado
    public Map<String, String> extractCertificateOwnerInfo(String path) throws InvalidExtractionCertificateOwnerInfoException {
        try {
            X509Certificate certificate = this.loadCertificate(path);
            Principal owner = certificate.getSubjectDN();
            String[] ownerInfoArray = owner.getName().split(", ");
            Map<String, String> ownerInfo = new HashMap<>();
            for (String s : ownerInfoArray) {
                String[] ownerInfoSplitedArray = s.split("=");
                ownerInfo.put(ownerInfoSplitedArray[0], ownerInfoSplitedArray[1]);
            }
            ownerInfo.put("SIGNATURETYPE", certificate.getSigAlgName());
            ownerInfo.put("VERSION", String.valueOf(certificate.getVersion()));
            ownerInfo.put("SERIE", String.valueOf(certificate.getSerialNumber()));
            ownerInfo.put("VALIDUNTIL", certificate.getNotAfter().toString());
            Principal issuer = certificate.getIssuerDN();
            String[] issuerInfoArray = owner.getName().split(", ");
            for (String s : issuerInfoArray) {
                String[] issuerInfoSplitedArray = s.split("=");
                if (issuerInfoSplitedArray[0].equals("CN")) {
                    ownerInfo.put("ISSUER", issuerInfoSplitedArray[1]);
                }
            }
            ownerInfo.put("CERTIFICATE", this.certificateHex);

            return ownerInfo;
        } catch (Exception e) {
            throw new InvalidExtractionCertificateOwnerInfoException("An error was occurred while trying to extract certificate owner info. Please, check the information and try again.");
        }
    }

    // exibe informacoes do dono do certificado
    private void printCertificateOwnerInfo(Map<String, String> ownerInfo) {
        for (String key : ownerInfo.keySet()) {
            System.out.println(key + " - " + ownerInfo.get(key));
        }
    }

    private void getCertificate(String path) throws IOException {
        File file = new File(path);
        String textAll = "";
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            int singleCharInt;
            char singleChar = Character.MIN_VALUE;

            while ((singleCharInt = fileInputStream.read()) != -1) {
                singleChar = (char) singleCharInt;
                textAll += singleChar;
            }

            String[] text = textAll.split("-----BEGIN CERTIFICATE-----");
            this.certificateHex = text[1].replace("-----END CERTIFICATE-----", "");
        } catch (Exception e) {
            throw e;
        }
    }
}
