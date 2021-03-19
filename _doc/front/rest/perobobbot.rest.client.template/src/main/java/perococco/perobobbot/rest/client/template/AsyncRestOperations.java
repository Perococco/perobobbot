package perococco.perobobbot.rest.client.template;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;

import java.net.URI;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletionStage;

/**
 * The {@link org.springframework.web.client.RestOperations} in asynchronous mode
 */
public interface AsyncRestOperations  {
    <T> CompletionStage<T> getForObject(String url, Class<T> responseType, Object... uriVariables) throws RestClientException;
    <T> CompletionStage<T> getForObject(String url, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException;
    <T> CompletionStage<T> getForObject(URI url, Class<T> responseType) throws RestClientException;

    <T> CompletionStage<ResponseEntity<T>> getForEntity(String url, Class<T> responseType, Object... uriVariables) throws RestClientException;
    <T> CompletionStage<ResponseEntity<T>> getForEntity(String url, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException;
    <T> CompletionStage<ResponseEntity<T>> getForEntity(URI url, Class<T> responseType) throws RestClientException;

    CompletionStage<HttpHeaders> headForHeaders(String url, Object... uriVariables) throws RestClientException;
    CompletionStage<HttpHeaders> headForHeaders(String url, Map<String, ?> uriVariables) throws RestClientException;
    CompletionStage<HttpHeaders> headForHeaders(URI url) throws RestClientException;

    CompletionStage<URI> postForLocation(String url, Object request, Object... uriVariables) throws RestClientException;
    CompletionStage<URI> postForLocation(String url, Object request, Map<String, ?> uriVariables) throws RestClientException;
    CompletionStage<URI> postForLocation(URI url, Object request) throws RestClientException;

    <T> CompletionStage<T> postForObject(String url, Object request, Class<T> responseType, Object... uriVariables) throws RestClientException;
    <T> CompletionStage<T> postForObject(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException;
    <T> CompletionStage<T> postForObject(URI url, Object request, Class<T> responseType) throws RestClientException;

    <T> CompletionStage<ResponseEntity<T>> postForEntity(String url, Object request, Class<T> responseType, Object... uriVariables) throws RestClientException;
    <T> CompletionStage<ResponseEntity<T>> postForEntity(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException;
    <T> CompletionStage<ResponseEntity<T>> postForEntity(URI url, Object request, Class<T> responseType) throws RestClientException;

    CompletionStage<?> put(String url, Object request, Object... uriVariables) throws RestClientException;
    CompletionStage<?> put(String url, Object request, Map<String, ?> uriVariables) throws RestClientException;
    CompletionStage<?> put(URI url, Object request) throws RestClientException;

    <T> CompletionStage<T> patchForObject(String url, Object request, Class<T> responseType, Object... uriVariables) throws RestClientException;
    <T> CompletionStage<T> patchForObject(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException;
    <T> CompletionStage<T> patchForObject(URI url, Object request, Class<T> responseType) throws RestClientException;

    CompletionStage<?> delete(String url, Object... uriVariables) throws RestClientException;
    CompletionStage<?> delete(String url, Map<String, ?> uriVariables) throws RestClientException;
    CompletionStage<?> delete(URI url) throws RestClientException;

    CompletionStage<Set<HttpMethod>> optionsForAllow(String url, Object... uriVariables) throws RestClientException;
    CompletionStage<Set<HttpMethod>> optionsForAllow(String url, Map<String, ?> uriVariables) throws RestClientException;
    CompletionStage<Set<HttpMethod>> optionsForAllow(URI url) throws RestClientException;

    <T> CompletionStage<ResponseEntity<T>> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables) throws RestClientException;
    <T> CompletionStage<ResponseEntity<T>> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException;
    <T> CompletionStage<ResponseEntity<T>> exchange(URI url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType) throws RestClientException;
    <T> CompletionStage<ResponseEntity<T>> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType, Object... uriVariables) throws RestClientException;
    <T> CompletionStage<ResponseEntity<T>> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType, Map<String, ?> uriVariables) throws RestClientException;
    <T> CompletionStage<ResponseEntity<T>> exchange(URI url, HttpMethod method, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType) throws RestClientException;
    <T> CompletionStage<ResponseEntity<T>> exchange(RequestEntity<?> requestEntity, Class<T> responseType) throws RestClientException;
    <T> CompletionStage<ResponseEntity<T>> exchange(RequestEntity<?> requestEntity, ParameterizedTypeReference<T> responseType) throws RestClientException;

    <T> CompletionStage<T> execute(String url, HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor, Object... uriVariables) throws RestClientException;
    <T> CompletionStage<T> execute(String url, HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor, Map<String, ?> uriVariables) throws RestClientException;
    <T> CompletionStage<T> execute(URI url, HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor) throws RestClientException;
}
