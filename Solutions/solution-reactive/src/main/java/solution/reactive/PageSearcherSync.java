package solution.reactive;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@AllArgsConstructor
@Component
@Log4j2
public class PageSearcherSync implements PageSearcher {

    @Autowired
    private HttpClient client;

    public Result searchPageFor(String url, String term) {

        try {
            HttpRequest request = HttpRequest.newBuilder()
                                             .uri(URI.create(url))
                                             .build();

            log.info("***** client.sendSync() call started...");
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("***** client.sendSync() call ended...");

            boolean contains = response.body().contains(term);
            return Result.completed(url, term, contains);
        }
        catch (InterruptedException | IOException ex) {
            return Result.failed(url, term, ex);
        }
    }
}
