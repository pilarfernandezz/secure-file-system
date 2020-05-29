package services;

import exceptions.InvalidCertificateException;
import exceptions.InvalidExtractionCertificateOwnerInfoException;

import java.io.*;
import java.security.Principal;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

public class DigitalCertificateService {

    private static DigitalCertificateService instance;

    public static DigitalCertificateService getInstance() {
        if (instance == null)
            instance = new DigitalCertificateService();
        return instance;
    }

    private DigitalCertificateService() {
    }

    // recebe o caminho de um arquivo contendo um certificado
    // digital e retorna o seu conteudo como objeto X509Certificate
    public X509Certificate loadCertificate(String cert, boolean isFile) throws FileNotFoundException, InvalidCertificateException {
        try {
            if (isFile) {
                return (X509Certificate) CertificateFactory.getInstance("X509")
                        .generateCertificate(new FileInputStream(new File(cert)));
            }
            return (X509Certificate) CertificateFactory.getInstance("X509")
                    .generateCertificate(new ByteArrayInputStream(cert.getBytes()));
        } catch (CertificateException | IOException e) {
            throw new InvalidCertificateException("Arquivo não compatível com certificado do tipo X509.");
        }
    }

    // recebe um certificado digital e retorna um objeto
    // que contem as informacoes do dono do certificado
    public Map<String, String> extractCertificateOwnerInfo(String path, boolean isFile) throws InvalidExtractionCertificateOwnerInfoException {
        try {
            X509Certificate certificate = this.loadCertificate(path, isFile);
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

            ownerInfo.put("CERTIFICATE", this.getCertificate(path));

            return ownerInfo;
        } catch (Exception e) {
            throw new InvalidExtractionCertificateOwnerInfoException("Ocorreu um erro ao tentar extrair dados do certificado.");
        }
    }

    public String getCertificate(String path) throws IOException {
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
            return "-----BEGIN CERTIFICATE-----".concat(text[1]);
        } catch (IOException e) {
            throw new IOException("Ocorreu um erro ao obter o certificado formato PEM: " + e.getMessage());
        }
    }

}
