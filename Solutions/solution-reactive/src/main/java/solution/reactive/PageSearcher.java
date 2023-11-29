package solution.reactive;

import java.util.List;

public interface PageSearcher {
    Result searchPageFor(String url, String term);
}