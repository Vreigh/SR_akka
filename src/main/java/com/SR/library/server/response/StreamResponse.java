package com.SR.library.server.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StreamResponse extends AbstractResponse {

  private String sentence;

  public StreamResponse(ResponseStatus status, String sentence) {
    super(status);
    this.sentence = sentence;
  }

  @Override
  public String show() {
    return sentence;
  }
}
