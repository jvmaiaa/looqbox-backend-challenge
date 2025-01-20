# Sistema de gerenciamento de vagas [![NPM](https://img.shields.io/npm/l/react)](https://github.com/jvmaiaa/backend-test-java/blob/master/LICENSE)

## Sobre o projeto

Projeto que consiste em realizar uma requisição na **PokéApi** para ordenar todos os pokemons de acordo com o parâmetro que for passado. Utilizei o **WebClient** para consumir a API. Implementei também todas as ordenações de maneira manual, detalhando de forma passo a passo a complexidade do algoritmo desenvolvido **(variação do bubble sort)**. Incluí também exceções personalizadas para tratar erros que possam ocorrer durante a execução da aplicação. Além disso, setei o endpoint que será consumido no `application.properties` para seguir boas práticas e evitar repetição de código.

## Diagrama detalhado da Arquitetura
A partir deste diagrama é possível entender a ordem que ocorre cada comportamento da aplicação.
![Modelagem](Arquitetura_Aplicacao.gif)

## Documentação e endpoints disponíveis - Swagger
Caso você queira ver todas as rotas (endpoints) disponíveis, inicialize o projeto e acesse a seguinte URL pelo seu navegador: `http://localhost:8080/swagger-ui/index.html`

## Tecnologias e suas versões
<table>
  <tr>
    <td>Java</td>
    <td>Spring Boot</td>
    <td>SpringDoc OpenApi - (Swagger)</td>
    <td>WebClient</td>
  </tr>
  <tr>
    <td>17</td>
    <td>3.4.1</td>
    <td>2.8.3</td>
    <td>...</td>
  </tr>
</table>

## Como Executar o projeto
Pré requisitos:`
- Ter o `git` instalado na máquina.
- Ter o `Docker` instalado na máquina. 
- Algum Client para testar as rotas (endpoints). Recomendo o `Postman` por ser simples e amigável.

Passos a serem executados:
1. git clone `https://github.com/jvmaiaa/looqbox-backend-challenge.git`
2. cd `looqbox-backend-challenge`
3. `./gradlew clean build`
4. `docker build -t imagem-looqbox-challenge .` -> **Precisa estar com o docker sendo executado**
5. `docker run -p 8080:8080 --name container-looqbox-challenge imagem-looqbox-challenge -d`

<details>
<summary>Como cadastrar-se</summary>

**CLIENTE:** Deve acessar a URL `localhost:8080/api/usuario/cliente`
**FUNCIONÁRIO:** Deve acessar a URL `localhost:8080/api/usuario/funcionario`
**GERENTE:** Deve acessar a URL `localhost:8080/api/usuario/gerente`

No corpo da requisição passar a seguinte estrutura **JSON**.
```
{
  "login" : "<seu_login>",
  "senha" : "<seu_senha>"
}
``` 
Automaticamente o seu usuário irá receber a devida permissão por estar cadastrando a partir de determinada URL.
</details>
<details>
<summary>Como autenticar-se</summary>

Deverá acessar o endpoint `localhost:8080/api/auth` passando o seu login e senha que está cadastrado no sistema, através de um arquivo JSON.
```
{
  "login" : "<seu_login>",
  "senha" : "<seu_senha>"
}
``` 
Caso suas credenciais estejam corretas, você receberá um Json Web Token (JWT) semelhante ao seguinte: `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoLWFwaSIsInN1YiI6ImNsaSIsImV4cCI6MTcyNjQ3NTkxOX0.KFptyIfkI_-Xa1riZwruf7GDImLMQ0ePDD-rY82HdgE`
</details>
<details>
<summary>Como acessar uma rota protegida</summary>

Com o JWT em mãos, obtido no passo anterior, acesse a uma rota qualquer protegida, passando ele no no campo `Bearer Token` do seu API Client (recomendo o Postman) e execute a requisição. Caso você tenha a permissão desejada, irá ser feito com sucesso.

</details>

## Entidades do Projeto
-> OBS: O projeto deve estar em execução antes de realizer qualquer ação para a URL de cada entidade ser acessada
<details>
<summary>Endereço</summary>

Você irá realizar o cadastro de um endereço a partir da URL `localhost:8080/api/endereco` para que possa ser cadastrada uma empresa futuramente, pois não é possível cadastrar uma empresa sem endereço. O único usuário que pode manipular a entidade `Endereço` é o usuário com a permissão de `GERENTE`.

Cadastrar um endereço - **POST** -> `localhost:8080/api/endereco`
```
{
    "cep" : "123",
    "rua" : "rua teste",
    "numero" : 20,
    "bairro" : "centro",
    "cidade" : "fualin",
    "estado" : "beltranin"
}
```
Buscar endereços **paginados** - **GET** -> `localhost:8080/api/endereco`

Buscar um endereço por id - **GET** -> `localhost:8080/api/endereco/{id}` passando um Id.

