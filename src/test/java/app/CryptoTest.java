package app;


import app.model.Configuration;
import org.junit.BeforeClass;

/**
 * This class is the super class for all cryptographical tests.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public abstract class CryptoTest {
    /**
     * The amount of times, the tests must repeat in the for schleife
     */
    protected static int times;
    /**
     * The configuration for the test
     */
    protected static Configuration config = new Configuration();

    /**
     * Initializing times and setting the default hash value, which can be overwritten.
     */
    @BeforeClass
    public static void initTimes() {
        times = 1;
        config.addSetting("HashType", "SHA-256");
    }
}
