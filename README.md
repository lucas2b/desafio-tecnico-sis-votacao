# Desafio Técnico Sis Votação
Resolução de um desafio técnico que implementa um sistema de votação de Associados.

A solução adotada para este desafio foi desenvolvida em Java 8, utilizado o
framework SpringBoot, a base de dados MySQL, mapeamento ORM JPA, gerenciador de migrações
de Banco de dados Flyway e banco de dados em memória H2 para execução de testes unitários.

Para executar com sucesso a aplicação, é necessário antes ter uma instalação do MySQL rodando 
em localhost, na porta 3306, com um schema chamado "sis_votacao" e um usuário chamado
"desafio_user" com senha "desafio_user" possuindo todos privilégios sobre o schema criado.

Endpoints da API: (payloads de exemplo no diretório "payloads" na raiz da aplicação)

1. Listar Associados<br/>
   Tipo de Chamada: GET<br/>
   URL: http://localhost:8080/api/associados<br/>
   Descrição: Retorna a lista de Associados da tabela “associados”<br/>
   
2. Criação de Nova Pauta
   Tipo de chamada: POST<br/>
   URL: http://localhost:8080/api/pauta<br/>
   Descrição: Cria nova Pauta de Votação com sua descrição e<br/>
   retorna seu ID.
      
 3. Listar Pautas, 
    Tipo de Chamada: GET, 
    URL: http://localhost:8080/api/pauta/listarPautas , 
    Descrição: Retorna todas Pautas cadastradas com suas informações.
   
4. Abertura de Sessão de Pauta<br/>
   Tipo de Chamada: PUT<br/>
   URL: http://localhost:8080/api/pauta<br/>
   Descrição: Abre sessão de Votação para a Pauta indicada pelo atributo “id” que refere-se<br/>
   ao id da Pauta. O tempo de sessão é determinado em minutos e o atributo “tempoDeSessaoMinutos” é opcional<br/>
   caso este esteja ausente, o tempo de votação é de 1min conforme especificação.
   
5. Cadastrar Voto de Associado<br/>
   Tipo de Chamada: POST<br/>
   URL: http://localhost:8080/api/votacao/<br/>
   Descrição: Cadastra o voto do Associado em Determinada Pauta caso:<br/>
      1. A Pauta esteja aberta para votação.
      2. Caso o Associado ainda não tenha votado na Pauta.
      3. Caso o CPF do Associado seja válido.
      4. Caso a API de Testes de CPF fornecida retorne “ABLE_TO_VOTE”.
   Todas as condições acima devem ser satisfeitas para o voto ser computado e são testadas nesta ordem.<br/>
   O voto (sim ou não) do associado é descrito no payload pela propriedade “voto” por um valor booleano<br/>
   “true” ou “false”,indicando se o associado vota “Sim” ou “Não“ como resposta desta Pauta.
   
 6. Contar Votos Por Pauta
    Tipo de Chamada: GET<br/>
    URL: http://localhost:8080/api/votacao/contabilizar/{idDaPauta}<br/>
    Descrição: Conta e fornece os resultados para a pauta indicada no path da URL.
