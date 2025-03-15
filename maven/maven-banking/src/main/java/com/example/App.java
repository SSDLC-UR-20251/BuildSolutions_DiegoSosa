package com.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import org.json.*;

public class App {

    // 🔹 1. Leer el archivo JSON desde un .txt
    public static String leerArchivo(String rutaArchivo) {
        try {
            return new String(Files.readAllBytes(Paths.get(rutaArchivo)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 🔹 2. Obtener transacciones de un usuario específico
    public static List<JSONObject> obtenerTransacciones(String jsonData, String usuario) {
        List<JSONObject> transacciones = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(jsonData);
        if (jsonObject.has(usuario)) {
            JSONArray transaccionesArray = jsonObject.getJSONArray(usuario);
            for (int i = 0; i < transaccionesArray.length(); i++) {
                JSONObject transaccion = transaccionesArray.getJSONObject(i);
                transaccion.put("juan.jose@urosario.edu.co", usuario); // Añadir el usuario a cada transacción
                transacciones.add(transaccion);
            }
        }
        return transacciones;
    }

    // 🔹 3. Generar extracto bancario en un archivo .txt
    public static void generarExtracto(String usuario, List<JSONObject> transacciones) {
        StringBuilder extracto = new StringBuilder();
        extracto.append("Extracto bancario para: ").append(usuario).append("\n");
        extracto.append("====================================\n");
        for (JSONObject transaccion : transacciones) {
            extracto.append("timestamp: ").append(transaccion.getString("timestamp")).append("\n");
            extracto.append("balance: ").append(transaccion.getDouble("balance")).append("\n");
            extracto.append("type: ").append(transaccion.getString("type")).append("\n");
            extracto.append("------------------------------------\n");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.txt"))) {
            writer.write(extracto.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // TODO: Solicitar usuario, leer transacciones y generar extracto
        String rutaArchivo = "src/resources/transactions.txt"; // Cambiar a la ruta real del archivo JSON
        String jsonData = leerArchivo(rutaArchivo);
        if (jsonData != null) {
            String usuario = "juan.jose@urosario.edu.co"; // Cambiar al usuario real
            List<JSONObject> transacciones = obtenerTransacciones(jsonData, usuario);
            generarExtracto(usuario, transacciones);
        }
    }
}