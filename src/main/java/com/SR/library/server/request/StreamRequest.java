package com.SR.library.server.request;

import akka.actor.ActorRef;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class StreamRequest extends AbstractRequest implements Serializable {

  public StreamRequest(ActorRef actorRef, String title) {
    super(actorRef, title);
  }
}
