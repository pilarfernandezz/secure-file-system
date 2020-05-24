package models;

import repositories.UserRepository;

public class User {
    private int id;
    private String email;
    private String salt;
    private String password;
    private String passwordConfirmation;
    private String name;
    private String group;


    private String certificatePath;
    private Certificate certificate = new Certificate(certificatePath);
    private boolean allowed;

    public User(String password, String passwordConfirmation, String group, String certificatePath) {
        this.passwordConfirmation = passwordConfirmation;
        this.password = password ;
        this.name = certificate.getName();
        this.email = certificate.getEmail();
        this.group = group;
        this.certificatePath = certificatePath;
        this.allowed = true;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", passwordConfirmation='" + passwordConfirmation + '\'' +
                ", name='" + name + '\'' +
                ", group='" + group + '\'' +
                ", certificatePath='" + certificatePath + '\'' +
                ", certificate=" + certificate +
                ", allowed=" + allowed +
                '}';
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

}
