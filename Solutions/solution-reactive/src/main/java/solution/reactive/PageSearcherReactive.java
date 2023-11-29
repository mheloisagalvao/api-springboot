package solution.reactive;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
@AllArgsConstructor
@Log4j2
public class PageSearcherReactive implements PageSearcher {

    @Autowired
    private HttpClient client;

    public Result searchPageFor(String url, String term) {

        try {
            HttpRequest request = HttpRequest.newBuilder()
                                             .uri(URI.create(url))
                                             .build();

            log.info("***** client.sendAsync() call started...");

            StringMatchSubscriber subscriber = new StringMatchSubscriber(url, term);
            client.sendAsync(request, BodyHandlers.fromLineSubscriber(subscriber))
                  .exceptionally(ex -> {
                      subscriber.onError(ex);
                      return null;
                  });

            log.info("***** client.sendAsync() call ended...");

            Util.doSomethingUseful(50);
            return subscriber.getFutureResult().get();
        }
        catch (InterruptedException | ExecutionException ex) {
            return Result.failed(url, term, ex);
        }
    }
}
