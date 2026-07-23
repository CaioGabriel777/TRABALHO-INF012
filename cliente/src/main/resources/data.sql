INSERT INTO clientes (data_criacao, data_atualizacao, nome, cpf, email, telefone)
SELECT v.criacao::timestamp, NOW(), v.nome, v.cpf, v.email, v.telefone
FROM (VALUES
  (NOW() - INTERVAL '6 months', 'Ana Souza',      '111.222.333-44', 'ana.souza@email.com',      '(71) 99001-1001'),
  (NOW() - INTERVAL '5 months', 'Bruno Lima',     '222.333.444-55', 'bruno.lima@email.com',     '(71) 99002-2002'),
  (NOW() - INTERVAL '4 months', 'Carla Mendes',   '333.444.555-66', 'carla.mendes@email.com',   '(71) 99003-3003'),
  (NOW() - INTERVAL '3 months', 'Diego Ferreira', '444.555.666-77', 'diego.ferreira@email.com', '(71) 99004-4004'),
  (NOW() - INTERVAL '2 months', 'Eduarda Costa',  '555.666.777-88', 'eduarda.costa@email.com',  '(71) 99005-5005'),
  (NOW() - INTERVAL '1 month',  'Felipe Ramos',   '666.777.888-99', 'felipe.ramos@email.com',   '(71) 99006-6006')
) AS v(criacao, nome, cpf, email, telefone)
WHERE NOT EXISTS (SELECT 1 FROM clientes LIMIT 1);

INSERT INTO enderecos (data_criacao, data_atualizacao, logradouro, numero, complemento, bairro, cidade, estado, cep, tipo, cliente_id)
SELECT NOW(), NOW(), v.log, v.num, v.comp, v.bairro, v.cidade, v.estado, v.cep, v.tipo, v.cli::bigint
FROM (VALUES
  ('Rua das Acácias',    '10',  NULL,    'Pituba',        'Salvador',          'BA', '40000-001', 'RESIDENCIAL', '1'),
  ('Av. Paralela',       '200', 'Ap 5',  'Paralela',      'Salvador',          'BA', '41000-002', 'RESIDENCIAL', '2'),
  ('Rua do Comércio',    '50',  'Sala 3','Centro',        'Feira de Santana',  'BA', '44000-003', 'COBRANCA',    '3'),
  ('Trav. São Jorge',    '30',  NULL,    'Liberdade',     'Salvador',          'BA', '40100-004', 'RESIDENCIAL', '4'),
  ('Blvd Sete de Abril', '99',  'Bl. B', 'Boca do Rio',  'Salvador',          'BA', '40200-005', 'RESIDENCIAL', '5'),
  ('Rua Nova',           '15',  NULL,    'Brotas',        'Salvador',          'BA', '40300-006', 'RESIDENCIAL', '6')
) AS v(log, num, comp, bairro, cidade, estado, cep, tipo, cli)
WHERE NOT EXISTS (SELECT 1 FROM enderecos LIMIT 1);
