package solution.reactive;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@Log4j2
public class Result {

    private final String url;
    private final String term;
    private final boolean containsSearchItem;

    public static Result completed(String url, String term, boolean containsSearchItem) {
        Result result = new Result(url, term, containsSearchItem);
        log.info("Search completed " + result);
        return result;
    }

    public static Result failed(String url, String term, Throwable exception) {
        Result result = new Result(url, term, false);
        log.error("Error while searching " + url + " for " + term + ": " + exception.getMessage());
        return result;
    }
}
