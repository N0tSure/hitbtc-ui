package com.artemsirosh.hitbtc;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Consumer;

/**
 * Created on 05 Apr, 2020.
 *
 * Utility for checking connection to {@literal HitBTC} API gateway.
 *
 * @author Artemis A. Sirosh
 */
public class HitBTCNetConnection {

    private static final String HIT_BTC_ADDR = "https://api.hitbtc.com";

    /**
     * Allows to checkout network connection to {@literal HitBTC} API gateway.
     * Use {@code connectionVerifier} to verify connection and
     * {@code errorInspector} to handle error due connecting.
     *
     * @param connectionVerifier verifies {@link HttpURLConnection} in some manner
     * @param errorInspector handles {@link IOException} if it occurs
     */
    public static void checkoutNetworkConnection(
        HttpConnectionConsumer connectionVerifier, Consumer<IOException> errorInspector
    ) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(HIT_BTC_ADDR).openConnection();
            connection.connect();
            connectionVerifier.useConnection(connection);
        } catch (IOException exc) {
            errorInspector.accept(exc);
        } finally {
            if (connection != null)
                connection.disconnect();
        }

    }

    /**
     * This is special consumer which instead of {@link Consumer} allows work
     * with a {@link HttpURLConnection} instance that throws checked exceptions
     * by any reason.
     */
    @FunctionalInterface
    public interface HttpConnectionConsumer {

        /**
         * Allows to use {@link HttpURLConnection} to verify network
         * connectivity.
         *
         * @param connection {@literal HTTP} network connection
         * @throws IOException in case of connection error or {@literal HTTP}
         * violation.
         */
        void useConnection(HttpURLConnection connection) throws IOException;
    }
}
