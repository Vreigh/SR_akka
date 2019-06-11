package com.SR.library.server.request;

import akka.actor.ActorRef;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public abstract class AbstractRequest {

  private ActorRef actorRef;
  private String title;

}
