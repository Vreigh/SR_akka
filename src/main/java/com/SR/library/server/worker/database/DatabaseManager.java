package com.SR.library.server.worker.database;

import java.util.Arrays;
import java.util.List;

import com.SR.library.server.request.SearchRequest;

import akka.actor.AbstractActor;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;
import scala.concurrent.duration.Duration;

import static akka.actor.SupervisorStrategy.stop;

public class DatabaseManager extends AbstractActor {

  private static final SupervisorStrategy STRATEGY =
    new OneForOneStrategy(10, Duration.create("1 minute"), DeciderBuilder.matchAny(o -> stop()).build());

  private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
  private final List<String> databases = Arrays.asList("library/book_db_1.txt", "library/book_db_2.txt");

  @Override
  public Receive createReceive() {
    return receiveBuilder().match(SearchRequest.class, request -> {
      context().actorOf(DatabaseWorker.props(databases)).tell(request, self());
    }).build();
  }

  public static Props props() {
    return Props.create(DatabaseManager.class, DatabaseManager::new);
  }

  @Override
  public SupervisorStrategy supervisorStrategy() {
    return STRATEGY;
  }

}
