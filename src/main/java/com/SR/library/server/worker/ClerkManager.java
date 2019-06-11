package com.SR.library.server.worker;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class ClerkManager extends AbstractActor {

  @Override
  public Receive createReceive() {
    return receiveBuilder().match(Object.class, ob -> {
      System.out.println("clerk called");
    }).build();
  }

  public static Props props() {
    return Props.create(ClerkManager.class, ClerkManager::new);
  }

}
