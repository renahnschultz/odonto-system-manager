INSERT INTO `dente` 
VALUES 
	(11,'Dente 11','11.png',8),
	(12,'Dente 12','12.png',7),
	(13,'Dente 13','13.png',6),
	(14,'Dente 14','14.png',5),
	(15,'Dente 15','15.png',4),
	(16,'Dente 16','16.png',3),
	(17,'Dente 17','17.png',2),
	(18,'Dente 18','18.png',1),
	(21,'Dente 21','21.png',9),
	(22,'Dente 22','22.png',10),
	(23,'Dente 23','23.png',11),
	(24,'Dente 24','24.png',12),
	(25,'Dente 25','25.png',13),
	(26,'Dente 26','26.png',14),
	(27,'Dente 27','27.png',15),
	(28,'Dente 28','28.png',16),
	(31,'Dente 31','31.png',25),
	(32,'Dente 32','32.png',26),
	(33,'Dente 33','33.png',27),
	(34,'Dente 34','34.png',28),
	(35,'Dente 35','35.png',29),
	(36,'Dente 36','36.png',30),
	(37,'Dente 37','37.png',31),
	(38,'Dente 38','38.png',32),
	(41,'Dente 41','41.png',24),
	(42,'Dente 42','42.png',23),
	(43,'Dente 43','43.png',22),
	(44,'Dente 44','44.png',21),
	(45,'Dente 45','45.png',20),
	(46,'Dente 46','46.png',19),
	(47,'Dente 47','47.png',18),
	(48,'Dente 48','48.png',17);


INSERT INTO `permissao` 
VALUES 
	(1,'administrador','administrador','administrador',NULL),
	(2,'visualizar.servicos','visualizar-servicos','servicos',NULL),
	(3,'editar.servicos','editar-servicos','servicos',NULL),
	(4,'excluir.servicos','excluir-servicos','servicos',NULL),
	(5,'visualizar.materiais','visualizar-materiais','materiais',NULL),
	(6,'editar.materiais','editar-materiais','materiais',NULL),
	(7,'excluir.materiais','excluir-materiais','materiais',NULL),
	(8,'visualizar.usuarios','visualizar-usuarios','usuarios',NULL),
	(9,'editar.usuarios','editar-usuarios','usuarios',NULL),
	(10,'excluir.usuarios','excluir-usuarios','usuarios',NULL),
	(11,'cadastrar.pergunta.anamnese','cadastrar-perguntas','pergunta.anamnese','administrador'),
	(12,'editar.pergunta.anamnese','editar-perguntas','pergunta.anamnese','administrador'),
	(13,'excluir.pergunta.anamnese','excluir-perguntas','pergunta.anamnese','administrador'),
	(14,'cadastrar.anamnese','cadastrar-anamnese','anamnese','paciente, odontologo'),
	(15,'editar.anamnese','editar-anamnese','anamnese','paciente, odontologo');	
    
    

INSERT INTO `usuario` 
VALUES 
	(1,'Administador','','admin@osm.com','2018-01-01','111.111.111-11',4,1,'admin','+554133333333','+55419999999',NULL);
    

INSERT INTO `usuario_has_permissao` 
VALUES 
	(1,1);
    
    
INSERT INTO `pergunta` VALUES 
	(1,'Está tomando algum medicamento?',1,1,1),
	(2,'Tem algum tipo de alergia?',1,1,1),
	(3,'Sua pressão é: (Alta/Normal/Baixa)',0,0,1),
	(4,'Tem ou teve algum problema de coração?',1,0,1),
	(5,'Sente falta de ar com frequência?',1,0,1),
	(6,'Tem diabetes?',1,0,1),
	(7,'Quando se corta há um sangramento: (Normal/Excessivo)',0,0,1),
	(8,'Sua cicatrização é: (Normal/Complicada)',0,0,1),
	(9,'Já fez alguma cirurgia?',1,0,1),
	(10,'Gestante?',1,1,1),
	(11,'Problemas de saúde que já teve:',0,0,1),
	(12,'Já teve alguma reação com anestesia dental?',1,1,1),
	(13,'Quando foi seu último tratamento dentário?',0,0,1),
	(14,'Tem sentido alguma dor nos dentes ou na gengiva?',1,0,1),
	(15,'Sua gengiva sangra?',1,1,1),
	(16,'Tem sentido gosto ruim na boca ou boca seca?',1,0,1),
	(17,'Quantas vezes escova os dentes por dia?',0,0,1),
	(18,'Usa fio dental?',1,0,1),
	(19,'Sente dores ou estalos no maxilar ou no ouvido?',1,0,1),
	(20,'Range os dentes de dia ou de noite?',1,0,1),
	(21,'Já teve alguma ferida ou bolha na face ou nos lábios?',1,0,1),
	(22,'Fuma?',1,1,1);