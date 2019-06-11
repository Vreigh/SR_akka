package com.SR.library.client;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.SR.library.client.command.Command;
import com.SR.library.client.command.CommandReader;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class Client extends AbstractActor {

  private static final String CLIENT_SYSTEM_NAME = "library";
  private static final String SERVER_ADDRESS = "akka://library@127.0.0.1:2552/user/server";
  private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);


  @Override
  public Receive createReceive() {
    return receiveBuilder().match(Command.class, command -> {
      switch (command.getCommandType()) {
        case ORDER:
          System.out.println(command.getTitle());
          break;
        case SEARCH:
          break;
        case STREAM:
          break;
      }
    }).build();
  }

  public static Props props() {
    return Props.create(Client.class, Client::new);
  }

  public static void main(String[] args) {
    Config config = ConfigFactory.load(Client.class.getClassLoader(), "Client");
    final ActorSystem system = ActorSystem.create(CLIENT_SYSTEM_NAME, config);
    ActorRef client = system.actorOf(Client.props(), "client");

    CommandReader commandReader = new CommandReader(System.in);
    while (true) {
      Command command = commandReader.nextCommand();
      if (command.getCommandType() == Command.CommandType.EXIT) {
        system.terminate();
        return;
      } else {
        client.tell(command, ActorRef.noSender());
      }
    }

  }


}
