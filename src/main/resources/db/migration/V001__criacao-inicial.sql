CREATE TABLE `pessoa` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `idade_pessoa` int DEFAULT NULL,
  `nome_pessoa` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;