Atualizar um endereço - **PUT** -> `localhost:8080/api/endereco/{id}` passando um Id no parâmetro e um JSON semelhante acima.

Delete um endereço - **DELETE** -> `localhost:8080/api/endereco/{id}` passando um Id no parâmetro.

</details>

<details>
<summary>Empresa</summary>

Você irá realizar um cadastro de uma empresa a partir da URL `localhost:8080/api/empresa` para que possa ser feito um relatório futuramente, pois não é possível cadastrar um relaório sem Veículo e Empresa. O único usuário que pode manipular a entidade `Empresa` é o usuário com a permissão de `GERENTE`.

Cadastrar uma Empresa - **POST** -> `localhost:8080/api/empresa`
```
{
    "nome" : "João Víctor",
    "cnpj" : "3333",
    "telefone" : [
        "33383383",
        "23883383"
    ],
    "vagasMoto" : 100,
    "vagasCarro" : 100,
    "idEndereco" : 1
    
}
```
Buscar empresas **paginadas** - **GET** -> `localhost:8080/api/empresa`

Buscar uma empresa por id - **GET** -> `localhost:8080/api/empresa/{id}` passando um Id.

Atualizar uma empresa - **PUT** -> `localhost:8080/api/empresa/{id}` passando um Id no parâmetro e um JSON semelhante acima.

Delete uma empresa - **DELETE** -> `localhost:8080/api/empresa/{id}` passando um Id no parâmetro.

</details>

<details>
<summary>Veículo</summary>

Você irá realizar um cadastro de um veículo a partir da URL `localhost:8080/api/veiculo` para que possa ser cadastrada um relatório futuramente, pois não é possível cadastrar um relatório sem veículo e empresa. Todos usuários podem manipular a entidade `Veículo` com limitações para alguns.

Cadastrar um veículo - **POST** -> `localhost:8080/api/veiculo`. Pode ser feito por **todos usuários**.
```
{
    "marca" : "Fiat",
    "modelo" : "uno",
    "cor" : "Branco",
    "placa" : "ABC-123",
    "tipoDeVeiculo" : "CARRO"
}
```
Buscar veículos **paginados** - **GET** -> `localhost:8080/api/veiculo`. Pode ser feito por **GERENTE** e **FUNCIONÁRIO**.

Buscar um veículo por id - **GET** -> `localhost:8080/api/veiculo/{id}` passando um Id. Pode ser feito por **GERENTE** e **FUNCIONÁRIO**.

Atualizar um veículo - **PUT** -> `localhost:8080/api/veiculo/{id}` passando um Id no parâmetro e um JSON semelhante acima. Pode ser feito por **GERENTE** e **FUNCIONÁRIO**.

Delete um veículo - **DELETE** -> `localhost:8080/api/veiculo/{id}` passando um Id no parâmetro. Pode ser feito por **GERENTE** e **FUNCIONÁRIO**.

</details>
<details>
<summary>Relatório</summary>

Você irá realizar um cadastro de um relatório a partir da URL `localhost:8080/api/relatorio` para que possa ser feita a gestão de vagas e controle de entrada e saída. Apenas os usuários com permissões de **GERENTE** e **FUNCIONÁRIO** podem manipular a entidade `Relatório`.

Cadastrar um relatório - **POST** -> `localhost:8080/api/relatorio`. A partir do momento que essa requisição for feita, ela irá buscar a empresa que está presente na requisição e o tipo do veículo, e então, irá decrementar (subtrair) a vaga daquele tipo de veículo da empresa.
```
{
    "idEmpresa" : 1,
    "idVeiculo" : 1
}
```
Buscar relatórios **paginados** - **GET** -> `localhost:8080/api/relatorio/registro-geral`. Traz as informações gerais de um relatório.

Buscar um relatório por id - **GET** -> `localhost:8080/api/relatorio/registro-geral/{id}` passando um Id.

Finalizar um relatório - **PUT** -> `localhost:8080/api/relatorio/registra-saida/{id}` passando um Id no parâmetro. Após o relatório ser finalizado, a data de finalização será cadastrada naquele momento exato e a vaga do veículo que estava no relatório finalizado, será incrementada (somada), mostrando informando que o veículo já saiu do local.

Busca a **quantidade total** de **entradas e saídas** que ocorreram no sistema - **GET** -> `localhost:8080/api/relatorio/contagem-total/{id}` passando um Id como parâmetro.

Busca a **quantidade total** de **entradas e saídas por HORA** que ocorreram no sistema - **GET** -> `localhost:8080/api/relatorio/contagem-por-hora/{id}` passando um Id como parâmetro. Deve-se passar o **Id da empresa**, **horário de inicio** e **horário de fim** para que seja feita a busca da maneira correta, segue o exemplo:

Delete um relatório - **DELETE** -> `localhost:8080/api/relatorio/{id}` passando um Id no parametro.

</details>

