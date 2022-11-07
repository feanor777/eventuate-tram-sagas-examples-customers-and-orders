package io.eventuate.examples.tram.sagas.ordersandcustomers.orders.service;

import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.api.messaging.common.OrderDetails;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.api.messaging.sagas.createorder.UpdateOrderSagaData;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.domain.Order;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.domain.OrderRepository;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.api.messaging.sagas.createorder.CreateOrderSagaData;
import io.eventuate.tram.sagas.orchestration.SagaInstanceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class OrderSagaService {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private SagaInstanceFactory sagaInstanceFactory;

  @Autowired
  private CreateOrderSaga createOrderSaga;

  @Autowired
  private UpdateOrderSaga updateOrderSaga;

  public OrderSagaService(OrderRepository orderRepository, SagaInstanceFactory sagaInstanceFactory, CreateOrderSaga createOrderSaga, UpdateOrderSaga updateOrderSaga) {
    this.orderRepository = orderRepository;
    this.sagaInstanceFactory = sagaInstanceFactory;
    this.createOrderSaga = createOrderSaga;
    this.updateOrderSaga = updateOrderSaga;
  }

  @Transactional
  public Order createOrder(OrderDetails orderDetails) {
    CreateOrderSagaData data = new CreateOrderSagaData(orderDetails);
    sagaInstanceFactory.create(createOrderSaga, data);
    return orderRepository.findById(data.getOrderId()).get();
  }

  @Transactional
  public Order updateOrder(OrderDetails orderDetails, Long orderId) {
    UpdateOrderSagaData data = new UpdateOrderSagaData(orderDetails);
    data.setOrderId(orderId);
    sagaInstanceFactory.create(updateOrderSaga, data);
    return orderRepository.findById(data.getOrderId()).get();
  }
}
