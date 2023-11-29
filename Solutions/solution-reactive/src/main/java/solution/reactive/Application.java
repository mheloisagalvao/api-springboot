package solution.reactive;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.net.http.HttpClient;

@SpringBootApplication
@Log4j2
public class Application {

    private static final String url = "https://en.wikipedia.org/wiki/Opinion_polling_in_United_Kingdom_constituencies,_2010%E2%80%932015";
    private static final String term = "Wales";

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(Application.class, args);

        // Uncomment one of the following statements:
        // doSearch(context.getBean(PageSearcherSync.class));
        // doSearch(context.getBean(PageSearcherAsync.class));
        // doSearch(context.getBean(PageSearcherReactive.class));
    }

    private static void doSearch(PageSearcher searcher) {
        Result result = searcher.searchPageFor(url, term);
        log.info(result);
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newHttpClient();
    }
}
