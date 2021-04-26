CREATE TABLE empresa (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	cnpj varchar(255) NOT NULL,
	data_atualizacao datetime NOT NULL,
	data_criacao datetime NOT NULL,
	razao_social VARCHAR(255) NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDb DEFAULT CHARSET=utf8;

CREATE TABLE funcionario (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	cpf varchar(255) NOT NULL,
	data_atualizacao datetime NOT NULL,
	data_criacao datetime NOT NULL,
	nome VARCHAR(255) NOT NULL,
	email VARCHAR(255) NOT NULL,
	perfil VARCHAR(255) NOT NULL,
	qtd_horas_trabalhadas_dia float DEFAULT NULL,
	qtd_horas_almoco float DEFAULT NULL,
	senha VARCHAR(255) NOT NULL,
	valor_hora decimal(19,2) DEFAULT NULL,
	empresa_id bigint(20) DEFAULT NULL,
	PRIMARY KEY (id),
	CONSTRAINT fk_empresa FOREIGN KEY (empresa_id) REFERENCES empresa(id)
) ENGINE=InnoDb DEFAULT CHARSET=utf8;

CREATE TABLE lancamento (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	data datetime NOT NULL,
	data_atualizacao datetime NOT NULL,
	data_criacao datetime NOT NULL,
	descricao VARCHAR(255) DEFAULT NULL,
	localizacao VARCHAR(255) DEFAULT NULL,
	tipo VARCHAR(255) NOT NULL,
	funcionario_id bigint(20) DEFAULT NULL,
	PRIMARY KEY(id),
	CONSTRAINT fk_funcionario FOREIGN KEY (funcionario_id) references funcionario(id)
) ENGINE=InnoDb DEFAULT CHARSET=utf8;