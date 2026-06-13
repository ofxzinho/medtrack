# MedTrack

[![CI](https://github.com/ofxzinho/medtrack/actions/workflows/ci.yml/badge.svg)](https://github.com/ofxzinho/medtrack/actions)
[![Download](https://img.shields.io/badge/Download-v1.3.0-blue)](https://github.com/ofxzinho/medtrack/releases/tag/v1.3.0)

Aplicação CLI desenvolvida para o controle de horários e dosagens de medicamentos, focada em auxiliar idosos e seus cuidadores através de um sistema de monitoramento colaborativo e normalizado.

---

## Deploy

A aplicação está publicada e disponível para download na página de releases do GitHub:

**[Download MedTrack v1.3.0](https://github.com/ofxzinho/medtrack/releases/tag/v1.3.0)**

Após baixar o `.jar`, execute com:

```bash
java -jar medtrack-1.3.0-jar-with-dependencies.jar
```

---

## 1. Problema Real

Muitos idosos acabam esquecendo de tomar seus remédios ou se confundem com os horários e dosagens ao longo do dia. Esse é um problema de saúde grave que frequentemente resulta em complicações ou piora em tratamentos contínuos.

Além disso, familiares e cuidadores muitas vezes têm dificuldade em acompanhar se a dose foi realmente administrada quando não estão presentes no local.

---

## 2. Proposta da Solução

O **MedTrack** possui uma arquitetura onde o **Cuidador** e o **Medicamento** são entidades independentes, permitindo que um único cuidador gerencie múltiplos medicamentos de forma organizada e rastreável.

A partir da versão 1.2.0, a aplicação integra a **API pública OpenFDA** para consulta de informações oficiais sobre medicamentos. Na versão 1.3.0, os dados passaram a ser persistidos em um **banco de dados PostgreSQL hospedado na nuvem (Supabase)**, garantindo que nenhuma informação seja perdida entre sessões.

---

## 3. Público-Alvo

- Idosos que necessitam de auxílio para organizar sua rotina de medicação
- Familiares e cuidadores responsáveis por acompanhar e registrar o tratamento de forma profissional e auditável

---

## 4. Funcionalidades Principais

- **Gestão de Cuidadores:** Cadastro, listagem e remoção de responsáveis pelo monitoramento
- **Vínculo de Medicamentos:** Cadastro de medicamentos associados ao ID de um cuidador existente
- **Edição de Medicamentos:** Atualização de nome, dosagem e horário via ID
- **Monitoramento em Tempo Real:** Listagem com status (Pendente ou Tomado)
- **Segurança de Dados:** Validação que impede cadastro sem cuidador válido e remoção de cuidador com medicamentos vinculados
- **Controle de Dose:** Atualização de status e remoção via ID
- **Consulta OpenFDA:** Busca de informações oficiais sobre medicamentos via API pública
- **Persistência em Nuvem:** Dados salvos em PostgreSQL no Supabase

---

## 5. Tecnologias Utilizadas

- Java 17
- Maven 3.9+
- JUnit 5
- Checkstyle
- GitHub Actions (CI)
- OpenFDA API (https://open.fda.gov)
- PostgreSQL (Supabase — banco de dados em nuvem)
- Gson 2.11.0

---

## 6. Variáveis de Ambiente

A conexão com o banco de dados é configurada via variáveis de ambiente:

| Variável | Descrição |
|---|---|
| `DB_URL` | URL de conexão JDBC do Supabase |
| `DB_USER` | Usuário do banco |
| `DB_PASSWORD` | Senha do banco |

---

## 7. Instalação e Execução

### Pré-requisitos

- Java 17 (ou superior)
- Maven instalado
- Variáveis de ambiente configuradas (`DB_URL`, `DB_USER`, `DB_PASSWORD`)

### Passos

```bash
git clone https://github.com/ofxzinho/medtrack.git
cd medtrack
mvn package -DskipTests
java -jar target/medtrack-1.3.0-jar-with-dependencies.jar
```

---

## 8. Testes e Lint

### Rodar testes (JUnit)

```bash
mvn test
```

### Rodar análise estática (Checkstyle)

```bash
mvn checkstyle:check
```

---

## 9. Exemplo de Uso

```text
╔════════════════════════════════════╗
║         MEDTRACK v1.3.0            ║
║   Monitoramento de Saúde Familiar  ║
╚════════════════════════════════════╝

--- MENU ---
1. Cadastrar Cuidador
2. Listar Cuidadores
3. Remover Cuidador
4. Cadastrar Medicamento
5. Listar Medicamentos
6. Editar Medicamento
7. Marcar como Tomado
8. Remover Medicamento
9. Consultar Medicamento na FDA
0. Sair

Escolha: 1
Nome do Cuidador: Fábio Ruan
✔ Cuidador cadastrado com ID: 1

Escolha: 9
Nome do medicamento para consultar na FDA: aspirin
🔍 Consultando base da FDA...

--- Informações da FDA ---
Fabricante : Bayer
Indicação  : For the temporary relief of headache...
--------------------------
```

---

## 10. Estrutura do Projeto (v1.3.0)

```text
medtrack/
├── src/
│   ├── main/java/com/medtrack/
│   │   ├── Main.java
│   │   ├── cli/CLI.java
│   │   ├── db/DatabaseConnection.java
│   │   ├── model/
│   │   │   ├── Caregiver.java
│   │   │   └── Medication.java
│   │   ├── repository/
│   │   │   ├── CaregiverRepository.java
│   │   │   └── MedicationRepository.java
│   │   └── service/
│   │       ├── CaregiverService.java
│   │       ├── MedicationService.java
│   │       └── OpenFdaService.java
│   └── test/java/com/medtrack/
│       └── service/
│           ├── CaregiverServiceTest.java
│           ├── MedicationServiceTest.java
│           └── OpenFdaServiceTest.java
├── .github/workflows/ci.yml
├── checkstyle.xml
├── pom.xml
└── README.md
```

---

## 11. Integrantes do Grupo

| RA | Nome Completo | E-mail |
|---|---|---|
| 22510653 | Daniel Pinheiro Antunes Nogueira | daniel.pinheiro@sempreceub.com |
| 22508652 | Fábio Ruan Moreira de Alencar | fabio.alencar@sempreceub.com |
| 22510781 | Gabriel Costa Guimarães | gabrielcosta@sempreceub.com |
| 22508159 | João Gabriel Araújo de Oliveira | joaogabriel.araujo@sempreceub.com |
| 22552147 | Rafael Siqueira Soares | rafael.siqueira@sempreceub.com |

---

## 12. Informações do Projeto

- **Versão:** 1.3.0 (Banco de Dados em Nuvem + Testes Ampliados)
- **Instituição:** UniCEUB
- **Repositório:** https://github.com/ofxzinho/medtrack
- **Deploy:** https://github.com/ofxzinho/medtrack/releases/tag/v1.3.0
