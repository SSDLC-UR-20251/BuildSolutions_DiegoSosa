package com.example;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    @Test
    public void testLeerArchivo() {
        File archivo = new File("src/resources/transactions.txt");
        assertTrue(archivo.exists(), "El archivo no existe");
        assertTrue(archivo.canRead(), "El archivo no se puede leer");
    }

    @Test
    public void testObtenerTransacciones() {
        String jsonData = "{\"juan.jose@urosario.edu.co\":[" +
                      "{\"balance\":\"50\",\"type\":\"Deposit\",\"timestamp\":\"2025-02-11 14:17:21.921536\"}," +
                      "{\"balance\":\"-20\",\"type\":\"Withdrawal\",\"timestamp\":\"2025-02-15 10:30:15.123456\"}" +
                      "]}";
        String usuario = "juan.jose@urosario.edu.co";
        List<JSONObject> transacciones = App.obtenerTransacciones(jsonData, usuario);
        assertEquals(2, transacciones.size(), "El número de transacciones no es correcto");
        assertEquals("juan.jose@urosario.edu.co", transacciones.get(0).getString("usuario"), "El usuario no coincide");
}

    @Test
    public void testGenerarExtracto() {
        String usuario = "juan.jose@urosario.edu.co";
        List<JSONObject> transacciones = List.of(
            new JSONObject().put("timestamp", "2023-10-01").put("balance", 100.0).put("type", "Withdrawal")
        );
        App.generarExtracto(usuario, transacciones);
        File archivo = new File("transactions.txt");
        assertTrue(archivo.exists(), "El archivo de extracto no se generó");
        assertTrue(archivo.canRead(), "El archivo de extracto no se puede leer");
    }
}