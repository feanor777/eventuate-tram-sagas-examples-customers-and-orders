package io.eventuate.examples.tram.sagas.ordersandcustomers.orders.api.messaging.sagas.createorder;

import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.api.messaging.common.OrderDetails;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.api.messaging.common.RejectionReason;

public class UpdateOrderSagaData {

  private OrderDetails orderDetails;
  private Long orderId;
  private RejectionReason rejectionReason;

  public UpdateOrderSagaData(OrderDetails orderDetails) {
    this.orderDetails = orderDetails;
  }

  public UpdateOrderSagaData() {
  }

  public Long getOrderId() {
    return orderId;
  }

  public OrderDetails getOrderDetails() {
    return orderDetails;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public void setRejectionReason(RejectionReason rejectionReason) {
    this.rejectionReason = rejectionReason;
  }

  public RejectionReason getRejectionReason() {
    return rejectionReason;
  }
}
