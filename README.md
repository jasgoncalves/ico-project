# Projeto de Inteligencia Computacional e Otimizacao

## Objetivo

Desenvolvimento de uma aplicação para suporte a casos de uso a gestão e utilização do conhecimento com recursos a ontologias OWL,  incluindo utilização do conhecimento no contexto dos projetos como o de VRP (Vehicle Routing Problem) ou Scheduling.

O foco deste projeto está na representação, modelação, manutenção, interrogação, visualização, curadoria, etc., do conhecimento com recursos a normas, ferramentas e APIS como:

Protegé
OWL
SQWRL
VOWL

A API (REST) desenvolvida tem como objetivo principal permitir de forma simples, sem ser necessario conhecer em detalhe a sintaxe SQWRL, efetuar queries a uma ontologia (OWL) previamente carregadas. Tem como outras funcionalidades a possibilidade de guardar em base de dados queries criadas via API e tambem uma funcionlaidade especifica para edicao de um ficheiro OWL, neste projeto foi utilizado para demonstrar a possibilidade de adicionar-mos o nosso problema customizado de scheduling.

## Arquitetura

Para este projeto optei como padrao arquitetural da aplicacao o pardrao _Clean Architecture_ proposto por Robert Martin, com objetivo de promover a implementacao de um sistema reusavel, independente de tecnologia e testavel.

![image](https://user-images.githubusercontent.com/33223967/122293324-cfdf7a80-ceee-11eb-9725-4dceef05967b.png)

## Tecnologias 

Mais relevantes:

Linguagem: 

  - Kotlin v 1.5.0
  - Java - JDK 11.0.9 (Camada Infrastructura)

- Apache Jena 4.0.0 (https://jena.apache.org/)
- SQWRL API 2.0.8 (https://github.com/protegeproject/swrlapi/wiki/SQWRLQueryAPI)
- Hibernate 5.4.2 (https://hibernate.org/)
- H2 Database Engine 1.4.2 (https://h2database.com/html/main.html)
- Spring Boot - 5.1.6 (https://spring.io/projects/spring-boot) 


## Endpoints

[GET] /semantic/api/v1/classes - Retorna todas as classes que compoe a ontologia.

Response:

```JSON
{
    "data": [
        {
            "id": "FlexibleJobShop",
            "label": "FlexibleJobShop",
            "uri": "http://www.semanticweb.org/vbasto/ontologies/2021/4/scheduling#FlexibleJobShop",
            "description": null
        }
    ],
    "error": {
        "statusCode": 200,
        "statusDescription": "200 OK",
        "description": "Success"
    }
}
```

[GET] /semantic/api/v1/individuals - Retorna todos individuos que compoe a ontologia.

Response:

```JSON
{
    "data": [
        {
            "id": "makespan",
            "label": "makespan",
            "uri": "http://www.semanticweb.org/vbasto/ontologies/2021/4/scheduling#makespan",
            "description": null
        },
        {
            "id": "weightedShortestProcessingTimeFirst",
            "label": "weightedShortestProcessingTimeFirst",
            "uri": "http://www.semanticweb.org/vbasto/ontologies/2021/4/scheduling#weightedShortestProcessingTimeFirst",
            "description": null
        }
    ],
    "error": {
        "statusCode": 200,
        "statusDescription": "200 OK",
        "description": "Success"
    }
}
```

[GET] /semantic/api/v1/object-properties - Retorna todas as _object properties_ que compoe a ontologia.

Response:

```JSON
{
    "data": [
        {
            "id": "isAppliableHeuristicFor",
            "label": "isAppliableHeuristicFor",
            "domainId": null,
            "domainLabel": null,
            "rangeId": null,
            "uri": "http://www.semanticweb.org/vbasto/ontologies/2021/4/scheduling#isAppliableHeuristicFor",
            "description": null
        }
    ],
    "error": {
        "statusCode": 200,
        "statusDescription": "200 OK",
        "description": "Success"
    }
}
```

[GET] /semantic/api/v1/datatype-properties - Retorna todas as _datatype properties_ que compoe a ontologia.

Response:

```JSON
{
    "data": [
        {
            "id": "isParallelUnrelatedMachinesProblem",
            "label": "isParallelUnrelatedMachinesProblem",
            "dataType": "boolean",
            "domainId": "SchedulingProblem",
            "domainLabel": null,
            "uri": "http://www.semanticweb.org/vbasto/ontologies/2021/4/scheduling#isParallelUnrelatedMachinesProblem",
            "description": null
        }
    ],
    "error": {
        "statusCode": 200,
        "statusDescription": "200 OK",
        "description": "Success"
    }
}
```

[GET] /semantic/api/v1/relational-operators - Retorna todas a lista de operadores relacionais SWRL diponiveis na API que compoe a ontologia.

Response:

```JSON
{
    "data": [
        {
            "id": "swrlb:equal",
            "label": "Equal",
            "description": "Satisfied iff the first argument and the second argument are the same."
        }
    ],
    "error": {
        "statusCode": 200,
        "statusDescription": "200 OK",
        "description": "Success"
    }
}
```




