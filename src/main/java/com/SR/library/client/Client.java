package com.SR.library.client;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.SR.library.client.command.Command;
import com.SR.library.client.command.CommandReader;
import com.SR.library.server.request.OrderRequest;
import com.SR.library.server.request.SearchRequest;
import com.SR.library.server.response.AbstractResponse;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class Client extends AbstractActor {

  private static final String CLIENT_SYSTEM_NAME = "client";
  private static final String SERVER_ADDRESS = "akka.tcp://library@127.0.0.1:2552/user/server";
  private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);


  @Override
  public Receive createReceive() {
    return receiveBuilder().match(Command.class, command -> {
      switch (command.getCommandType()) {
        case ORDER:
          context().actorSelection(SERVER_ADDRESS).tell(new OrderRequest(self(), command.getTitle()), self());
          break;
        case SEARCH:
          context().actorSelection(SERVER_ADDRESS).tell(new SearchRequest(self(), command.getTitle()), self());
          break;
        case STREAM:
          context().actorSelection(SERVER_ADDRESS).tell("test123", self());
          break;
      }
    }).match(AbstractResponse.class, this::handleResponse).build();
  }

  private void handleResponse(AbstractResponse response) {
    log.info("Received response {}", response.getLogInfo());
    System.out.println(response.show());
  }

  public static Props props() {
    return Props.create(Client.class, Client::new);
  }

  public static void main(String[] args) {
    Config config = ConfigFactory.load("client.conf");
    final ActorSystem system = ActorSystem.create(CLIENT_SYSTEM_NAME, config);
    ActorRef client = system.actorOf(Client.props(), "client");

    CommandReader commandReader = new CommandReader(System.in);
    System.out.println("Awaiting commands");
    while (true) {
      Command command = commandReader.nextCommand();
      if (command.getCommandType() == Command.CommandType.EXIT) {
        System.out.println("Terminating system");
        system.terminate();
        return;
      } else {
        client.tell(command, ActorRef.noSender());
      }
    }

  }


}
