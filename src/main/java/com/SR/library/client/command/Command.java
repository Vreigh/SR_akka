package com.SR.library.client.command;

import lombok.Data;

@Data
public class Command {

  private final CommandType commandType;
  private final String title;

  public enum CommandType {
    ORDER, SEARCH, STREAM, EXIT
  }
}
