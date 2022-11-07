package io.eventuate.examples.tram.sagas.ordersandcustomers.orders.api.web;


import io.eventuate.examples.tram.sagas.ordersandcustomers.commondomain.Money;

public class UpdateOrderRequest {
  private Money orderTotal;
  private Long customerId;

  public UpdateOrderRequest() {
  }

  public UpdateOrderRequest(Long customerId, Money orderTotal) {
    this.customerId = customerId;
    this.orderTotal = orderTotal;
  }

  public Money getOrderTotal() {
    return orderTotal;
  }

  public Long getCustomerId() {
    return customerId;
  }
}
