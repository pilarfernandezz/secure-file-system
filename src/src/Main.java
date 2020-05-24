import facade.Facade;

public class Main {
    public static void main(String args[]) {
        Facade.getFacadeInstance().start();
    }
}

//Insert into users (id, email, password, name, user_group, certificate, salt) values ( 2, 'aa',  '1234561010101010' , 'pilar','user', 'certificado','1010101010' );
//Insert into users (id, email, password, name, user_group, certificate, salt) values ( 1, 'aa',  '1234561010101010' , 'pilar','administrator', 'certificado','1010101010' );
//Insert into locked_users (id, user_id , isActive) values (2, 1, 1 );

//deixar o usuario travado por 2 minutos
//ajustar a senha
//ajeitar a tela de consulta
//criptografar a senha e o salt antes de colocar no banco
//pegar as info do certificado

