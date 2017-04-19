/*
 *  Copyright (c) 2017 Dell Inc., or its subsidiaries.
 */

package com.emc.pravega.framework;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

import java.util.concurrent.CompletableFuture;

import static com.emc.pravega.framework.LoginClient.LOGIN_URL;
import static com.emc.pravega.framework.LoginClient.getAuthenticationRequestInterceptor;
import static com.emc.pravega.framework.TrustingSSLSocketFactory.getSslContext;

/**
 * Authentication enabled http client.
 */
@Slf4j
public class AuthEnabledHttpClient {

    private enum HttpClientSingleton {
        INSTANCE;

        private final CloseableHttpAsyncClient httpClient;

        HttpClientSingleton() {
            httpClient = HttpAsyncClients.custom()
                    .setSSLHostnameVerifier((s, sslSession) -> true).setSSLContext(getSslContext()).build();
            httpClient.start();
        }
    }

    /**
     * Get the HttpClient instance.
     *
     * @return instance of HttpClient.
     */
    public static CloseableHttpAsyncClient getHttpClient() {
        return HttpClientSingleton.INSTANCE.httpClient;
    }

    public static CompletableFuture<HttpResponse> getURL(final String url) {

        HttpGet request = new HttpGet(url);
        request.addHeader("Authorization", "token=" + LoginClient.getAuthToken(LOGIN_URL,
                getAuthenticationRequestInterceptor()));
        HttpAsyncCallback callBack = new HttpAsyncCallback();
        getHttpClient().execute(request, callBack);
        return callBack.getFuture();
    }

    private static final class HttpAsyncCallback implements FutureCallback<HttpResponse> {
        private final CompletableFuture<HttpResponse> future = new CompletableFuture<>();

        CompletableFuture<HttpResponse> getFuture() {
            return future;
        }

        @Override
        public void completed(HttpResponse httpResponse) {
            future.complete(httpResponse);
        }

        @Override
        public void failed(Exception e) {
            future.completeExceptionally(e);
        }

        @Override
        public void cancelled() {
            future.cancel(true);
        }
    }
}
