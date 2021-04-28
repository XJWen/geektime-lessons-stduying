package org.geektimes.reactive;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Mono;

public class MonoDemo {

    public static void main(String[] args) {
        Mono.just("test")
                .map(m -> "")
                .subscribe(new Subscriber<String>() {

                    private Subscription subscription;

                    @Override
                    public void onSubscribe(Subscription subscription) {
                        this.subscription = subscription;
                    }

                    @Override
                    public void onNext(String s) {
                        if (s.isEmpty()){
                            this.subscription.cancel();
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
