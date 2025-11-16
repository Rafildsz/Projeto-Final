//Dado.java

import java.util.Random;

public class Dado {
    private static final Random random = new Random();

    public static int rolar(int faces) {
        return random.nextInt(faces) + 1;
    }
}