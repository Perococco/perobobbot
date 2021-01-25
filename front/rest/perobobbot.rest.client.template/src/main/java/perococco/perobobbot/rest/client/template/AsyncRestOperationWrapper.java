package perococco.perobobbot.rest.client.template;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import perobobbot.lang.ThreadFactories;

import java.net.URI;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class AsyncRestOperationWrapper implements AsyncRestOperations {

    private static final Executor EXECUTOR = Executors.newCachedThreadPool(
            ThreadFactories.daemon("call(() -> delegate.s.operations %d")
    );

    private final @NonNull RestOperations delegate;


    private <T> CompletionStage<T> call(@NonNull Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier, EXECUTOR);
    }

    private CompletionStage<?> call(@NonNull Runnable runnable) {
        return CompletableFuture.runAsync(runnable);
    }

    @Override
    public <T> CompletionStage<T> getForObject(String url, Class<T> responseType, Object... uriVariables) throws RestClientException {
        return call(() -> delegate.getForObject(url, responseType, uriVariables));
    }

    @Override
    public <T> CompletionStage<T> getForObject(String url, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        return call(() -> delegate.getForObject(url, responseType, uriVariables));
    }

    @Override
    public <T> CompletionStage<T> getForObject(URI url, Class<T> responseType) throws RestClientException {
        return call(() -> delegate.getForObject(url, responseType));
    }

    @Override
    public <T> CompletionStage<ResponseEntity<T>> getForEntity(String url, Class<T> responseType, Object... uriVariables) throws RestClientException {
        return call(() -> delegate.getForEntity(url, responseType, uriVariables));
    }

    @Override
    public <T> CompletionStage<ResponseEntity<T>> getForEntity(String url, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        return call(() -> delegate.getForEntity(url, responseType, uriVariables));
    }

    @Override
    public <T> CompletionStage<ResponseEntity<T>> getForEntity(URI url, Class<T> responseType) throws RestClientException {
        return call(() -> delegate.getForEntity(url, responseType));
    }

    @Override
    public CompletionStage<HttpHeaders> headForHeaders(String url, Object... uriVariables) throws RestClientException {
        return call(() -> delegate.headForHeaders(url, uriVariables));
    }

    @Override
    public CompletionStage<HttpHeaders> headForHeaders(String url, Map<String, ?> uriVariables) throws RestClientException {
        return call(() -> delegate.headForHeaders(url, uriVariables));
    }

    @Override
    public CompletionStage<HttpHeaders> headForHeaders(URI url) throws RestClientException {
        return call(() -> delegate.headForHeaders(url));
    }

    @Override
    public CompletionStage<URI> postForLocation(String url, Object request, Object... uriVariables) throws RestClientException {
        return call(() -> delegate.postForLocation(url, request, uriVariables));
    }

    @Override
    public CompletionStage<URI> postForLocation(String url, Object request, Map<String, ?> uriVariables) throws RestClientException {
        return call(() -> delegate.postForLocation(url, request, uriVariables));
    }

    @Override
    public CompletionStage<URI> postForLocation(URI url, Object request) throws RestClientException {
        return call(() -> delegate.postForLocation(url, request));
    }

    @Override
    public <T> CompletionStage<T> postForObject(String url, Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        return call(() -> delegate.postForObject(url, request, responseType, uriVariables));
    }

    @Override
    public <T> CompletionStage<T> postForObject(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        return call(() -> delegate.postForObject(url, request, responseType, uriVariables));
    }

    @Override
    public <T> CompletionStage<T> postForObject(URI url, Object request, Class<T> responseType) throws RestClientException {
        return call(() -> delegate.postForObject(url, request, responseType));
    }

    @Override
    public <T> CompletionStage<ResponseEntity<T>> postForEntity(String url, Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        return call(() -> delegate.postForEntity(url, request, responseType, uriVariables));
    }

    @Override
    public <T> CompletionStage<ResponseEntity<T>> postForEntity(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        return call(() -> delegate.postForEntity(url, request, responseType, uriVariables));
    }

    @Override
    public <T> CompletionStage<ResponseEntity<T>> postForEntity(URI url, Object request, Class<T> responseType) throws RestClientException {
        return call(() -> delegate.postForEntity(url, request, responseType));
    }

    @Override
    public CompletionStage<?> put(String url, Object request, Object... uriVariables) throws RestClientException {
        return call(() -> delegate.put(url, request, uriVariables));
    }

    @Override
    public CompletionStage<?> put(String url, Object request, Map<String, ?> uriVariables) throws RestClientException {
        return call(() -> delegate.put(url, request, uriVariables));
    }

    @Override
    public CompletionStage<?> put(URI url, Object request) throws RestClientException {
        return call(() -> delegate.put(url, request));
    }

    @Override
    public <T> CompletionStage<T> patchForObject(String url, Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        return call(() -> delegate.patchForObject(url, request, responseType, uriVariables));
    }

    @Override
    public <T> CompletionStage<T> patchForObject(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        return call(() -> delegate.patchForObject(url, request, responseType, uriVariables));
    }

    @Override
    public <T> CompletionStage<T> patchForObject(URI url, Object request, Class<T> responseType) throws RestClientException {
        return call(() -> delegate.patchForObject(url, request, responseType));
    }

    @Override
    public CompletionStage<?> delete(String url, Object... uriVariables) throws RestClientException {
        return call(() -> delegate.delete(url, uriVariables));
    }

    @Override
    public CompletionStage<?> delete(String url, Map<String, ?> uriVariables) throws RestClientException {
        return call(() -> delegate.delete(url, uriVariables));
    }

    @Override
    public CompletionStage<?> delete(URI url) throws RestClientException {
        return call(() -> delegate.delete(url));
    }

    @Override
    public CompletionStage<Set<HttpMethod>> optionsForAllow(String url, Object... uriVariables) throws RestClientException {
        return call(() -> delegate.optionsForAllow(url, uriVariables));
    }

    @Override
    public CompletionStage<Set<HttpMethod>> optionsForAllow(String url, Map<String, ?> uriVariables) throws RestClientException {
        return call(() -> delegate.optionsForAllow(url, uriVariables));
    }

    @Override
    public CompletionStage<Set<HttpMethod>> optionsForAllow(URI url) throws RestClientException {
        return call(() -> delegate.optionsForAllow(url));
    }

    @Override
    public <T> CompletionStage<ResponseEntity<T>> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables) throws RestClientException {
        return call(() -> delegate.exchange(url, method, requestEntity, responseType, uriVariables));
    }

    @Override
    public <T> CompletionStage<ResponseEntity<T>> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        return call(() -> delegate.exchange(url, method, requestEntity, responseType, uriVariables));
    }

    @Override
    public <T> CompletionStage<ResponseEntity<T>> exchange(URI url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType) throws RestClientException {
        return call(() -> delegate.exchange(url, method, requestEntity, responseType));
    }

    @Override
    public <T> CompletionStage<ResponseEntity<T>> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType, Object... uriVariables) throws RestClientException {
        return call(() -> delegate.exchange(url, method, requestEntity, responseType, uriVariables));
    }

    @Override
    public <T> CompletionStage<ResponseEntity<T>> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        return call(() -> delegate.exchange(url, method, requestEntity, responseType, uriVariables));
    }

    @Override
    public <T> CompletionStage<ResponseEntity<T>> exchange(URI url, HttpMethod method, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType) throws RestClientException {
        return call(() -> delegate.exchange(url, method, requestEntity, responseType));
    }

    @Override
    public <T> CompletionStage<ResponseEntity<T>> exchange(RequestEntity<?> requestEntity, Class<T> responseType) throws RestClientException {
        return call(() -> delegate.exchange(requestEntity, responseType));
    }

    @Override
    public <T> CompletionStage<ResponseEntity<T>> exchange(RequestEntity<?> requestEntity, ParameterizedTypeReference<T> responseType) throws RestClientException {
        return call(() -> delegate.exchange(requestEntity, responseType));
    }

    @Override
    public <T> CompletionStage<T> execute(String url, HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor, Object... uriVariables) throws RestClientException {
        return call(() -> delegate.execute(url, method, requestCallback, responseExtractor, uriVariables));
    }

    @Override
    public <T> CompletionStage<T> execute(String url, HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor, Map<String, ?> uriVariables) throws RestClientException {
        return call(() -> delegate.execute(url, method, requestCallback, responseExtractor, uriVariables));
    }

    @Override
    public <T> CompletionStage<T> execute(URI url, HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor) throws RestClientException {
        return call(() -> delegate.execute(url, method, requestCallback, responseExtractor));
    }
}
