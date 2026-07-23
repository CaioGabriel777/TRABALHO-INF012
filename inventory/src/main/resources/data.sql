INSERT INTO categorias (data_criacao, data_atualizacao, nome)
SELECT NOW(), NOW(), v.nome
FROM (VALUES
  ('Eletrônicos'),
  ('Periféricos'),
  ('Acessórios'),
  ('Componentes')
) AS v(nome)
WHERE NOT EXISTS (SELECT 1 FROM categorias LIMIT 1);

INSERT INTO fornecedores (data_criacao, data_atualizacao, nome, cnpj, email, telefone)
SELECT NOW(), NOW(), v.nome, v.cnpj, v.email, v.telefone
FROM (VALUES
  ('TechSupply Ltda',   '11.222.333/0001-44', 'contato@techsupply.com',  '(71) 3001-1001'),
  ('ByteStore S.A.',    '22.333.444/0001-55', 'vendas@bytestore.com',    '(71) 3001-2002'),
  ('InfoParts ME',      '33.444.555/0001-66', 'info@infoparts.com',      '(71) 3001-3003'),
  ('MegaElec Distribu', '44.555.666/0001-77', 'mega@megaelec.com',       '(71) 3001-4004')
) AS v(nome, cnpj, email, telefone)
WHERE NOT EXISTS (SELECT 1 FROM fornecedores LIMIT 1);

INSERT INTO produtos (data_criacao, data_atualizacao, nome, descricao, preco, quantidade_estoque, estoque_minimo, ativo, categoria_id, fornecedor_id)
SELECT NOW(), NOW(), v.nome, v.descricao, v.preco::numeric, v.qtd::int, v.min::int, true, v.cat::bigint, v.forn::bigint
FROM (VALUES
  ('Monitor 24" Full HD',     'Monitor LED IPS 24 polegadas 1080p',          '899.90',  '15', '5',  '1', '1'),
  ('Teclado Mecânico RGB',    'Switch red, retroiluminação RGB completa',     '349.90',  '30', '8',  '1', '1'),
  ('Mouse Gamer 12000 DPI',   'Mouse óptico 12000 DPI com 7 botões',          '189.90',  '40', '10', '2', '2'),
  ('Headset Bluetooth 5.0',   'Fone sem fio com cancelamento de ruído',       '279.90',  '22', '6',  '2', '2'),
  ('SSD NVMe 500GB',          'Unidade de estado sólido PCIe Gen 4',          '399.90',  '18', '5',  '4', '3'),
  ('Memória RAM 16GB DDR5',   'Kit dual channel 6400MHz CL30',                '479.90',  '12', '4',  '4', '3'),
  ('Placa de Vídeo RTX 4060', 'GPU com 8GB GDDR6 e DLSS 3',                 '2499.90',   '8', '2',  '4', '4'),
  ('Webcam Full HD 1080p',    'Câmera USB com microfone embutido',            '159.90',  '25', '7',  '2', '1'),
  ('Hub USB-C 7-em-1',        'Hub USB-C com HDMI, USB 3.0 e SD Card',       '129.90',  '35', '10', '3', '2'),
  ('Mousepad XL',             'Mousepad gamer 900x400mm antiderrapante',       '79.90',  '50', '15', '3', '3'),
  ('Cabo HDMI 2.1 2m',        'Suporte 8K 60Hz e 4K 120Hz',                   '49.90',  '60', '20', '3', '4'),
  ('Cooler CPU 120mm',        'Cooler com 4 heat-pipes e iluminação ARGB',   '149.90',   '7', '3',  '4', '4')
) AS v(nome, descricao, preco, qtd, min, cat, forn)
WHERE NOT EXISTS (SELECT 1 FROM produtos LIMIT 1);

INSERT INTO movimentacoes_estoque (data_criacao, data_atualizacao, tipo, quantidade, motivo, produto_id)
SELECT v.criacao::timestamp, NOW(), v.tipo, v.qtd::int, v.motivo, v.prod::bigint
FROM (VALUES
  (NOW() - INTERVAL '5 months', 'ENTRADA', '50', 'Compra inicial de fornecedor',    '1'),
  (NOW() - INTERVAL '4 months', 'ENTRADA', '80', 'Reposição de estoque',            '3'),
  (NOW() - INTERVAL '3 months', 'SAIDA',   '10', 'Venda para cliente corporativo',  '1'),
  (NOW() - INTERVAL '2 months', 'ENTRADA', '20', 'Recebimento de NF-3312',          '7'),
  (NOW() - INTERVAL '1 month',  'SAIDA',    '5', 'Devolução com defeito',           '6'),
  (NOW() - INTERVAL '2 weeks',  'ENTRADA', '40', 'Promoção de fim de mês',          '2')
) AS v(criacao, tipo, qtd, motivo, prod)
WHERE NOT EXISTS (SELECT 1 FROM movimentacoes_estoque LIMIT 1);
