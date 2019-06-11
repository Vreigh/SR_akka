package com.SR.library.server.worker.database;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.SR.library.server.request.SearchRequest;
import com.SR.library.server.response.SearchResponse;

import akka.actor.*;
import akka.japi.pf.DeciderBuilder;
import scala.concurrent.duration.Duration;

import static akka.actor.SupervisorStrategy.stop;
import static com.SR.library.server.response.ResponseStatus.ERROR;
import static com.SR.library.server.response.ResponseStatus.OK;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

public class DatabaseWorker extends AbstractActor {

  private static final SupervisorStrategy STRATEGY =
    new OneForOneStrategy(10, Duration.create("1 minute"), DeciderBuilder.matchAny(o -> stop()).build());

  private final List<ActorRef> searchers;
  private final AtomicInteger counter;

  public DatabaseWorker(List<String> databases) {
    searchers = databases.stream().map(DatabaseSearchWorker::props)
      .map(props -> context().actorOf(props))
      .collect(toList());
    counter = new AtomicInteger(searchers.size());
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder().match(SearchRequest.class, searchRequest -> {
      searchers.forEach(searcher -> searcher.tell(searchRequest, self()));
    }).match(SearchResult.class, result -> {
      if (result.isSuccess() || counter.decrementAndGet() == 0) {
        handleFinalResult(result);
        context().stop(self());
      }
    }).build();
  }

  private void handleFinalResult(SearchResult result) {
    if (nonNull(result.getClientRef())) {
      SearchResponse clientResponse = new SearchResponse(result.isSuccess() ? OK : ERROR, result.getPrice());
      result.getClientRef().tell(clientResponse, self());
    } else if (nonNull(result.getWorkerRef())) {
      result.getWorkerRef().tell(result, self());
    }
  }

  public static Props props(List<String> databases) {
    return Props.create(DatabaseWorker.class, () -> new DatabaseWorker(databases));
  }

  @Override
  public SupervisorStrategy supervisorStrategy() {
    return STRATEGY;
  }

  @Override
  public void postStop() {
    searchers.forEach(searcher -> context().stop(searcher));
  }
}
