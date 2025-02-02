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

/*povoamento*/
INSERT INTO ESTOQUE_PRODUTO (nome, descricao, preco, quantidade_estoque) VALUES
('Arroz', 'Pacote de 5kg', 25.00, 100),
('Feijão', 'Pacote de 1kg', 8.50, 100),
('Óleo de Soja', 'Garrafa de 900ml', 10.00, 50),
('Macarrão', 'Pacote de 500g', 5.00, 80),
('Açúcar', 'Pacote de 2kg', 7.00, 90),
('Café', 'Pacote de 500g', 15.00, 60),
('Sal', 'Pacote de 1kg', 3.00, 100),
('Farinha de Trigo', 'Pacote de 1kg', 6.50, 70),
('Leite', 'Caixa de 1L', 4.80, 50),
('Manteiga', 'Pote de 500g', 12.00, 30),
('Refrigerante', 'Garrafa de 2L', 9.00, 40),
('Biscoito', 'Pacote de 300g', 4.50, 60),
('Chocolate', 'Barra de 150g', 7.50, 50),
('Sabão em pó', 'Pacote de 1kg', 14.00, 40),
('Amaciante', 'Frasco de 2L', 18.00, 30),
('Shampoo', 'Frasco de 400ml', 22.00, 30),
('Condicionador', 'Frasco de 400ml', 22.00, 30),
('Papel Higiênico', 'Pacote com 12 rolos', 15.00, 50),
('Detergente', 'Frasco de 500ml', 3.50, 60),
('Esponja', 'Pacote com 4 unidades', 5.50, 70),
('Carne bovina', '1kg', 40.00, 25),
('Frango', '1kg', 18.00, 30),
('Peixe', '1kg', 35.00, 20),
('Ovos', 'Dúzia', 14.00, 40),
('Queijo', '500g', 25.00, 30),
('Presunto', '500g', 18.00, 30),
('Margarina', 'Pote de 500g', 8.00, 40),
('Iogurte', 'Pote de 170g', 3.80, 60),
('Suco de laranja', 'Caixa de 1L', 6.50, 50),
('Água mineral', 'Garrafa de 1,5L', 2.80, 80);

INSERT INTO ESTOQUE_VENDA (data_venda, forma_pagamento, valor_total, id_cliente) VALUES
('2025-01-10', 'Cartão de Crédito', 80.00, 1),
('2025-01-11', 'Dinheiro', 78.50, 2),
('2025-01-12', 'Cartão de Débito', 50.00, 3),
('2025-01-13', 'Pix', 99.00, 4),
('2025-01-14', 'Cartão de Crédito', 93.50, 5),
('2025-01-15', 'Dinheiro', 82.00, 6),
('2025-01-16', 'Pix', 86.00, 7),
('2025-01-17', 'Cartão de Débito', 63.00, 8),
('2025-01-18', 'Cartão de Crédito', 112.50, 9),
('2025-01-19', 'Dinheiro', 47.50, 10);

INSERT INTO ESTOQUE_ITEM_VENDA (quantidade, preco_unitario, subtotal, id_venda, id_produto) VALUES
(2, 25.00, 50.00, 1, 1),
(3, 10.00, 30.00, 1, 3),
(5, 8.50, 42.50, 2, 2),
(2, 18.00, 36.00, 2, 22),
(1, 40.00, 40.00, 3, 21),
(2, 5.00, 10.00, 3, 4),
(5, 15.00, 75.00, 4, 6),
(2, 12.00, 24.00, 4, 10),
(3, 22.00, 66.00, 5, 16),
(5, 5.50, 27.50, 5, 20),
(2, 14.00, 28.00, 6, 24),
(3, 18.00, 54.00, 6, 22),
(4, 9.00, 36.00, 7, 11),
(2, 25.00, 50.00, 7, 25),
(2, 4.50, 9.00, 8, 12),
(3, 18.00, 54.00, 8, 26),
(5, 6.50, 32.50, 9, 8),
(2, 40.00, 80.00, 9, 21),
(3, 6.50, 19.50, 10, 29),
(2, 14.00, 28.00, 10, 24);

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