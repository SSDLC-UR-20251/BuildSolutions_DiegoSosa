import sys
import random
import string

# TODO: Implementar la función para generar códigos de recuperación
def generar_codigo():
    """
    Debe generar un código de 6 caracteres alfanuméricos sin caracteres ambiguos.
    Caracteres ambiguos a evitar: '0', 'O', '1', 'l'
    """
    carac = string.ascii_letters + string.digits.replace('0', '').replace('O', '').replace('1', '').replace('l', '')
    code = ''
    for i in range(0, 6):
        code = code + str(carac[random.randint(0, len(carac))])
     
    return code  # TODO: Implementar la generación del código

if __name__ == "__main__":
    if len(sys.argv) != 2 or not sys.argv[1].isdigit():
        print("Uso: python main.py <cantidad_de_codigos>")
        sys.exit(1)

    cantidad = int(sys.argv[1])
    print("Generando códigos de recuperación...")

    for i in range(cantidad):
        print(f"Código {i+1}: {generar_codigo()}")