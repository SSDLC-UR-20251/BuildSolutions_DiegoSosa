const { transferir } = require('../src/app');

test('Transferencia entre cuentas', () => {
    const resultado = transferir('juan.jose@urosario.edu.co', 'sara.palaciosc@urosario.edu.co', 30);
    expect(resultado['exito']).toBe(true); // Asumiendo que la función retorna true si la transferencia fue exitosa
});

test('Transferencia con saldo insuficiente', () => {
    const resultado = transferir('juan.jose@urosario.edu.co', 'sara.palaciosc@urosario.edu.co', 1000);
    expect(resultado['exito']).toBe(false); // Asumiendo que la función retorna false si la transferencia falla por saldo insuficiente
});
