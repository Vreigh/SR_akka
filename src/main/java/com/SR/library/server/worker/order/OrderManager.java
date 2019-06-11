package com.SR.library.server.worker.order;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class OrderManager extends AbstractActor {

  @Override
  public Receive createReceive() {
    return receiveBuilder().match(Object.class, ob -> {
      System.out.println("order called");
    }).build();
  }

  public static Props props() {
    return Props.create(OrderManager.class, OrderManager::new);
  }

}
