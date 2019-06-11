package com.SR.library.server.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchResponse extends AbstractResponse implements Serializable {

  private String price;

  public SearchResponse(ResponseStatus status, String price) {
    super(status);
    this.price = price;
  }

  @Override
  public String show() {
    return price;
  }
}
