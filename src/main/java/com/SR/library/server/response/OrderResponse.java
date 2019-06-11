package com.SR.library.server.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrderResponse extends AbstractResponse implements Serializable {

  private String orderId;

  public OrderResponse(ResponseStatus status, String orderId) {
    super(status);
    this.orderId = orderId;
  }

  @Override
  public String show() {
    return orderId;
  }
}
