# **DEVin[_Philips_] - LABMedical**

## **Módulo 3 - Projeto Avaliativo 2**

A **LABMedicine LTDA**, empresa líder no segmento tecnológico de gestão hospitalar, foi selecionada em edital e recebeu um aporte financeiro para aprimorar seu principal produto, o *LABMedical*. A expectativa é desenvolver um novo sistema no formato white-label, capaz de ser personalizado e comercializado para hospitais, clínicas particulares e postos de saúde em todo o país.

O modelo white-label consiste em um software padrão que pode ser personalizado com as cores, tipografias, logotipos e demais elementos visuais da identidade do cliente, proporcionando um resultado personalizado.

O perfil de sua squad chamou a atenção dos gestores da empresa, e vocês foram designados para criar o novo produto, utilizando as tecnologias *HTML, CSS, JavaScript, Angular, Java, Oracle SQL e Spring*. Para a elaboração do projeto, será necessário selecionar uma estrutura padrão de layout e um nome e identidade visual fictícios para o hospital, a fim de ilustrar o uso do software durante a apresentação aos gestores.

## **Ferramentas Utilizadas para Desenvolvimento**

```
IntelliJ IDEA
PL/SQL Developer
DBeaver
Postman
Spring Boot     
Java
Oracle 21c
```

## **Dependências**

O desenvolvimento de código em Java, em geral, usufrui de um significativo conjunto de bibliotecas e _frameworks_. Esta
reutilização é incorporada em um projeto por meio de dependências. Para gerenciar foi utiliado o _Maven_.

```
Spring Web MVC
Spring JPA
Oracle JDBC
Lombok
Flyway
MapStruct
DevTools
Validation
```

## **Scripts**

Todo script para criação do banco de dados se encontra na pasta **resources/db/migration**

## **Métodos**

Requisições para a API devem seguir os padrões:

| Método | Descrição |
|---|---|
| `GET` | Retorna informações de um ou mais registros. |
| `POST` | Utilizado para criar um novo registro. |
| `PUT` | Atualiza dados de um registro ou altera sua situação. |
| `DELETE` | Remove um registro do sistema. |

## **Respostas**

| Status | Descrição                                                          |
|--------|--------------------------------------------------------------------|
| `200`  | Requisição executada com sucesso (success).                        |
| `201`  | Requisição executada com sucesso (success).                        |
| `400`  | Erros de validação ou os campos informados não existem no sistema. |
| `409`  | Conflito.                                                          |
| `405`  | Método não implementado.                                           |

# **Recursos da API**

| Método     | Endpoint                                             |
|------------|------------------------------------------------------|
| `GET`      | /api/usuarios                                        |
| `GET`      | /api/usuarios/{id}                                   |
| `POST`     | /api/usuarios                                        |
| `POST`     | /api/usuarios/login                                  |
| `PUT`      | /api/usuarios/{id}                                   |
| `PUT`      | /api/usuarios/{id}/resetarsenha                      |
| `DELETE`   | /api/usuarios/{id}                                   |
| ---------- | --------------------------                           |
| `GET`      | /api/pacientes                                       |
| `GET`      | /api/pacientes/{id}                                  |
| `POST`     | /api/pacientes                                       |
| `PUT`      | /api/pacientes/{id}                                  |
| `DELETE`   | /api/pacientes/{id}                                  |
| ---------- | --------------------------                           |
| `GET`      | /api/empresas                                        |
| `GET`      | /api/empresas/{id}                                   |
| `POST`     | /api/empresas                                        |
| `PUT`      | /api/empresas/{id}                                   |
| `DELETE`   | /api/empresas/{id}                                   |
| ---------- | --------------------------                           |
| `GET`      | /api/consultas                                       |
| `GET`      | /api/consultas/{id}                                  |
| `POST`     | /api/consultas                                       |
| `PUT`      | /api/consultas/{id}                                  |
| `DELETE`   | /api/consultas/{id}                                  |
| ---------- | --------------------------                           |
| `GET`      | /api/exames                                          |
| `GET`      | /api/exames/{id}                                     |
| `POST`     | /api/exames                                          |
| `PUT`      | /api/exames/{id}                                     |
| `DELETE`   | /api/exames/{id}                                     |
| ---------- | --------------------------                           |
| `GET`      | /api/dietas                                          |
| `GET`      | /api/dietas/{id}                                     |
| `GET`      | /api/dietas?nomePaciente={nomePaciente}              |
| `POST`     | /api/dietas                                          |
| `PUT`      | /api/dietas/{id}                                     |
| `DELETE`   | /api/dietas/{id}                                     |
| ---------- | --------------------------                           |
| `GET`      | /api/medicamentos                                    |
| `GET`      | /api/medicamentos/{id}                               |
| `GET`      | /api/medicamentos?nomePaciente={nomePaciente}        |
| `POST`     | /api/medicamentos                                    |
| `PUT`      | /api/medicamentos/{id}                               |
| `DELETE`   | /api/medicamentos/{id}                               |
| ---------- | --------------------------                           |
| `GET`      | /api/exercicios                                      |
| `GET`      | /api/exercicios/{id}                                 |
| `GET`      | /api/exercicios?nomePaciente={nomePaciente}          |
| `POST`     | /api/exercicios                                      |
| `PUT`      | /api/exercicios/{id}                                 |
| `DELETE`   | /api/exercicios/{id}                                 |
| ---------- | --------------------------                           |
| `GET`      | /api/logs                                            |
| `GET`      | /api/logs/listar?codLink={codLink}&tabLink={tabLink} |
| ---------- | --------------------------                           |
| `GET`      | /api/estatisticas                                    |