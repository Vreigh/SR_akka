package com.SR.library.server.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class AbstractResponse {

  private ResponseStatus status;

  public String getLogInfo() {
    return this.toString();
  }

  public abstract String show();
}
