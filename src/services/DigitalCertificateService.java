package services;

import exceptions.InvalidCertificateException;
import exceptions.InvalidExtractionCertificateOwnerInfoException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.Principal;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

public class DigitalCertificateService {

    // recebe o caminho de um arquivo contendo um certificado
    // digital e retorna o seu conteudo como objeto X509Certificate
    public X509Certificate loadCertificate(String path) throws FileNotFoundException, InvalidCertificateException {
        try {
            return (X509Certificate) CertificateFactory.getInstance("X509")
                    .generateCertificate(new FileInputStream(new File(path)));
        } catch (CertificateException e) {
            throw new InvalidCertificateException("File doesn't match to valid X509 certificate. Please, check the information and try again.");
        }
    }

    // recebe um certificado digital e retorna um objeto
    // que contem as informacoes do dono do certificado
    public Map<String, String> extractCertificateOwnerInfo(X509Certificate certificate) throws InvalidExtractionCertificateOwnerInfoException {
        try {
            Principal owner = certificate.getSubjectDN();
            String[] ownerInfoArray = owner.getName().split(", ");
            Map<String, String> ownerInfo = new HashMap<>();
            for (String s : ownerInfoArray) {
                String[] ownerInfoSplitedArray = s.split("=");
                ownerInfo.put(ownerInfoSplitedArray[0], ownerInfoSplitedArray[1]);
            }

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
}
