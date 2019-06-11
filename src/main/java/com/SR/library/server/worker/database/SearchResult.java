package com.SR.library.server.worker.database;

import java.io.Serializable;

import akka.actor.ActorRef;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SearchResult implements Serializable {

  private ActorRef clientRef;
  private ActorRef workerRef;
  private boolean success;
  private String price;
  private String path;

}
