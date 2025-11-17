// Importa a classe Random, usada para gerar números aleatórios
import java.util.Random;

public class Dado {

    // Instância única de Random para evitar criar um novo objeto toda vez que o dado for rolado.
    private static final Random random = new Random();

    //Rola um dado com a quantidade de faces informada.
    public static int rolar(int faces) {
        // nextInt(faces) gera um número entre 0 e (faces - 1)
        return random.nextInt(faces) + 1;
    }
}
