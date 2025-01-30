create table if not exists ESTOQUE_VENDA(
    id_venda int auto_increment,
    data_venda date not null,
    forma_pagamento varchar(45) not null,
    valor_total double not null,
    id_cliente int not null,
    primary key(id_venda)
);

create table if not exists ESTOQUE_PRODUTO(
    id_produto int auto_increment,
    nome varchar(255) not null,
    descricao text not null,
    preco double not null,
    quantidade_estoque int not null,
    primary key(id_produto)
);

create table if not exists ESTOQUE_ITEM_VENDA(
    id_item_venda int auto_increment,
    quantidade int not null,
    preco_unitario double not null,
    subtotal double not null,
    id_venda int not null,
    id_produto int not null,
    primary key(id_item_venda),
    foreign key(id_venda) references ESTOQUE_VENDA(id_venda),
    foreign key(id_produto) references ESTOQUE_PRODUTO(id_produto) 
);