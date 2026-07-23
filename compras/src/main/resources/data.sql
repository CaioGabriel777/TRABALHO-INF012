INSERT INTO compras (data_criacao, data_atualizacao, cliente_id, status, tipo_entrega, valor_total)
SELECT v.criacao::timestamp, NOW(), v.cli::bigint, v.status, v.entrega, v.total::numeric
FROM (VALUES
  (NOW() - INTERVAL '6 months', '1', 'CONCLUIDA',  'ENTREGA_DOMICILIO',   '899.90'),
  (NOW() - INTERVAL '5 months', '2', 'CONCLUIDA',  'RETIRADA_LOJA',       '539.80'),
  (NOW() - INTERVAL '5 months', '3', 'CONCLUIDA',  'ENTREGA_DOMICILIO',   '479.90'),
  (NOW() - INTERVAL '4 months', '1', 'CONCLUIDA',  'ENTREGA_DOMICILIO',  '2499.90'),
  (NOW() - INTERVAL '4 months', '4', 'CONCLUIDA',  'RETIRADA_LOJA',       '349.90'),
  (NOW() - INTERVAL '3 months', '5', 'CONCLUIDA',  'ENTREGA_DOMICILIO',   '469.80'),
  (NOW() - INTERVAL '3 months', '2', 'CONCLUIDA',  'RETIRADA_LOJA',       '159.90'),
  (NOW() - INTERVAL '2 months', '6', 'CONCLUIDA',  'ENTREGA_DOMICILIO',   '429.80'),
  (NOW() - INTERVAL '2 months', '3', 'CANCELADA',  'RETIRADA_LOJA',       '279.90'),
  (NOW() - INTERVAL '1 month',  '1', 'CONCLUIDA',  'ENTREGA_DOMICILIO',   '529.80'),
  (NOW() - INTERVAL '1 month',  '4', 'CONCLUIDA',  'RETIRADA_LOJA',       '399.90'),
  (NOW() - INTERVAL '3 weeks',  '5', 'CONFIRMADA', 'ENTREGA_DOMICILIO',   '209.80'),
  (NOW() - INTERVAL '2 weeks',  '6', 'CONFIRMADA', 'RETIRADA_LOJA',      '2499.90'),
  (NOW() - INTERVAL '1 week',   '2', 'PENDENTE',   'ENTREGA_DOMICILIO',   '149.90'),
  (NOW() - INTERVAL '2 days',   '3', 'PENDENTE',   'RETIRADA_LOJA',       '479.90')
) AS v(criacao, cli, status, entrega, total)
WHERE NOT EXISTS (SELECT 1 FROM compras LIMIT 1);

INSERT INTO itens_compra (data_criacao, data_atualizacao, compra_id, produto_id, quantidade, preco_unitario)
SELECT NOW(), NOW(), v.cid::bigint, v.pid::bigint, v.qtd::int, v.preco::numeric
FROM (VALUES
  ('1',  '1',  '1',  '899.90'),
  ('2',  '2',  '1',  '349.90'),
  ('2',  '3',  '1',  '189.90'),
  ('3',  '6',  '1',  '479.90'),
  ('4',  '7',  '1', '2499.90'),
  ('5',  '2',  '1',  '349.90'),
  ('6',  '4',  '1',  '279.90'),
  ('6',  '3',  '1',  '189.90'),
  ('7',  '8',  '1',  '159.90'),
  ('8',  '9',  '1',  '129.90'),
  ('8',  '10', '1',   '79.90'),
  ('8',  '11', '1',  '219.90'),
  ('9',  '4',  '1',  '279.90'),
  ('10', '3',  '1',  '189.90'),
  ('10', '11', '1',  '219.90'),
  ('10', '10', '1',  '119.90'),
  ('11', '5',  '1',  '399.90'),
  ('12', '8',  '1',  '159.90'),
  ('12', '10', '1',   '49.90'),
  ('13', '7',  '1', '2499.90'),
  ('14', '12', '1',  '149.90'),
  ('15', '6',  '1',  '479.90')
) AS v(cid, pid, qtd, preco)
WHERE NOT EXISTS (SELECT 1 FROM compras LIMIT 1);
