# MedTrack

[![CI](https://github.com/ofxzinho/medtrack/actions/workflows/ci.yml/badge.svg)](https://github.com/ofxzinho/medtrack/actions)

Aplicação CLI desenvolvida para o controle de horários e dosagens de medicamentos, focada em auxiliar idosos e seus cuidadores.

## 1. Problema Real

Muitos idosos acabam esquecendo de tomar seus remédios ou se confundem com os horários e dosagens ao longo do dia. Esse é um problema de saúde grave que frequentemente resulta em idas desnecessárias ao hospital ou piora em tratamentos contínuos, muitas vezes apenas por falta de uma organização simples e acessível ou pela dificuldade da família em acompanhar a rotina.

## 2. Proposta da Solução

O MedTrack é um sistema de linha de comando (CLI) direto ao ponto. Ele permite o cadastro rápido de medicamentos e exibe uma lista clara do que está pendente e do que já foi tomado no dia. A escolha por uma interface de terminal garante que o sistema seja extremamente leve, rápido de usar e não dependa de conexão com a internet ou interfaces complexas que possam gerar confusão.

## 3. Público-Alvo

* Idosos que necessitam de auxílio para organizar sua rotina de medicação.
* Familiares e cuidadores responsáveis por acompanhar e registrar o tratamento.

## 4. Funcionalidades Principais

* Cadastro de novos medicamentos informando nome, dosagem e horário.
* Listagem de todos os remédios com indicativo visual de status (Pendente ou Tomado).
* Atualização de status para marcar um remédio específico como tomado.
* Remoção de medicamentos do registro através do ID.

## 5. Tecnologias Utilizadas

* Java 17
* Maven 3.9+ (Gerenciamento de dependências e build)
* JUnit 5 (Testes automatizados)
* Checkstyle (Análise estática de código / Linting)
* GitHub Actions (Integração Contínua)

## 6. Instruções de Instalação e Execução

### Pré-requisitos
Para rodar o projeto, é necessário ter o Java 17 (ou superior) e o Maven instalados na sua máquina.

### Passos para rodar
1. Clone o repositório para o seu computador:
```bash
git clone [https://github.com/ofxzinho/medtrack.git](https://github.com/ofxzinho/medtrack.git)
cd medtrack
```

2. Compile o projeto e gere o arquivo executável (JAR) usando o Maven:
```bash
mvn package -DskipTests
```

3. Execute a aplicação:
```bash
java -jar target/medtrack-1.0.0-jar-with-dependencies.jar
```

## 7. Instruções para Testes e Lint

### Rodar os Testes Automatizados (JUnit)
O projeto conta com testes unitários cobrindo as principais regras de negócio. Para executá-los, rode:
```bash
mvn test
```

### Rodar a Análise Estática (Checkstyle)
Para verificar a padronização e qualidade do código de acordo com as regras definidas, execute:
```bash
mvn checkstyle:check
```

## 8. Exemplo de Uso (Saída do Terminal)

```text
============================
      MedTrack v1.0.0      
  Controle de Medicamentos  
============================

--- MENU ---
1. Cadastrar medicamento
2. Listar medicamentos
3. Marcar como tomado
4. Remover medicamento
5. Sair
Escolha: 1

Nome do medicamento: Losartana
Dosagem (ex: 50mg): 50mg
Horário (ex: 08:00): 08:00

Medicamento cadastrado: [1] Losartana | 50mg | 08:00 | Pendente
```

## 9. Estrutura do Repositório

```text
medtrack/
├── src/
│   ├── main/java/com/medtrack/
│   │   ├── Main.java
│   │   ├── cli/CLI.java
│   │   ├── model/Medication.java
│   │   ├── repository/MedicationRepository.java
│   │   └── service/MedicationService.java
│   └── test/java/com/medtrack/
│       └── service/MedicationServiceTest.java
├── .github/workflows/ci.yml
├── checkstyle.xml
├── pom.xml
└── README.md
```

## 10. Informações do Projeto

* **Versão atual:** 1.0.0
* **Autor:** Fábio Ruan
* **Repositório Público:** https://github.com/ofxzinho/medtrack