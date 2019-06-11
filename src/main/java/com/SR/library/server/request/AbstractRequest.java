package com.SR.library.server.request;

import akka.actor.ActorRef;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
public abstract class AbstractRequest implements Serializable {

  private ActorRef actorRef;
  private String title;

}
