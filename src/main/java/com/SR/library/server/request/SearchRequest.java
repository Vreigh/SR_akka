package com.SR.library.server.request;

import akka.actor.ActorRef;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SearchRequest extends AbstractRequest implements Serializable {

  public SearchRequest(ActorRef actorRef, String title) {
    super(actorRef, title);
  }
}
