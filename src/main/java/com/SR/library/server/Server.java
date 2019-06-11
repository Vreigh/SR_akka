package com.SR.library.server;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.SR.library.server.request.OrderRequest;
import com.SR.library.server.request.SearchRequest;
import com.SR.library.server.request.StreamRequest;
import com.SR.library.server.worker.database.DatabaseManager;
import com.SR.library.server.worker.order.OrderManager;
import com.SR.library.server.worker.stream.StreamManager;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@AllArgsConstructor
public class Server extends AbstractActor {

  private static final String SERVER_SYSTEM_NAME = "library";

  private final ActorRef orderManager;
  private final ActorRef databaseManager;
  private final ActorRef streamManager;

  public static Props props(ActorRef orderManager, ActorRef databaseManager, ActorRef streamManager) {
    return Props.create(Server.class, () -> new Server(orderManager, databaseManager, streamManager));
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder().match(OrderRequest.class, orderRequest -> {
      orderManager.tell(orderRequest, self());
    }).match(SearchRequest.class, searchRequest -> {
      databaseManager.tell(searchRequest, self());
    }).match(StreamRequest.class, streamRequest -> {
      streamManager.tell(streamRequest, self());
    }).build();
  }

  public static void main(String[] args) {
    Config config = ConfigFactory.load(Server.class.getClassLoader(), "server.conf");
    final ActorSystem system = ActorSystem.create(SERVER_SYSTEM_NAME, config);
    ActorRef orderManager = system.actorOf(OrderManager.props(), "orderManager");
    ActorRef databaseManager = system.actorOf(DatabaseManager.props(), "databaseManager");
    ActorRef streamManager = system.actorOf(StreamManager.props(), "streamManager");
    ActorRef server = system.actorOf(Server.props(orderManager, databaseManager, streamManager), "server");

    System.out.println("Server ready");

    try {
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
      while (!bufferedReader.readLine().equals("exit")) ;
    } catch (Exception e) {
      e.printStackTrace();
    }

    System.out.println("Terminating system");
    system.terminate();

  }


}
