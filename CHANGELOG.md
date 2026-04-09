# Changelog

Todas as mudanças notáveis deste projeto serão documentadas aqui.

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
