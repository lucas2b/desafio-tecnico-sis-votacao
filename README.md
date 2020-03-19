# desafio-tecnico-sis-votacao
Resolução de um desafio técnico que implementa um sistema de votação de Associados.

A solução adotada para este desafio foi desenvolvida em Java 8, utilizado o
framework SpringBoot, a base de dados MySQL, mapeamento ORM JPA, gerenciador de migrações
de Banco de dados Flyway e banco de dados em memória H2 para execução de testes unitários.

Para executar com sucesso a aplicação, é necessário antes ter uma instalação do MySQL rodando 
em localhost, na porta 3306, com um schema chamado "sis_votacao" e um usuário chamado
"desafio_user" com senha "desafio_user" possuindo todos privilégios sobre o schema criado.

Operações:
