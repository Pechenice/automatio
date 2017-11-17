package helpers;

import java.util.UUID;

public class DataGenerator {
    public static String stringGenerator(String generated) {
        return generated = generated + UUID.randomUUID().toString().replace("-","").substring(0,5);
    }
}
