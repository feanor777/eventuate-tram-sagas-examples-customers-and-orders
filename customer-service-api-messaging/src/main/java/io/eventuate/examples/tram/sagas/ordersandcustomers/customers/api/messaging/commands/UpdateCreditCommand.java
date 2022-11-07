package io.eventuate.examples.tram.sagas.ordersandcustomers.customers.api.messaging.commands;

import io.eventuate.examples.tram.sagas.ordersandcustomers.commondomain.Money;
import io.eventuate.tram.commands.common.Command;

public class UpdateCreditCommand implements Command {
  private Long orderId;
  private Money orderTotal;
  private long customerId;

  public UpdateCreditCommand() {
  }

  public UpdateCreditCommand(Long customerId, Long orderId, Money orderTotal) {
    this.customerId = customerId;
    this.orderId = orderId;
    this.orderTotal = orderTotal;
  }

  public Money getOrderTotal() {
    return orderTotal;
  }

  public void setOrderTotal(Money orderTotal) {
    this.orderTotal = orderTotal;
  }

  public Long getOrderId() {

    return orderId;
  }

  public void setOrderId(Long orderId) {

    this.orderId = orderId;
  }

  public long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(long customerId) {
    this.customerId = customerId;
  }

}
