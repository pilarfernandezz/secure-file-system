package services;

import exceptions.InvalidExtractionCertificateOwnerInfoException;
import models.LockedUser;
import models.User;
import repositories.LockedUserRepository;
import repositories.UserRepository;

import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AuthenticationService {
    private static AuthenticationService instance;
    private User loggedUser;
    private static UserRepository userRepository;
    private static LockedUserRepository lockedUserRepository;
    private static DigitalCertificateService getInstance;
    private static KeyService keyService;

    public static AuthenticationService getInstance() {
        try {
            userRepository = UserRepository.getInstance();
            lockedUserRepository = LockedUserRepository.getInstance();
            keyService = KeyService.getInstance();
            getInstance = DigitalCertificateService.getInstance();
            if (instance == null)
                instance = new AuthenticationService();
            return instance;
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao inicializar o serviço de autenticação: " + e.getMessage());
            return null;
        }
    }

    private AuthenticationService() {
    }

    public static int getNumberOfUsersRegistered() {
        return userRepository.countUsers();
    }

    public static User getDataFromCertificate(User user, String path) {
        try {
            Map<String, String> data = getInstance.extractCertificateOwnerInfo(path, true);
            user.setName(data.get("CN"));
            user.setEmail(data.get("EMAILADDRESS"));
            user.setCertificate(data.get("CERTIFICATE"));
            return user;
        } catch (InvalidExtractionCertificateOwnerInfoException e) {
            // todo log
            return null;
        } catch (Exception e) {
            //todo log
            System.out.println("Ocorreu um erro ao buscar informações do certificado: " + e.getMessage());
            return null;
        }
    }

    public boolean checkEmail(String email) {
        //todo log
        if (this.loggedUser != null) {
            System.out.println("Já existe um usuário logado");
            return false;
        } else if (email.trim().length() == 0) {
            System.out.println("Email em branco");
            return false;
        } else {
            User user = userRepository.getUser(email);
            if (user != null)
                return true;
        }
        return false;
    }

    public void makeUserLogged(String email, String path, String secret) {
        try {
            //todo log
            this.loggedUser = userRepository.getUser(email);
            this.loggedUser.setTotalAccess(this.loggedUser.getTotalAccess() + 1);
            this.loggedUser.setPvtKey(keyService.loadPrivateKey(path, secret));
            this.loggedUser.setPbcKey(keyService.loadPublicKey(getInstance.loadCertificate(this.loggedUser.getCertificate(), false)));
            userRepository.updateTotalAccess(this.loggedUser.getId(), this.loggedUser.getTotalAccess());
            userRepository.updateUser(this.loggedUser);
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao autenticar o usuário: " + e.getMessage());
        }
    }

    public void updateNumberConsult() {
        this.loggedUser.setTotalConsults(this.loggedUser.getTotalConsults() + 1);
        userRepository.updateTotalConsults(this.loggedUser.getId(), this.getLoggedUser().getTotalConsults());
    }

    public void registerUser(String certificatePath, String group, String password) {
        //todo log
        try {
            String salt = this.saltGenerator();
            String encryptedPw = PasswordCipherService.getInstance().encryptPassword(password + salt);
            User user = new User(encryptedPw, encryptedPw, group, certificatePath, 0, 0);
            user.setSalt(salt);
            String errors = this.verifyFields(user);

            user = AuthenticationService.getDataFromCertificate(user, certificatePath);
            System.out.println(" authenticationservice" + user.getCertificate());
            if (errors != "") {
                throw new Exception(errors);
            }
            if (userRepository.getUser(user.getEmail()) != null) {
                throw new Exception("Usuário " + user.getEmail() + " já existe");
            }
            if (!user.getPassword().equals(user.getPasswordConfirmation())) {
                throw new Exception("Senhas não coincidem");
            }
            userRepository.createUser(user);
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao registrar o usuário: " + e.getMessage());
        }
    }

    public void updateUser(String certificatePath, String password, String passwordConfirmation) {
        try {
            this.loggedUser.setCertificatePath(certificatePath);
            this.loggedUser = AuthenticationService.getDataFromCertificate(this.loggedUser, certificatePath);
            this.loggedUser.setPassword(password + this.loggedUser.getSalt());
            this.loggedUser.setPasswordConfirmation(passwordConfirmation + this.loggedUser.getSalt());
            String errors = this.verifyFields(this.loggedUser);
            if (errors != "") {
                throw new Exception(errors);
            }

            if (!this.loggedUser.getPassword().equals(this.loggedUser.getPasswordConfirmation())) {
                throw new Exception("Senhas não coincidem");
            }

            if (this.loggedUser.getPassword() == null || this.loggedUser.getPasswordConfirmation() == null || this.loggedUser.getCertificatePath() == null) {
                throw new Exception("Campos em branco");
            }
            userRepository.updateUser(this.loggedUser);
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao atualizar o usuário: " + e.getMessage());
        }
    }

    public User findUser(String email) {
        try {
            if (this.loggedUser != null && this.loggedUser.getEmail().equals(email)) {
                return this.loggedUser;
            } else if (email != null && email.length() > 0) {
                return userRepository.getUser(email);
            }
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao buscar o usuário: " + e.getMessage());
        }
        return null;
    }

    public void logout() {
        this.loggedUser = null;
    }

    public User getLoggedUser() {
        return this.loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public boolean verifyIsLocked(String email) {
        //todo log
        try {
            LocalDateTime localDateTime = LocalDateTime.now();
            LockedUser lockedUser = LockedUserRepository.getInstance().getLockedUser(email);

            if (lockedUser != null) {
                LocalDateTime lockDate = lockedUser.getLockDate();
                System.out.println(localDateTime.toString() + " " + lockDate.toString());
                if (LocalDateTime.parse(LocalDateTime.now().toString()).minusMinutes(2).compareTo(lockedUser.getLockDate()) < 0) {
                    return true;
                } else {
                    this.unlockUser(email);
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao verificar se o usuário está bloqueado: " + e.getMessage());
            return false;
        }
    }

    public void lockUser(String email) {
        //todo log
        User user = userRepository.getUser(email);
        LockedUserRepository.getInstance().createLockedUser(user);
    }

    public void unlockUser(String email) {
        //todo log
        LockedUserRepository.getInstance().updateLockedUser(email, false);
    }

    // Gera salt de 10 caracteres alfa numéricos
    public String saltGenerator() {
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String salt = "";
        Random rand = new Random();

        int upperbound = alphabet.length();

        for (int i = 0; i < 10; i++) {
            int int_random = rand.nextInt(upperbound);

            salt += alphabet.substring(int_random, int_random + 1);
        }

        return salt;
    }

    public String verifyFields(User user) {
        String errors = "";
        if (user.getPassword() == null || user.getPassword() == "") {
            errors += "Senha não inserida\n";
        }
        if (user.getPasswordConfirmation() == null || user.getPasswordConfirmation() == "") {
            errors += "Confirmação da senha não inserida\n";
        }
        if (user.getGroup() == null || user.getGroup() == "") {
            errors += "Grupo não inserido";
        }
        if (user.getCertificatePath() == null || user.getCertificatePath() == "") {
            errors += "Caminho do certificado não inserido\n";
        }
        return errors;
    }

    // Testando todas as combinações de senhas possíveis para verificar se é correta
    public boolean verifyPassword(List<int[]> typedPw, String email) {
        try {
            User user = this.findUser(email);

            String currentPw = "";
            int[] index = new int[9];

            for (index[0] = 0; index[0] < 2; index[0]++) {
                for (index[1] = 0; index[1] < 2; index[1]++) {
                    for (index[2] = 0; index[2] < 2; index[2]++) {
                        for (index[3] = 0; index[3] < 2; index[3]++) {
                            for (index[4] = 0; index[4] < 2; index[4]++) {
                                for (index[5] = 0; index[5] < 2; index[5]++) {
                                    for (index[6] = 0; index[6] < 2; index[6]++) {
                                        for (index[7] = 0; index[7] < 2; index[7]++) {
                                            for (index[8] = 0; index[8] < 2; index[8]++) {
                                                for (int i = 0; i < typedPw.size(); i++) {
                                                    currentPw += typedPw.get(i)[index[i]];
                                                }

                                                String value = PasswordCipherService.getInstance().encryptPassword(currentPw + user.getSalt());
                                                if (value.equals(user.getPassword())) {
                                                    return true;
                                                }

                                                currentPw = "";
                                                if (typedPw.size() < 9) {
                                                    break;
                                                }
                                            }
                                            if (typedPw.size() < 8) {
                                                break;
                                            }
                                        }
                                        if (typedPw.size() < 7) {
                                            break;
                                        }

                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao validar a senha do usuário: " + e.getMessage());
            return false;
        }
    }

    public boolean keysValidation(String email, String path, String secret) {
        try {
            User user = this.findUser(email);
            X509Certificate cert = getInstance.loadCertificate(user.getCertificate(), false);
            return keyService.verifyKeyPairIntegrity(keyService.loadPublicKey(cert), keyService.loadPrivateKey(path, secret));
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao validar a chave privadas do usuário: " + e.getMessage());
            return false;
        }
    }

    // Verifica se a senha tem entre tem entre 6 e 8 caracteres alfa numéricos, como especificado
    public boolean validatePassword(String pw) {
        for (int i = 0; i < pw.length() - 1; i++) {
            if (pw.charAt(i) == pw.charAt(i + 1) || pw.charAt(i) == pw.charAt(i + 1) + 1 || pw.charAt(i) == pw.charAt(i + 1) - 1) {
                return false;
            }
        }
        return true;
    }
}
