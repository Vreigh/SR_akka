package com.SR.library.server.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchResponse extends AbstractResponse {

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
