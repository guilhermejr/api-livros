    create table refresh_token (
    	id bigint not null auto_increment,
       	token char(32) not null,
       	expiracao datetime(6) not null,
        primary key (id)
    ) engine=InnoDB;