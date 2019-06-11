package com.SR.library.server.request;

import akka.actor.ActorRef;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequest extends AbstractRequest {

  public SearchRequest(ActorRef actorRef, String title) {
    super(actorRef, title);
  }
}
