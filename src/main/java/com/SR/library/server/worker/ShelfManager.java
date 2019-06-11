package com.SR.library.server.worker;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class ShelfManager extends AbstractActor {

  @Override
  public Receive createReceive() {
    return receiveBuilder().match(Object.class, ob -> {
      System.out.println("shelf called");
    }).build();
  }

  public static Props props() {
    return Props.create(ShelfManager.class, ShelfManager::new);
  }

}
