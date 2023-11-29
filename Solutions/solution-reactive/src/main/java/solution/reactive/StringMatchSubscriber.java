package solution.reactive;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

@Data
@Log4j2
public class StringMatchSubscriber implements Subscriber<String> {

    private final String url;
    private final String term;
    private final CompletableFuture<Result> futureResult = new CompletableFuture<>();;
    private Subscription subscription;

    public CompletableFuture<Result> getFutureResult() {
        return futureResult;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        log.info("onSubscribe() called, searching for term: " + term);
        this.subscription = subscription;
        this.subscription.request(1);
    }

    @Override
    public void onNext(String line) {
        log.info("onNext() called, line is: " + line);
        if (line.contains(term)) {
            futureResult.complete(Result.completed(url, term, true));
            subscription.cancel();
        }
        else {
            subscription.request(1);
        }
    }

    @Override
    public void onComplete() {
        log.info("onComplete() called, so we can't have found the search term");
        futureResult.complete(Result.completed(url, term, false));
    }

    @Override
    public void onError(Throwable ex) {
        log.info("onError() called");
        futureResult.completeExceptionally(ex);
    }
}
