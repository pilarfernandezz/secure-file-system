package models;

public class Certificate {
    private String path;
    private String version;
    private String serie;
    private String validUntil;
    private String SignatureType;
    private String emissor;
    private String name = "name";
    private String email = "pilar@gmail.com";

    public Certificate(String path){
        this.path = path;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(String validUntil) {
        this.validUntil = validUntil;
    }

    public String getSignatureType() {
        return SignatureType;
    }

    public void setSignatureType(String signatureType) {
        SignatureType = signatureType;
    }

    public String getEmissor() {
        return emissor;
    }

    public void setEmissor(String emissor) {
        this.emissor = emissor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
