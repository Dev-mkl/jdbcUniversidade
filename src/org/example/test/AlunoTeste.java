package org.example.test;

import org.example.model.Aluno;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;
public class AlunoTeste {
    @Test
    void testVerificaDataNascimento() {
        Aluno aluno = new Aluno("joao", 202450622, "10052000", "78639512355", 6);
        String result = aluno.VerificaDataNascimento();
        Assertions.assertEquals("10/05/2000", result);
    }
}
