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

Diagrama da aplicacao

![image](https://user-images.githubusercontent.com/33223967/122305839-61ef7f00-ceff-11eb-8a24-39cebc349fbb.png)

## Tecnologias 

Tecnologias utilizadas mais relevantes:

Linguagem: 

  - Kotlin v 1.5.0
  - Java - JDK 11.0.9 (Camada Infrastructura)

- Apache Jena 4.0.0 (https://jena.apache.org/)
- SQWRL API 2.0.8 (https://github.com/protegeproject/swrlapi/wiki/SQWRLQueryAPI)
- Hibernate 5.4.2 (https://hibernate.org/)
- H2 Database Engine 1.4.2 (https://h2database.com/html/main.html)
- Spring Boot - 5.1.6 (https://spring.io/projects/spring-boot) 

## Detalhes importantes:

### Configuracao Ontologia

No modulo `infrastructure` existe uma classe (`InfrastrutureConfig.kt`) que permite definir a ontologia a ser usada com os seguintes parametros.

| Nome da Parametro  | Descricao  |
|---|---|
| ONTOLOGY_URL | URL da ontologia a ser importada pela aplicacao | 
| USE_URL | Se a ontologia devera ser importada da URL definida no parametro `ONTOLOGY_URL` | 
| ONTOLOGY_PREFIX | Prefixo a ser utilizado na ontologia  | 
| ONTOLOGY_RESOURCE_FILE | Nome do ficheiro OWL guardado na pasta resources da camada `infrastructure` e que podera ser utilizado no caso do paramtero `USE_URL` e igual a `false` | 

![image](https://user-images.githubusercontent.com/33223967/122307780-e1328200-cf02-11eb-98af-17f17438f79d.png)


### Operadores Relacionais

Atualmente a aplicacao apenas possui uma selecao de 9 operadores relacionais, que estao guardados no ficheiro `builtinswrl.csv` na pasta `resources` do modulo `infrastucture`, poderao ser adicionados novos operadores desde que os mesmos nao utilizem mais de duas variaveis, pois a versao atual da aplicacao apenas consegue lidar com duas variaveis.

![image](https://user-images.githubusercontent.com/33223967/122307115-9bc18500-cf01-11eb-8824-33d5453da96e.png)

| Nome  | Descricao  |
|---|---|
| swrlb:equal | Satisfied iff the first argument and the second argument are the same. | 
| swrlb:notEqual | The negation of swrlb:equal. | 
| swrlb:lessThan | Satisfied iff the first argument and the second argument are both in some implemented type and the first argument is less than the second argument according to a type-specific ordering (partial or total), if there is one defined for the type. The ordering function for the type of untyped literals is the partial order defined as string ordering when the language tags are the same (or both missing) and incomparable otherwise.;| 
| swrlb:lessThanOrEqual | Either less than, as above, or equal, as above. | 
| swrlb:greaterThan | Similarly to swrlb:lessThan. | 
| swrlb:greaterThanOrEqual | Similarly to swrlb:lessThanOrEqual. | 
| swrlb:stringEqualIgnoreCase | Satisfied iff the first argument is the same as the second argument (upper/lower case ignored)  | 
| swrlb:booleanNot | Satisfied iff the first argument is true and the second argument is false, or vice versa. | 
| sameAs | Determine if individuals refer to the same underlying individual | 

### Configuracao Motor Base de Dados

Como demonstrado atualmente a aplicacao esta a utilizar o motor de base de dados H2. Como tambem explicado a arquitetura implementada em conjunto com o padrao de desenho Repository, facilmente conseguimos substituir o motor de base de dados por outro. Com o suporte do Hibernate e com as configuracoes corretas a aplicacao fica pronta a ser utilizada com o novo motor de base de dados sem qualquer alteracao adicional.

No modulo `infrastructure` existe uma classe (`Bootstraper.kt`) onde e possivel parametrizar o motor de base de dados.

```JAVA

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
//        dataSource.setUrl("jdbc:h2:tcp://localhost/~/semantic;DB_CLOSE_DELAY=-1;INIT=CREATE SCHEMA IF NOT EXISTS SEMANTICA");
        dataSource.setUrl("jdbc:h2:mem:semantic;DB_CLOSE_DELAY=-1;INIT=CREATE SCHEMA IF NOT EXISTS SEMANTICA");
        dataSource.setUsername("sa");
        dataSource.setPassword("sa");

        return dataSource;
    }
    
    private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(
                "hibernate.hbm2ddl.auto", "update");
        hibernateProperties.setProperty(
                "hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        hibernateProperties.setProperty(
                "hibernate.show_sql", "true");

        return hibernateProperties;
    }

```


## Endpoints

[GET] /semantic/api/v1/classes - Retorna todas as classes que compoe a ontologia.

Classes são grupos abstratos, conjuntos ou coleções de objetos. Eles podem conter indivíduos, outras classes, ou uma combinação de ambos.

| Nome da Propriedade  | Descricao  |
|---|---|
| id | ID da classe na ontologia | 
| label | Label da classe na ontologia | 
| uri | URI da classe na ontologia  | 
| description | Descricao da classe na ontologia  | 

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

**[GET]** /semantic/api/v1/individuals - Retorna todos individuos que compoe a ontologia.

Indivíduos são os componentes básicos de uma ontologia. Os indivíduos em uma ontologia podem incluir objetos concretos como pessoas, animais, mesas, automóveis, moléculas, planetas, assim como indivíduos abstratos como números e palavras.

| Nome da Propriedade  | Descricao  |
|---|---|
| id | ID do individuo na ontologia | 
| label | Label do individuo na ontologia | 
| uri | URI do individuo na ontologia  | 
| description | Descricao do individuo na ontologia  | 

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

**[GET]** /semantic/api/v1/object-properties - Retorna todas as _object properties_ que compoe a ontologia.

As formas como os objetos podem se relacionar com outros objetos.

| Nome da Propriedade  | Descricao  |
|---|---|
| id | ID da propriedade na ontologia | 
| label | Label da propriedade na ontologia | 
| damainId | ID do dominio da propriedade na ontologia  | 
| domainLabel | Label do dominio da propriedade na ontologia  | 
| uri | URI da propriedade na ontologia  | 
| description | Descricao da propriedade na ontologia  | 

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

**[GET]** /semantic/api/v1/datatype-properties - Retorna todas as _datatype properties_ que compoe a ontologia.

Propriedades, características ou parâmetros que os objetos podem ter e compartilhar;

| Nome da Propriedade  | Descricao  |
|---|---|
| id | ID da propriedade na ontologia | 
| label | Label da propriedade na ontologia | 
| dataType | Tipo de dado da propriedade na ontologia | 
| damainId | ID do dominio da propriedade na ontologia  | 
| domainLabel | Label do dominio da propriedade na ontologia  | 
| uri | URI da propriedade na ontologia  | 
| description | Descricao da propriedade na ontologia  | 

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

**[GET]** /semantic/api/v1/relational-operators - Retorna todas a lista de operadores relacionais SWRL diponiveis na API que compoe a ontologia.

Operadores relacionais (ou mais precisamente SWRL Built-In) são predicados definidos pelo usuário que podem ser usados em regras SWRL.

| Nome da Propriedade  | Descricao  |
|---|---|
| id | ID do operador realcional | 
| label | Label do operador realcional | 
| description | Descricao do operador realcional | 

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

**[POST]** /semantic/api/v1/sqrwl/query/run - Executa um query com base nos dados fornecidos no corpo do _request_.

| Nome da Propriedade  | Descricao  |
|---|---|
| name | Nome do query a ser executado | 
| query_parameters.entity_type | Tipo de entidade. (Class, Individuals, ObjectProperty, DatatypeProperty, Literal, RelationalOperator | 
| query_parameters.entity | Id da entidade na ontologia | 
| query_parameters.name | Nome da propriedade a ser retornada na response deste request |
| query_parameters.is_ordered_by | Se o resultado deve ser ordenado por esta entidade. (true, false) |
| query_parameters.is_column_showed | Se esta propriedade devera ser retornada na response. (true, false) |
| query_parameters.args | Com excepcao do tipo de entidade Literal, que o conteudo do array e o valor literal desta entidade, as restantes entidades o valor corresponde ao valor da variavel no query|

Request:

```JSON
{
    "name": "SchedulingProblem",
    "query_parameters": [
        {
            "entity_type": "Class",
            "entity": "SchedulingProblem",
            "name": "SchedulingProblem",
            "is_ordered_by": false,
            "is_column_showed": true,
            "args": [
                "m"
            ]
        }
    ]
}
```

| Nome da Propriedade  | Descricao  |
|---|---|
| result | Resultado retornado apos execucao do query | 
| rows | Numero de registos retornados | 
| swrlquery | Query executado no servidor | 

Response:

```JSON
{
    "data": {
        "result": [
            {
                "SchedulingProblem": "myspecificproblem"
            },
            {
                "SchedulingProblem": "totalWeightedCompletionTime"
            },
            {
                "SchedulingProblem": "discountedTotalWeightedCompletionTime"
            },
            {
                "SchedulingProblem": "totalWeightedTardiness"
            }
        ],
        "rows": 4,
        "swrlquery": "scheduling:SchedulingProblem(?m) -> sqwrl:select(?m) ^ sqwrl:columnNames(\"SchedulingProblem\")"
    },
    "error": {
        "statusCode": 200,
        "statusDescription": "200 OK",
        "description": "Success"
    }
}
```

**[POST]** /semantic/api/v1/sqrwl/query - Guarda o querie resultado do parse ao request na base de dados.

| Nome da Propriedade  | Descricao  |
|---|---|
| name | Nome do query a ser executado | 
| query_parameters.entity_type | Tipo de entidade. (Class, Individuals, ObjectProperty, DatatypeProperty, Literal, RelationalOperator | 
| query_parameters.entity | Id da entidade na ontologia | 
| query_parameters.name | Nome da propriedade a ser retornada na response deste request |
| query_parameters.is_ordered_by | Se o resultado deve ser ordenado por esta entidade. (true, false) |
| query_parameters.is_column_showed | Se esta propriedade devera ser retornada na response. (true, false) |
| query_parameters.args | Com excepcao do tipo de entidade Literal, que o conteudo do array e o valor literal desta entidade, as restantes entidades o valor corresponde ao valor da variavel no query|

Request:

```JSON
{
    "name": "SchedulingProblem",
    "query_parameters": [
        {
            "entity_type": "Class",
            "entity": "SchedulingProblem",
            "name": "SchedulingProblem",
            "is_ordered_by": false,
            "is_column_showed": true,
            "args": [
                "m"
            ]
        }
    ]
}
```

| Nome da Propriedade  | Descricao  |
|---|---|
| data | id do query guardado na base de dados | 

Response:

```JSON
{
    "data": "2337fc16-5151-443e-96c5-041c55165235",
    "error": {
        "statusCode": 201,
        "statusDescription": "201 CREATED",
        "description": "Success"
    }
}
```

**[GET]** /semantic/api/v1/sqrwl/query - Retorna todos os queries guardados na base de dados.

| Nome da Propriedade  | Descricao  |
|---|---|
| name | ID do query | 
| name | Nome do query | 
| query_parameters.entity_type | Tipo de entidade. (Class, Individuals, ObjectProperty, DatatypeProperty, Literal, RelationalOperator | 
| query_parameters.entity | Id da entidade na ontologia | 
| query_parameters.name | Nome da propriedade a ser retornada na response deste request |
| query_parameters.is_ordered_by | Se o resultado deve ser ordenado por esta entidade. (true, false) |
| query_parameters.is_column_showed | Se esta propriedade devera ser retornada na response. (true, false) |
| query_parameters.args | Com excepcao do tipo de entidade Literal, que o conteudo do array e o valor literal desta entidade, as restantes entidades o valor corresponde ao valor da variavel no query|

Response:

```JSON
{
    "data": [
        {
            "id": "5215934a-2fa9-4289-9181-e10e7892505a",
            "name": "Researches",
            "query_parameters": [
                {
                    "entityType": "Class",
                    "entity": "OWLClass_9598b33d_f57f_4f27_942e_fa47ade955e3",
                    "name": "Researcher",
                    "orderedBy": false,
                    "columnShowed": true,
                    "args": [
                        "z"
                    ]
                }
            ]
        },
        {
            "id": "16c3af66-7dc7-47b3-b89d-3b9dd27d515d",
            "name": "Implementation Library",
            "query_parameters": [
                {
                    "entityType": "Class",
                    "entity": "OWLClass_cccfbe7c_e1d1_4a2d_8022_5603a6e0257b",
                    "name": "ImplementationLibrary",
                    "orderedBy": false,
                    "columnShowed": true,
                    "args": [
                        "z"
                    ]
                }
            ]
        }
    ],
    "error": {
        "statusCode": 200,
        "statusDescription": "200 OK",
        "description": "Success"
    }
}
```

**[GET]** /semantic/api/v1/sqrwl/query/{id} - Retorna o query de um ID especifico.

| Nome da Propriedade  | Descricao  |
|---|---|
| name | ID do query | 
| name | Nome do query | 
| query_parameters.entity_type | Tipo de entidade. (Class, Individuals, ObjectProperty, DatatypeProperty, Literal, RelationalOperator | 
| query_parameters.entity | Id da entidade na ontologia | 
| query_parameters.name | Nome da propriedade a ser retornada na response deste request |
| query_parameters.is_ordered_by | Se o resultado deve ser ordenado por esta entidade. (true, false) |
| query_parameters.is_column_showed | Se esta propriedade devera ser retornada na response. (true, false) |
| query_parameters.args | Com excepcao do tipo de entidade Literal, que o conteudo do array e o valor literal desta entidade, as restantes entidades o valor corresponde ao valor da variavel no query|

Exemplo: `/semantic/api/v1/sqrwl/query/2337fc16-5151-443e-96c5-041c55165235`

Response:

```JSON
{
    "data": {
        "id": "2337fc16-5151-443e-96c5-041c55165235",
        "name": "SchedulingProblem",
        "query_parameters": [
            {
                "entityType": "Class",
                "entity": "SchedulingProblem",
                "name": "SchedulingProblem",
                "orderedBy": false,
                "columnShowed": true,
                "args": [
                    "m"
                ]
            }
        ]
    },
    "error": {
        "statusCode": 200,
        "statusDescription": "200 OK",
        "description": "Success"
    }
}
```

**[PUT]** /semantic/api/v1/sqrwl/query/{id} - Atualiza o query de um ID especifico.

| Nome da Propriedade  | Descricao  |
|---|---|
| name | Nome do query a ser executado | 
| query_parameters.entity_type | Tipo de entidade. (Class, Individuals, ObjectProperty, DatatypeProperty, Literal, RelationalOperator | 
| query_parameters.entity | Id da entidade na ontologia | 
| query_parameters.name | Nome da propriedade a ser retornada na response deste request |
| query_parameters.is_ordered_by | Se o resultado deve ser ordenado por esta entidade. (true, false) |
| query_parameters.is_column_showed | Se esta propriedade devera ser retornada na response. (true, false) |
| query_parameters.args | Com excepcao do tipo de entidade Literal, que o conteudo do array e o valor literal desta entidade, as restantes entidades o valor corresponde ao valor da variavel no query|

Exemplo: `/semantic/api/v1/sqrwl/query/2337fc16-5151-443e-96c5-041c55165235`

Request:

```JSON
{
    "name": "Problema de Escalonamento",
    "query_parameters": [
        {
            "entity_type": "Class",
            "entity": "SchedulingProblem",
            "name": "SchedulingProblem",
            "is_ordered_by": false,
            "is_column_showed": true,
            "args": [
                "m"
            ]
        }
    ]
}
```

| Nome da Propriedade  | Descricao  |
|---|---|
| result | Resultado retornado apos execucao do query | 
| rows | Numero de registos retornados | 
| swrlquery | Query executado no servidor | 

Response:

```JSON
{
    "data": {
        "result": [
            {
                "SchedulingProblem": "myspecificproblem"
            },
            {
                "SchedulingProblem": "totalWeightedCompletionTime"
            },
            {
                "SchedulingProblem": "discountedTotalWeightedCompletionTime"
            },
            {
                "SchedulingProblem": "totalWeightedTardiness"
            }
        ],
        "rows": 4,
        "swrlquery": "scheduling:SchedulingProblem(?m) -> sqwrl:select(?m) ^ sqwrl:columnNames(\"SchedulingProblem\")"
    },
    "error": {
        "statusCode": 200,
        "statusDescription": "200 OK",
        "description": "Success"
    }
}
```

**[DELETE]** /semantic/api/v1/sqrwl/query/{id} - Elimina o query de um ID especifico.

Exemplo: `/semantic/api/v1/sqrwl/query/2337fc16-5151-443e-96c5-041c55165235`

Response:

```JSON
{
    "error": {
        "statusCode": 204,
        "statusDescription": "204 NO_CONTENT",
        "description": "Success"
    }
}
```


**[POST]** /semantic/api/v1/scheduling-problem - Endpoint especifico para a demonstracao do Scheduling Problem, mas que permite tambem demonstrar a edicao de uma ontologia usando a API.

A response deste endpoint e o conteudo do ficheiro OWL apos alteracao efetua a partir do request, o content type da response e `application/octet-stream`

| Nome da Propriedade  | Descricao  |
|---|---|
| name | Nome do problema de escalonamento | 
| schedulingProblem | Nome e respectivas propriedades do problema de escalonamento | 
| machines | Nome e respectivas propriedades das maquinas que compoem o problema de escalonamento | 
| order | Nome e respectivas propriedades da ordem que compoem o problema de escalonamento | 
| jobs | Nome e respectivas propriedades dos jobs da ordem que compoem o problema de escalonamento | 
| tasks | Nome e respectivas propriedades das tarefas de cada job da ordem que compoem o problema de escalonamento | 
| objectiveFunction | Funcoes objetivo que compoem o problema de escalonamento | 


Request:

```JSON
{
    "name":"TestScheduling",
    "schedulingProblem":{
        "name":"TestScheduling",
        "properties":[
            {
                "name":"isFlowShopProblem",
                "value":"true",
                "type":"DatatypeProperty"
            },
            {
                "name":"hasNumberOfMachines",
                "value":"3",
                "type":"DatatypeProperty"
            }
        ]
    },
    "machines":[
        {
            "name":"Machine1",
            "properties":[
                {
                    "name":"hasBreakdownTimeInSeconds",
                    "value":"100",
                    "type":"DatatypeProperty"
                },
                {
                    "name":"hasBatchProcessing",
                    "value":"5",
                    "type":"DatatypeProperty"
                }
            ]

        },
        {
            "name":"Machine2",
            "properties":[
                {
                    "name":"hasBreakdownTimeInSeconds",
                    "value":"10",
                    "type":"DatatypeProperty"
                },
                {
                    "name":"hasBatchProcessing",
                    "value":"2",
                    "type":"DatatypeProperty"
                }
            ]

        },
        {
            "name":"Machine3",
            "properties":[
                {
                    "name":"hasBreakdownTimeInSeconds",
                    "value":"0",
                    "type":"DatatypeProperty"
                },
                {
                    "name":"hasBatchProcessing",
                    "value":"1",
                    "type":"DatatypeProperty"
                }
            ]

        }
    ],
    "order":{
        "name":"TestSchedulingOrder",
        "properties":[
                {
                    "name":"hasAllJobsIdentical",
                    "value":"true",
                    "type":"DatatypeProperty"
                },
                {
                    "name":"hasJobFamilies",
                    "value":"2",
                    "type":"DatatypeProperty"
                },
                {
                    "name":"hasNumberOfJobs",
                    "value":"4",
                    "type":"DatatypeProperty"
                },
                {
                    "name":"hasJobPrecedenceConstraints",
                    "value":"true",
                    "type":"DatatypeProperty"
                }
            ]
    },
    "jobFamilies":[],
    "jobs":[
        {
            "name":"Job1",
            "properties":[
                {
                    "name":"hasNumberOfTasks",
                    "value":"1",
                    "type":"DatatypeProperty"
                },
                {
                    "name":"hasNoWaitConstraints",
                    "value":"true",
                    "type":"DatatypeProperty"
                },
                {
                    "name":"belongsToJobFamily",
                    "value":"JobFamily1",
                    "type":"ObjectProperty"
                },
                {
                    "name":"precedesJob",
                    "value":"Job3",
                    "type":"ObjectProperty"
                }
            ]

        },
        {
            "name":"Job2",
            "properties":[
                {
                    "name":"hasNumberOfTasks",
                    "value":"2",
                    "type":"DatatypeProperty"
                },
                {
                    "name":"hasNoWaitConstraints",
                    "value":"false",
                    "type":"DatatypeProperty"
                },
                {
                    "name":"belongsToJobFamily",
                    "value":"JobFamily2",
                    "type":"ObjectProperty"
                },
                {
                    "name":"precedesJob",
                    "value":"Job3",
                    "type":"ObjectProperty"
                }
            ]

        },
        {
            "name":"Job3",
            "properties":[
                {
                    "name":"hasNumberOfTasks",
                    "value":"2",
                    "type":"DatatypeProperty"
                },
                {
                    "name":"hasNoWaitConstraints",
                    "value":"false",
                    "type":"DatatypeProperty"
                },
                {
                    "name":"belongsToJobFamily",
                    "value":"JobFamily2",
                    "type":"ObjectProperty"
                }
            ]

        },
        {
            "name":"Job4",
            "properties":[
                {
                    "name":"hasNumberOfTasks",
                    "value":"2",
                    "type":"DatatypeProperty"
                },
                {
                    "name":"hasNoWaitConstraints",
                    "value":"false",
                    "type":"DatatypeProperty"
                },
                {
                    "name":"belongsToJobFamily",
                    "value":"JobFamily2",
                    "type":"ObjectProperty"
                },
                {
                    "name":"precedesJob",
                    "value":"Job3",
                    "type":"ObjectProperty"
                }
            ]

        }],
    "tasks":[
        {
            "name":"Task1",
            "properties":[
                {
                    "name":"hasProcessingTimeInSeconds",
                    "value":"3",
                    "type":"DatatypeProperty"
                },
                {
                    "name":"belongsToJob",
                    "value":"Job1",
                    "type":"ObjectProperty"
                }
            ]

        },
        {
            "name":"Task2",
            "properties":[
                {
                    "name":"hasProcessingTimeInSeconds",
                    "value":"4",
                    "type":"DatatypeProperty"
                },
                {
                    "name":"belongsToJob",
                    "value":"Job1",
                    "type":"ObjectProperty"
                },
                {
                    "name":"belongsToJobFamily",
                    "value":"Task1",
                    "type":"ObjectProperty"
                }
            ]

        },
        {
            "name":"Task3",
            "properties":[
                {
                    "name":"hasProcessingTimeInSeconds",
                    "value":"5",
                    "type":"DatatypeProperty"
                },
                {
                    "name":"belongsToJob",
                    "value":"Job2",
                    "type":"ObjectProperty"
                }
            ]

        }],
    "objectiveFunction":{
        "name":"TestSchedulingObjectiveFunction",
        "properties":[
            {
                "name":"makespan",
                "value":"",
                "type":"ObjectProperty"
            },
            {
                "name":"totalWeightedCompletionTime",
                "value":"",
                "type":"ObjectProperty"
            }
        ]
    }
}
```


Exemplo de erro para todos endpoints:

```JSON
{
    "error": {
        "statusCode": 500,
        "statusDescription": "500 INTERNAL_SERVER_ERROR",
        "description": "Invalid SWRL atom predicate 'scheduling:OWLClass_9598b33d_f57f_4f27_942e_fa47ade955e3'"
    }
}
```


### Referencias

* https://www.w3.org/Submission/SWRL/
* https://pt.wikipedia.org/wiki/Ontologia_(ci%C3%AAncia_da_computa%C3%A7%C3%A3o)
* https://github.com/protegeproject/swrlapi/wiki/SQWRLQueryAPI


