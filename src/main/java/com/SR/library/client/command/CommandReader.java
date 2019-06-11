package com.SR.library.client.command;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandReader {

  private static final Pattern COMMAND_PATTERN = Pattern.compile("(?<type>(order|search|stream)) (?<title>.+)");

  private final BufferedReader reader;

  public CommandReader(InputStream in) {
    reader = new BufferedReader(new InputStreamReader(in));
  }

  public Command nextCommand() {
    try {
      while (true) {
        String line = reader.readLine();
        if (line.equals("exit")) {
          return new Command(Command.CommandType.EXIT, null);
        }
        Matcher matcher = COMMAND_PATTERN.matcher(line);
        if (!matcher.matches()) {
          System.out.println("Unrecognised command");
          continue;
        }
        Command.CommandType commandType = Command.CommandType.valueOf(matcher.group("type").toUpperCase());
        String title = matcher.group("title");
        return new Command(commandType, title);
      }

    } catch (IOException e) {
      e.printStackTrace();
      System.exit(-1);
    }
    return null;
  }


}
