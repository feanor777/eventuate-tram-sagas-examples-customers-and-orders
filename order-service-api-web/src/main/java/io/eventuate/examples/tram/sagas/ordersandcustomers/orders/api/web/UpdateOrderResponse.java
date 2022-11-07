package io.eventuate.examples.tram.sagas.ordersandcustomers.orders.api.web;


public class UpdateOrderResponse {
  private long orderId;

  public UpdateOrderResponse() {
  }

  public UpdateOrderResponse(long orderId) {
    this.orderId = orderId;
  }

  public long getOrderId() {
    return orderId;
  }
}
