# Changelog

Todas as mudanças notáveis deste projeto serão documentadas aqui.

## [1.3.0] - 13/06/2026

### Adicionado
- Persistência de dados em banco de dados PostgreSQL hospedado na nuvem (Supabase)
- Remoção de cuidador via CLI com validação de medicamentos vinculados
- Edição de medicamentos via CLI (nome, dosagem e horário)
- Testes automatizados para `CaregiverService` com repositório fake (sem banco)
- Correção dos testes de `MedicationService` para rodar sem conexão com banco
- Atualização do README com integrantes do grupo, stack de banco de dados e instruções atualizadas

## [1.2.0] - 16/05/2026

### Adicionado
- Integração com a API pública OpenFDA para consulta de medicamentos
- Nova opção no menu CLI: Consultar Medicamento na FDA
- Teste de integração automatizado para `OpenFdaService`

## [1.1.0] - 09/04/2026

### Adicionado
- Arquitetura normalizada com entidade `Caregiver` independente
- Vínculo entre cuidador e medicamento via ID
- Validação que impede cadastro de medicamento sem cuidador válido
- Testes automatizados com JUnit 5
- Pipeline de CI com GitHub Actions
- Badge de build no README

## [1.0.0] - 09/04/2026

### Adicionado
- Versão inicial da aplicação CLI MedTrack
- Cadastro e listagem de medicamentos
- Controle de status (Pendente / Tomado)
- Remoção de medicamento por ID
