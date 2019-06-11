package com.SR.library.server.request;

import akka.actor.ActorRef;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest extends AbstractRequest {

  public OrderRequest(ActorRef actorRef, String title) {
    super(actorRef, title);
  }
}
