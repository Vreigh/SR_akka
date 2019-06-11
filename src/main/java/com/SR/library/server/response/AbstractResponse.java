package com.SR.library.server.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public abstract class AbstractResponse implements Serializable {

  private ResponseStatus status;

  public String getLogInfo() {
    return this.toString();
  }

  public abstract String show();
}
