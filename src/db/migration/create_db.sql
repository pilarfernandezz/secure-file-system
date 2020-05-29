create database secure_file_system;

use secure_file_system;

create table user_groups(
    id int not null auto_increment,
    name varchar(100) NOT NULL,
    primary key(id)
)

Create table users (
	id int NOT NULL AUTO_INCREMENT,
	email varchar (100) unique not null,
	password varchar(255) not null,
	name varchar (100) not null,
	user_group_id int not null,
	certificate varchar(5000) not null,
	salt varchar(10) not null,
	total_access int not null default 0,
	total_consults int not null default 0,
	foreign key (user_group_id) references user_group(id),

	primary key(id)
);

Create table locked_users (
	id int NOT NULL AUTO_INCREMENT,
	user_id int not null,
	lock_date Datetime not null default now(),
	is_active tinyint not null default 0,
	foreign key (user_id) references users(id),
	primary key(id)
);

Create table messages (
	id int not null,
	message varchar(255) not null,
	primary key(id)
);

create table registers(
    id int not null,
    code int not null,
    login varchar(100),
    arq varchar(255),
    creation_datetime Datetime not null default now(),
    foreign key (code) references message(id),
    primary key(id)
)

insert into user_groups (id,name) values (1, "Administrador");
insert into user_groups (id,name) values (2, "Usuário");

Insert into users (id, email, password, name, user_group, certificate, salt) values ( 1, 'admin@inf1416.puc-rio.br',  'BB3387D1B9C6F309244A704F2CC65DCAFB70C3C5' , 'Administrador', 1, "-----BEGIN CERTIFICATE-----\nMIID9jCCAt6gAwIBAgIBATANBgkqhkiG9w0BAQsFADCBhDELMAkGA1UEBhMCQlIx\nCzAJBgNVBAgMAlJKMQwwCgYDVQQHDANSaW8xDDAKBgNVBAoMA1BVQzEQMA4GA1UE\nCwwHSU5GMTQxNjETMBEGA1UEAwwKQUMgSU5GMTQxNjElMCMGCSqGSIb3DQEJARYW\nY2FAZ3JhZC5pbmYucHVjLXJpby5icjAeFw0xOTA1MDMxNzE5MjhaFw0yMjA1MDIx\nNzE5MjhaMHsxCzAJBgNVBAYTAkJSMQswCQYDVQQIDAJSSjEMMAoGA1UECgwDUFVD\nMRAwDgYDVQQLDAdJTkYxNDE2MRYwFAYDVQQDDA1BZG1pbmlzdHJhdG9yMScwJQYJ\nKoZIhvcNAQkBFhhhZG1pbkBpbmYxNDE2LnB1Yy1yaW8uYnIwggEiMA0GCSqGSIb3\nDQEBAQUAA4IBDwAwggEKAoIBAQDDnq2WpTioReNQ3EapxCdmUt9khsS2BHf/YB7t\njGILCzQegnV1swvcH+xfd9FUjR7pORFSNvrfWKt93t3l2Dc0kCvVffh5BSnXIwwb\nW94O+E1Yp6pvpyflj8YI+VLy0dNCiszHAF5ux6lRZYcrM4KiJndqeFRnqRP8zWI5\nO1kJJMXzCqIXwmXtfqVjWiwXTnjU97xfQqKkmAt8Z+uxJaQxdZJBczmo/jQAIz1g\nx+SXA4TshU5Ra4sQYLo5+FgAfA2vswHGXA6ba3N52wydZ2IYUJL2/YmTyfxzRnsy\nuqbL+hcOw6bm+g0OEIIC7JduKpinz3BieiO15vameAJlqpedAgMBAAGjezB5MAkG\nA1UdEwQCMAAwLAYJYIZIAYb4QgENBB8WHU9wZW5TU0wgR2VuZXJhdGVkIENlcnRp\nZmljYXRlMB0GA1UdDgQWBBSeUNmquC0OBxDLGpUaDNxe1t2EADAfBgNVHSMEGDAW\ngBQjgTvDGSuVmdnK6jtr/hwkc8KCjjANBgkqhkiG9w0BAQsFAAOCAQEAYjji1ws7\n7cw8uVhlUTkzVxyAaUKOgJx2zuvhR79MItH7L+7ocDrMB/tGCgoAhAM1gVeuyP2t\n0j9mmRuuFDEFvsFqmOoSDbLFkxr1G8StujUQDrLe+691qU5RNubP3XacRyPVTA1F\n/pSr/XUm4fymqDZyVcxqYPFewhQlL3VaD2bKeNWEAczgkOHkC3dDb9bCL4oDr1Ss\nURKDWWg2XbZpuTO7IhxTYKwddKvsJTjizHIz6mi6JavHM7+xtB/ZvQaW04O9y5QI\n9EQPJsF3nybVNKWIR9UA4tWSfHmQ5J9cGk/bZBCqzvgmV8Wv7cMUB7q6mzGUP1a+\nHtNmSvQW9Uow3g==\n-----END CERTIFICATE-----\n", 'jj9aE8ZrWJ' );

