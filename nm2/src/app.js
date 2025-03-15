const fs = require('fs');
const path = require('path');
const filePath = path.join(__dirname, 'transaction.txt');

// Función para leer el archivo transactions.txt
function leerArchivo() {
    try {
        const data = fs.readFileSync(filePath, 'utf8');
        return JSON.parse(data);
    } catch (err) {
        console.error('Error leyendo el archivo:', err);
        return {};
    }
}

// Función para escribir el archivo transactions.txt
function escribirArchivo(data) {
    try {
        fs.writeFileSync(filePath, JSON.stringify(data, null, 2), 'utf8');
    } catch (err) {
        console.error('Error escribiendo el archivo:', err);
    }
}

// Función para calcular el saldo actual de un usuario, basado en sus transacciones
function calcularSaldo(usuario) {
    const data = leerArchivo();
    const transacciones = data[usuario] || [];
    let saldo = 0;

    transacciones.forEach(transaccion => {
        saldo += parseFloat(transaccion.balance);
    });

    return saldo;
}

// Función para realizar la transferencia entre cuentas
function transferir(de, para, monto) {
    const data = leerArchivo();
    const saldoDe = calcularSaldo(de);

    if (saldoDe < monto) {
        return {
            exito: false,
            mensaje: `Saldo insuficiente en la cuenta de ${de}.`
        };
    }

    if (!data[de]) data[de] = [];
    if (!data[para]) data[para] = [];

    const timestamp = new Date().toISOString();

    data[de].push({
        balance: `-${monto}`,
        type: 'Transfer',
        timestamp: timestamp
    });

    data[para].push({
        balance: `${monto}`,
        type: 'Transfer',
        timestamp: timestamp
    });

    escribirArchivo(data);

    return {
        exito: true,
        mensaje: `Transferencia de ${monto} realizada correctamente de ${de} a ${para}.`
    };
}

const resultado = transferir('juan.jose@urosario.edu.co', 'sara.palaciosc@urosario.edu.co', 50);
console.log(resultado.mensaje);

// Exportar las funciones para pruebas
module.exports = { transferir, leerArchivo, escribirArchivo, calcularSaldo };
