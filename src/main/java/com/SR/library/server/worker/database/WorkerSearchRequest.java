package com.SR.library.server.worker.database;

import com.SR.library.server.request.SearchRequest;

import akka.actor.ActorRef;

public class WorkerSearchRequest extends SearchRequest {

  public WorkerSearchRequest(ActorRef actorRef, String title) {
    super(actorRef, title);
  }

}
