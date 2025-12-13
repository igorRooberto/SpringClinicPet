🐾 Spring Clinic Pet API

⚠️ STATUS: EM DESENVOLVIMENTO (WIP)

Este projeto ainda está em fase de construção e aprimoramento. Novas funcionalidades, refatorações e correções estão sendo implementadas constantemente.

📖 Sobre o Projeto

O Spring Clinic Pet é uma API RESTful desenvolvida para o gerenciamento de uma clínica veterinária. O sistema permite o cadastro de tutores, veterinários e administradores, além de gerenciar o registro de animais (pets) e o agendamento de consultas.

🤝 Código Aberto & Comunidade

Este projeto é Open Source e está disponível para qualquer pessoa estudar, modificar e melhorar. A ideia é compartilhar conhecimento e criar uma base sólida para estudos de Spring Boot.

Sinta-se à vontade para fazer um Fork e enviar Pull Requests.

Encontrou um bug ou tem uma ideia? Abra uma Issue.

⭐ Gostou do projeto?
Se este código te ajudou de alguma forma ou se achou a arquitetura interessante, considere dar uma Estrela (Star) no repositório! Isso ajuda muito a divulgar o trabalho.

🧠 Arquitetura e Decisões Técnicas

Neste projeto, tomei algumas decisões focadas em produtividade, performance e manutenibilidade do código:

Lombok para Redução de Boilerplate:
Para evitar a verbosidade excessiva do Java, utilizei o Lombok nas minhas classes de modelo (como User e Pet). Com anotações como @Getter e @NoArgsConstructor, eliminei a necessidade de escrever métodos manuais repetitivos, deixando o código mais limpo e focado nas regras de negócio.

Autenticação JWT e Sessão Stateless:
Na camada de segurança (SecurityConfiguration), optei por usar Spring Security com JWT e configurei a sessão como STATELESS. Essa escolha foi feita para reduzir o consumo de memória do servidor, já que a API não precisa guardar o estado da sessão do usuário. Isso torna a aplicação muito mais escalável, pois cada requisição é validada independentemente através do token.

Spring Data JPA:
Para a persistência de dados, escolhi o Spring Data JPA. Isso facilitou a criação dos repositórios (PetRepository, UserRepository), eliminando a necessidade de escrever SQL puro para operações básicas de CRUD e agilizando o desenvolvimento.

DTOs (Data Transfer Objects) e Records:
Utilizei Java Records para criar DTOs (como RegisterDto e PetRequestDto). Essa separação protege as entidades de banco de dados e permite aplicar validações de entrada (Bean Validation, como @NotBlank) logo que os dados chegam na API, garantindo integridade antes do processamento.

Versionamento de Banco com Flyway:
Para garantir que o banco de dados seja criado corretamente em qualquer ambiente, utilizei o Flyway. O controle de versão do schema (arquivo V1__create_entites_table.sql) evita inconsistências estruturais no banco.

🚀 Tecnologias Utilizadas

Java 21

Spring Boot

Spring Security

Spring Data JPA

PostgreSQL

Flyway

Lombok

Swagger / OpenAPI

Maven

⚙️ Funcionalidades Principais
👤 Autenticação e Usuários

Registro de Usuários (Tutores), Veterinários e Admins.

Login com geração de Token JWT.

Admin: Ativar/Desativar usuários e aprovar veterinários pendentes.

🐶 Pets

Cadastrar novo Pet (Cães, Gatos, Aves, Répteis, etc.).

Listar Pets do próprio usuário.

Desativar/Remover Pet.

📅 Consultas (Appointments)

Agendamento de consultas por tutores.

Cancelamento de consultas.

Veterinário: Visualizar agenda, finalizar consulta e inserir notas de diagnóstico.

Admin: Definir qual veterinário atenderá uma consulta agendada.

🛠️ Configuração
Variáveis de Ambiente

Antes de rodar, configure as seguintes variáveis no seu sistema ou IDE para que o application.properties funcione corretamente:

api.security.secret=${MY_SECRET_KEY}
api.security.secret.adminLogin=${ADMIN_LOGIN}
api.security.secret.adminPassword=${ADMIN_PASSWORD}
spring.datasource.password=${DB_PASSWORD}
▶️ Rodando a Aplicação
1️⃣ Clone o repositório

Abra o terminal e execute:

git clone https://github.com/igorRooberto/SpringClinicPet.git
2️⃣ Acesse a pasta do projeto
cd SpringClinicPet
3️⃣ Execute a aplicação

Utilize o Maven Wrapper para garantir a versão correta do Maven:

./mvnw spring-boot:run
📚 Documentação (Swagger)

Com a aplicação em execução, acesse a documentação interativa para testar os endpoints:

🔗 http://localhost:8080/swagger-ui.html
