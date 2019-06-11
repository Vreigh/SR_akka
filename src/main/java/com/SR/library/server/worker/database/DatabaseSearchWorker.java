package com.SR.library.server.worker.database;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import com.SR.library.server.request.SearchRequest;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import lombok.AllArgsConstructor;

import static com.SR.library.Utils.DELIM;
import static java.util.Objects.nonNull;

@AllArgsConstructor
public class DatabaseSearchWorker extends AbstractActor {

  private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

  private final String database;

  @Override
  public Receive createReceive() {
    return receiveBuilder().match(SearchRequest.class, searchRequest -> {
      getSender().tell(search(searchRequest), self());
    }).build();
  }

  public static Props props(String database) {
    return Props.create(DatabaseSearchWorker.class, () -> new DatabaseSearchWorker(database));
  }

  private SearchResult search(SearchRequest searchRequest) {
    String price = null;
    String path = null;
    try (BufferedReader dbReader = new BufferedReader(new InputStreamReader(new FileInputStream(database)))) {
      String line;
      while ((line = dbReader.readLine()) != null) {
        List<String> parts = Arrays.asList(line.split(DELIM));
        if (parts.size() != 3) {
          log.warning("Invalid line format in db");
        } else {
          if (parts.get(0).equals(searchRequest.getTitle())) {
            price = parts.get(1);
            path = parts.get(2);
            break;
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return buildSearchResult(price, path, searchRequest);
  }

  private SearchResult buildSearchResult(String price, String path, SearchRequest searchRequest) {
    SearchResult result = SearchResult.builder()
      .success(nonNull(price))
      .price(price)
      .path(path)
      .build();
    if (searchRequest instanceof WorkerSearchRequest) {
      result.setWorkerRef(searchRequest.getActorRef());
    } else {
      result.setClientRef(searchRequest.getActorRef());
    }
    return result;
  }
}
