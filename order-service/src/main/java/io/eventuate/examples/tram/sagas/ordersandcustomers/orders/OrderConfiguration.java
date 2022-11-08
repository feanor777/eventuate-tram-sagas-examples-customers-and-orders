package io.eventuate.examples.tram.sagas.ordersandcustomers.orders;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.eventuate.common.json.mapper.JSonMapper;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.domain.OrderRepository;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.service.CreateOrderSaga;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.service.OrderSagaService;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.service.OrderService;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.service.UpdateOrderSaga;
import io.eventuate.tram.sagas.orchestration.SagaInstanceFactory;
import io.eventuate.tram.spring.optimisticlocking.OptimisticLockingDecoratorConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;

@Configuration
@EnableJpaRepositories
@EnableAutoConfiguration
@Import(OptimisticLockingDecoratorConfiguration.class)
public class OrderConfiguration {

  @Bean
  public OrderSagaService orderSagaService(OrderRepository orderRepository, SagaInstanceFactory sagaInstanceFactory, CreateOrderSaga createOrderSaga, UpdateOrderSaga updateOrderSaga) {
    return new OrderSagaService(orderRepository, sagaInstanceFactory, createOrderSaga, updateOrderSaga);
  }

  @Bean
  public OrderService orderService(OrderRepository orderRepository) {
    return new OrderService(orderRepository);
  }

  @Bean
  public CreateOrderSaga createOrderSaga(OrderService orderService) {
    return new CreateOrderSaga(orderService);
  }

  @Bean
  public UpdateOrderSaga updateOrderSaga() {
    return new UpdateOrderSaga();
  }

  /**
   * To fix bug with Saga isn't able to serialize/deserialize java.time
   */
  @PostConstruct
  public void init() {
    JSonMapper.objectMapper.registerModule(new JavaTimeModule());
  }
}
