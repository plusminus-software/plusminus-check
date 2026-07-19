package software.plusminus.check;

/**
 * Runnable that is allowed to throw any exception.
 * Used in checks of a code that is expected to throw an exception.
 *
 * @author Taras Shpek
 */
@FunctionalInterface
public interface ThrowingRunnable {

    @SuppressWarnings({"PMD.SignatureDeclareThrowsException", "java:S112"})
    void run() throws Throwable;

}