Insert into messages (id, message) values (1001, "Sistema iniciado.");
Insert into messages (id, message) values (1002, "Sistema encerrado");
Insert into messages (id, message) values (2001, "Autenticação etapa 1 iniciada.");
Insert into messages (id, message) values (2002, "Autenticação etapa 1 encerrada.");
Insert into messages (id, message) values (2003, "Login name <login_name> identificado com acesso liberado.");
Insert into messages (id, message) values (2004, "Login name <login_name> identificado com acesso bloqueado.");
Insert into messages (id, message) values (2005, "Login name <login_name> não identificado.");
Insert into messages (id, message) values (3001, "Autenticação etapa 2 iniciada para <login_name>");
Insert into messages (id, message) values (3002, "Autenticação etapa 2 encerrada para <login_name>");
Insert into messages (id, message) values (3003, "Senha pessoal verificada positivamente para <login_name>");
Insert into messages (id, message) values (3004, "Primeiro erro da senha pessoal contabilizado para <login_name> .");
Insert into messages (id, message) values (3005, "Segundo erro da senha pessoal contabilizado para <login_name> .");
Insert into messages (id, message) values (3006, "Terceiro erro da senha pessoal contabilizado para <login_name> .");
Insert into messages (id, message) values (3007, "Acesso do usuario <login_name> bloqueado pela autenticação etapa 2.");
Insert into messages (id, message) values (4001, "Autenticação etapa 3 iniciada para <login_name>.”);
Insert into messages (id, message) values (4002, "Autenticação etapa 3 encerrada para <login_name>.");
Insert into messages (id, message) values (4003, "Chave privada verificada positivamente para <login_name>");
Insert into messages (id, message) values (4004, "Chave privada verificada negativamente para <login_name> (caminho inválido)");
Insert into messages (id, message) values (4005, "Chave privada verificada negativamente para <login_name> (frase secreta inválida)");
Insert into messages (id, message) values (4006, "Chave privada verificada negativamente para <login_name> (assinatura digital inválida).");
Insert into messages (id, message) values (4007, "Acesso do usuario <login_name> bloqueado pela autenticação etapa 3.");
Insert into messages (id, message) values (5001, "Tela principal apresentada para <login_name>.”);
Insert into messages (id, message) values (5002, "Opção 1 do menu principal selecionada por <login_name>.");
Insert into messages (id, message) values (5003, "Opção 2 do menu principal selecionada por <login_name>.");
Insert into messages (id, message) values (5004, "Opção 3 do menu principal selecionada por <login_name>.");
Insert into messages (id, message) values (5005, "Opção 4 do menu principal selecionada por <login_name>.”);
Insert into messages (id, message) values (6001, "Tela de cadastro apresentada para <login_name>.");
Insert into messages (id, message) values (6002, "Botão cadastrar pressionado por <login_name>");
Insert into messages (id, message) values (6003, "Senha pessoal inválida fornecida por <login_name>.");
Insert into messages (id, message) values (6004, "Caminho do certificado digital inválido fornecido por <login_name>.");
Insert into messages (id, message) values (6005, "Confirmação de dados aceita por <login_name>. ");
Insert into messages (id, message) values (6006, "Confirmação de dados rejeitada por <login_name>..");
Insert into messages (id, message) values (6007, "Botão voltar de cadastro para o menu principal pressionado por <login_name>.");
Insert into messages (id, message) values (7001, "Tela de alteração da senha pessoal e certificado apresentada para <login_name>.");
Insert into messages (id, message) values (7002, "Senha pessoal inválida fornecida por <login_name>.");
Insert into messages (id, message) values (7003, "Caminho do certificado digital inválido fornecido por <login_name>.");
Insert into messages (id, message) values (7004, "Confirmação de dados aceita por <login_name>.");
Insert into messages (id, message) values (7005, "Confirmação de dados rejeitada por <login_name>.");
Insert into messages (id, message) values (7006, "Botão voltar de carregamento para o menu principal pressionado por  <login_name>.");
Insert into messages (id, message) values (8001, "Tela de consulta de arquivos secretos apresentada para  <login_name>.");
Insert into messages (id, message) values (8002, "Botão voltar de consulta para o menu principal pressionado por <login_name>.");
Insert into messages (id, message) values (8003, "Botão Listar de consulta pressionado por <login_name>.");
Insert into messages (id, message) values (8004, "Caminho de pasta inválido fornecido por <login_name>.");
Insert into messages (id, message) values (8005, "Arquivo de índice decriptado com sucesso para <login_name>. ");
Insert into messages (id, message) values (8006, "Arquivo de índice verificado (integridade e autenticidade) com sucesso para <login_name>.");
Insert into messages (id, message) values (8007, "Falha na decriptação do arquivo de índice para <login_name>.“);
Insert into messages (id, message) values (8008, "Falha na verificação (integridade e autenticidade) do arquivo de índice para <login_name>.");
Insert into messages (id, message) values (8009, "Lista de arquivos presentes no índice apresentada para <login_name>.”);
Insert into messages (id, message) values (8010, "Arquivo<arq_name> selecionado por <login_name> para decriptação. ");
Insert into messages (id, message) values (8011, "Acesso permitido ao arquivo <arq_name> para <login_name>.");
Insert into messages (id, message) values (8012, "Acesso negado ao arquivo <arq_name> para <login_name>.");
Insert into messages (id, message) values (8013, "Arquivo <arq_name> decriptado com sucesso para <login_name>.");
Insert into messages (id, message) values (8014, "Arquivo <arq_name> verificado (integridade e autenticidade) com sucesso para <login_name>.");
Insert into messages (id, message) values (8015, "Falha na decriptação do arquivo <arq_name> para <login_name>.”);
Insert into messages (id, message) values (8016, "Falha na verificação (integridade e autenticidade) do arquivo <arq_name> para <login_name> .");
insert into messages (id, message) values (9001, "Tela de saída apresentada para <login_name>.”);
insert into messages (id, message) values (9002, "Saída não liberada por falta de one-time password para <login_name>.”);
insert into messages (id, message) values (9003, "Botão sair pressionado por <login_name>.");
Insert into messages (id, message) values (9004, "Botão voltar de sair para o menu principal pressionado por <login_name>.");



