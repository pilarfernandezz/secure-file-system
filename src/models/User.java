package models;

import services.DigitalCertificateService;

public class User {
    private int id;
    private String email;
    private String salt;
    private String password;
    private String passwordConfirmation;
    private String name;
    private String group;
    private String certificatePath;
    private String certificate;
    private boolean allowed;
    private int totalAccess = 0;
    private int totalConsults = 0;

    public User(String password, String passwordConfirmation, String group, String certificatePath, int totalAccess, int totalConsults) {
        this.passwordConfirmation = passwordConfirmation;
        this.password = password ;
        this.group = group;
        this.certificatePath = certificatePath;
        this.allowed = true;
        this.totalAccess = totalAccess;
        this.totalConsults = totalConsults;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getCertificatePath() {
        return certificatePath;
    }

    public void setCertificatePath(String certificatePath) {
        this.certificatePath = certificatePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getGroup() {
        return group;
    }

    public String getName() {
        return name;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTotalAccess() {
        return totalAccess;
    }

    public void setTotalAccess(int totalAccess) {
        this.totalAccess = totalAccess;
    }

    public int getTotalConsults() {
        return totalConsults;
    }

    public void setTotalConsults(int totalConsults) {
        this.totalConsults = totalConsults;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", salt='" + salt + '\'' +
                ", password='" + password + '\'' +
                ", passwordConfirmation='" + passwordConfirmation + '\'' +
                ", name='" + name + '\'' +
                ", group='" + group + '\'' +
                ", certificatePath='" + certificatePath + '\'' +
                ", allowed=" + allowed +
                ", totalAccess=" + totalAccess +
                ", totalConsults=" + totalConsults +
                '}';
    }
}
