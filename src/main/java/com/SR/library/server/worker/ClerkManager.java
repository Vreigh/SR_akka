package com.SR.library.server.worker;

import akka.actor.AbstractActor;
import akka.actor.Props;
import com.SR.library.server.request.SearchRequest;
import com.SR.library.server.response.ResponseStatus;
import com.SR.library.server.response.SearchResponse;

public class ClerkManager extends AbstractActor {

  @Override
  public Receive createReceive() {
    return receiveBuilder().match(SearchRequest.class, request -> {
      System.out.println("clerk called");
      SearchResponse response = new SearchResponse(ResponseStatus.OK, "12.76");
      request.getActorRef().tell(response, context().parent());
    }).build();
  }

  public static Props props() {
    return Props.create(ClerkManager.class, ClerkManager::new);
  }

}
