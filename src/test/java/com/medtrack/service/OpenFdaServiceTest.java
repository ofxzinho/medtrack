package com.medtrack.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OpenFdaServiceTest {

    private final OpenFdaService service = new OpenFdaService();

    @Test
    public void deveRetornarInformacoesParaMedicamentoValido() {
        String resultado = service.buscarMedicamento("aspirin");
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
    }

    @Test
    public void deveRetornarMensagemParaMedicamentoInexistente() {
        String resultado = service.buscarMedicamento("medicamentoxyzinexistente123");
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
    }
}
