--adiciona coluna email ao tabela tb_users
ALTER TABLE tb_users ADD COLUMN email VARCHAR(255);

--prenche usuários antigos com um email ficticio
UPDATE tb_users SET email = login || '@email.com' WHERE email IS NULL;

--define email como não pode ser nulo
ALTER TABLE tb_users ALTER COLUMN email SET NOT NULL;

--define email como unico, adicionando uma regra a coluna
ALTER TABLE tb_users ADD CONSTRAINT uc_users_email UNIQUE (email);