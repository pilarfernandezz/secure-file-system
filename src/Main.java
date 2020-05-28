import facade.Facade;
import services.AuthenticationService;
import services.PasswordCipherService;
import views.ConsultView;
import views.EmailView;
import views.PvtKeyView;

import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;

public class Main {


    private static Facade facade;

    public static void main(String args[]) throws Exception {
//        String salt = AuthenticationService.getAuthenticationInstance().saltGenerator();
//
//        System.out.println( salt + " " +PasswordCipherService.getInstance().encryptPassword("159753" + salt));
//     //  new ConsultView();
     //   new EmailView();
        //new PvtKeyView();



      facade.start();
         //   new ConsultView();
//        byte[] index = facade.decryptFile("Keys/", "Files/", "user01", "index", false, null);
//        byte[] file1 = facade.decryptFile("Keys/", "Files/", "user01", "XXYYZZ11", true, IndexService.getInstance().getIndexInfo(index).get("XXYYZZ11"));
//        byte[] file2 = facade.decryptFile("Keys/", "Files/", "user01", "XXYYZZ22", true, IndexService.getInstance().getIndexInfo(index).get("XXYYZZ22"));
    }

    private static void setupAllBeforeStart() throws NoSuchPaddingException, NoSuchAlgorithmException {
        facade = Facade.getFacadeInstance();
    }
}

//Insert into users (id, email, password, name, user_group, certificate, salt) values ( 2, 'aa',  '1234561010101010' , 'pilar','user', 'certificado','1010101010' );
//Insert into users (id, email, password, name, user_group, certificate, salt) values ( 1, 'bb',  '1234561010101010' , 'pilar','Administrador', 'certificado','1010101010' );
//Insert into locked_users (id, user_id , isActive) values (2, 1, 1 );

//ajeitar a tela de consulta
//criptografar a senha e o salt antes de colocar no banco
//ajustar grupo para botao
//informações do cabeçalho nao marretadas
//adicionar registros de log

//total de acessos do usuário,, incrementar toda vez que o usuário logar no sistema
//total de usuários do sistema, incrementar toda vez que um novo usuário for cadastrado
//total de consultas do usuário, incrementar toda vez que o usuário clicar no botao consultas na ConsultView