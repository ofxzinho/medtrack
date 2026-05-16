# MedTrack

[![CI](https://github.com/ofxzinho/medtrack/actions/workflows/ci.yml/badge.svg)](https://github.com/ofxzinho/medtrack/actions)
[![Download](https://img.shields.io/badge/Download-v1.2.0-blue)](https://github.com/ofxzinho/medtrack/releases/tag/v1.2.0)

Aplicação CLI desenvolvida para o controle de horários e dosagens de medicamentos, focada em auxiliar idosos e seus cuidadores através de um sistema de monitoramento colaborativo e normalizado.

---

##  Deploy

A aplicação está publicada e disponível para download na página de releases do GitHub:

** [Download MedTrack v1.2.0](https://github.com/ofxzinho/medtrack/releases/tag/v1.2.0)**

Após baixar o `.jar`, execute com:

```bash
java -jar medtrack-1.2.0-jar-with-dependencies.jar
```

---

## 1. Problema Real

Muitos idosos acabam esquecendo de tomar seus remédios ou se confundem com os horários e dosagens ao longo do dia. Esse é um problema de saúde grave que frequentemente resulta em complicações ou piora em tratamentos contínuos.

Além disso, familiares e cuidadores muitas vezes têm dificuldade em acompanhar se a dose foi realmente administrada quando não estão presentes no local.

---

## 2. Proposta da Solução

O **MedTrack** evoluiu para uma arquitetura robusta onde o **Cuidador** e o **Medicamento** são entidades independentes. Isso permite que um único cuidador gerencie múltiplos medicamentos de forma organizada e rastreável.

A partir da versão 1.2.0, a aplicação também se integra à **API pública OpenFDA**, permitindo consultar informações oficiais sobre medicamentos diretamente pelo terminal.

---

## 3. Público-Alvo

- Idosos que necessitam de auxílio para organizar sua rotina de medicação
- Familiares e cuidadores responsáveis por acompanhar e registrar o tratamento de forma profissional e auditável

---

## 4. Funcionalidades Principais

- **Gestão de Cuidadores:** Cadastro independente de responsáveis pelo monitoramento
- **Vínculo de Medicamentos:** Cadastro de medicamentos associados ao ID de um cuidador existente
- **Monitoramento em Tempo Real:** Listagem com status (Pendente ou Tomado)
- **Segurança de Dados:** Validação que impede cadastro sem cuidador válido
- **Controle de Dose:** Atualização de status e remoção via ID
- **Consulta OpenFDA:** Busca de informações oficiais sobre medicamentos via API pública

---

## 5. Tecnologias Utilizadas

- Java 17
- Maven 3.9+
- JUnit 5
- Checkstyle
- GitHub Actions (CI)
- OpenFDA API (https://api.fda.gov)

---

## 6. Instalação e Execução

### Pré-requisitos

- Java 17 (ou superior)
- Maven instalado

### Passos

```bash
git clone https://github.com/ofxzinho/medtrack.git
cd medtrack
mvn package -DskipTests
java -jar target/medtrack-1.2.0-jar-with-dependencies.jar
```

---

## 7. Testes e Lint

### Rodar testes (JUnit)

```bash
mvn test
```

### Rodar análise estática (Checkstyle)

```bash
mvn checkstyle:check
```

---

## 8. Exemplo de Uso

```text
╔════════════════════════════════════╗
║         MEDTRACK v1.2.0            ║
║   Monitoramento de Saúde Familiar  ║
╚════════════════════════════════════╝

--- MENU ---
1. Cadastrar Cuidador
2. Listar Cuidadores
3. Cadastrar Medicamento
4. Listar Medicamentos
5. Marcar como Tomado
6. Remover Medicamento
7. Consultar Medicamento na FDA
8. Sair

Escolha: 7
Nome do medicamento para consultar na FDA: aspirin
🔍 Consultando base da FDA...

--- Informações da FDA ---
Fabricante : Bayer
Indicação  : For the temporary relief of headache...
--------------------------
```

---

## 9. Estrutura do Projeto (v1.2.0)

```text
medtrack/
├── src/
│   ├── main/java/com/medtrack/
│   │   ├── Main.java
│   │   ├── cli/CLI.java
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
│           ├── MedicationServiceTest.java
│           └── OpenFdaServiceTest.java
├── .github/workflows/ci.yml
├── checkstyle.xml
├── pom.xml
└── README.md
```

---

## 10. Informações do Projeto

- **Versão:** 1.2.0 (Integração OpenFDA)
- **Autor:** Fábio Ruan
- **Instituição:** UniCEUB
- **Repositório:** https://github.com/ofxzinho/medtrack
- **Deploy:** https://github.com/ofxzinho/medtrack/releases/tag/v1.2.0
