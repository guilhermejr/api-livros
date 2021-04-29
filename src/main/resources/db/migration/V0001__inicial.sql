
    create table autores (
		id bigint not null auto_increment,
        descricao varchar(255) not null,
        criado datetime not null,
        primary key (id)
    ) engine=InnoDB default charset=utf8mb4;

    create table editoras (
		id bigint not null auto_increment,
        descricao varchar(255) not null,
        criado datetime not null,
        primary key (id)
    ) engine=InnoDB default charset=utf8mb4;

    create table estantes (
		id bigint not null auto_increment,
        descricao varchar(255) not null,
        criado datetime not null,
        primary key (id)
    ) engine=InnoDB default charset=utf8mb4;

    create table generos (
		id bigint not null auto_increment,
        descricao varchar(255) not null,
        criado datetime not null,
        primary key (id)
    ) engine=InnoDB default charset=utf8mb4;

    create table idiomas (
		id bigint not null auto_increment,
        descricao varchar(255) not null,
        criado datetime not null,
        primary key (id)
    ) engine=InnoDB default charset=utf8mb4;

    create table livros (
		id bigint not null auto_increment,
        capa varchar(255) not null,
        extensao varchar(5) not null,
		titulo varchar(255) not null,
		subtitulo varchar(255),
        isbn varchar(13) not null,
        descricao TEXT not null,
        ano_publicacao integer not null,
        paginas integer not null,
        criado datetime,
		atualizado datetime,
		ativo bit not null,
        editora_id bigint,
        estante_id bigint,
        idioma_id bigint,
        tipo_id bigint,
        usuario_id bigint,
        primary key (id)
    ) engine=InnoDB default charset=utf8mb4;

    create table livros_autores (
		livro_id bigint not null,
        autores_id bigint not null
    ) engine=InnoDB default charset=utf8mb4;

    create table livros_generos (
		livro_id bigint not null,
        generos_id bigint not null
    ) engine=InnoDB default charset=utf8mb4;

    create table perfis (
		id bigint not null auto_increment,
        descricao varchar(255) not null,
        criado datetime not null,
        primary key (id)
    ) engine=InnoDB default charset=utf8mb4;

    create table tipos (
		id bigint not null auto_increment,
        descricao varchar(255) not null,
        criado datetime not null,
        primary key (id)
    ) engine=InnoDB default charset=utf8mb4;

    create table usuarios (
		id bigint not null auto_increment,
		nome varchar(255) not null,
        email varchar(255) not null,
        senha varchar(255) not null,
        ativo bit not null,
        criado datetime not null,
        atualizado datetime not null,
        ultimo_acesso datetime,
        primary key (id)
    ) engine=InnoDB default charset=utf8mb4;

    create table usuarios_perfis (
		usuario_id bigint not null,
        perfis_id bigint not null
    ) engine=InnoDB default charset=utf8mb4;

    alter table autores add constraint UK_autores unique (descricao);

    alter table editoras add constraint UK_editoras unique (descricao);

    alter table estantes add constraint UK_estantes unique (descricao);

    alter table generos add constraint UK_generos unique (descricao);

    alter table idiomas add constraint UK_idiomas unique (descricao);

    alter table livros add constraint UK_livros unique (isbn);

    alter table perfis add constraint UK_perfis unique (descricao);

    alter table tipos add constraint UK_tipos unique (descricao);

    alter table usuarios add constraint UK_usuarios unique (email);

    alter table livros add constraint FK_livros_editoras foreign key (editora_id) references editoras (id);

    alter table livros add constraint FK_livros_estantes foreign key (estante_id) references estantes (id);

    alter table livros add constraint FK_livros_idiomas foreign key (idioma_id) references idiomas (id);

    alter table livros add constraint FK_livros_tipos foreign key (tipo_id) references tipos (id);

    alter table livros add constraint FK_livros_usuarios foreign key (usuario_id) references usuarios (id);

    alter table livros_autores add constraint FK_livros_autores_autores foreign key (autores_id) references autores (id);

    alter table livros_autores add constraint FK_livros_autores_livros foreign key (livro_id) references livros (id);

    alter table livros_generos add constraint FK_livros_generos_generos foreign key (generos_id) references generos (id);

    alter table livros_generos add constraint FK_livros_generos_livros foreign key (livro_id) references livros (id);

    alter table usuarios_perfis add constraint FK_usuarios_perfis_perfis foreign key (perfis_id) references perfis (id);

    alter table usuarios_perfis add constraint FK_usuarios_perfis_usuarios foreign key (usuario_id) references usuarios (id);
	   
	insert into estantes (descricao, criado) VALUES ('Biblioteca', NOW());
	insert into estantes (descricao, criado) VALUES ('Desejados', NOW());
	insert into estantes (descricao, criado) VALUES ('Doação', NOW());
	
	insert into perfis (descricao, criado) VALUES ('ROLE_USUARIO', NOW());
	
	insert into tipos (descricao, criado) VALUES ('Físico', NOW());
	insert into tipos (descricao, criado) VALUES ('E-book', NOW());
	insert into tipos (descricao, criado) VALUES ('PDF', NOW());
	
	insert into usuarios (nome, email, senha, ativo, criado, atualizado, ultimo_acesso) VALUES ('Guilherme Jr.', 'falecom@guilhermejr.net', '$2a$10$iyH3auB6TnNAb2oyfaedHeyhW0i27142VuFUGlzQqNPPnqLQRW1.2', b'1', NOW(), NOW(), NULL);
	
	insert into usuarios_perfis (usuario_id, perfis_id) VALUES (1, 1);
