
import exceptions.InvalidCertificateException;
import exceptions.InvalidExtractionCertificateOwnerInfoException;
import facade.Facade;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class Main {
    public static void main(String args[]) throws FileNotFoundException, InvalidCertificateException, InvalidExtractionCertificateOwnerInfoException, SQLException {
        Facade.getFacadeInstance().start();
//        DigitalCertificateService digitalCertificateService = new DigitalCertificateService();
//        digitalCertificateService.extractCertificateOwnerInfo(digitalCertificateService.loadCertificate("Keys/admin-x509.crt"));
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