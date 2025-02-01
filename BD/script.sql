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

/*pro exemplo*/
INSERT INTO ESTOQUE_VENDA (id_venda, data_venda, forma_pagamento, valor_total, id_cliente)
VALUES
(11, '2025-02-01', 'Dinheiro', 126.00, 1);

INSERT INTO ESTOQUE_PRODUTO (id_produto, nome, preco, descricao, quantidade_estoque) VALUES (31, 'Maçã', 5.00, 'pacote de 1kg', 67);

INSERT INTO ESTOQUE_ITEM_VENDA (quantidade, preco_unitario, subtotal, id_venda, id_produto)
VALUES
(2, 5.00, 10.00, 11, 31);

INSERT INTO ESTOQUE_ITEM_VENDA (quantidade, preco_unitario, subtotal, id_venda, id_produto)
VALUES
(2, 35.00, 70.00, 11, 1);

INSERT INTO ESTOQUE_ITEM_VENDA (quantidade, preco_unitario, subtotal, id_venda, id_produto)
VALUES
(1, 12.00, 12.00, 11, 2);

UPDATE ESTOQUE_VENDA
SET valor_total = (SELECT SUM(subtotal) FROM ESTOQUE_ITEM_VENDA WHERE id_venda = 11)
WHERE id_venda = 11;