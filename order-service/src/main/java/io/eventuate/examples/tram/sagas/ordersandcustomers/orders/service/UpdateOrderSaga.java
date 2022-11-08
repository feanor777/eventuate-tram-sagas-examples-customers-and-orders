package io.eventuate.examples.tram.sagas.ordersandcustomers.orders.service;

import io.eventuate.examples.tram.sagas.ordersandcustomers.commondomain.Money;
import io.eventuate.examples.tram.sagas.ordersandcustomers.customers.api.messaging.commands.UpdateCreditCommand;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.api.messaging.sagas.createorder.UpdateOrderSagaData;
import io.eventuate.tram.commands.consumer.CommandWithDestination;
import io.eventuate.tram.sagas.orchestration.SagaDefinition;
import io.eventuate.tram.sagas.simpledsl.SimpleSaga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.eventuate.tram.commands.consumer.CommandWithDestinationBuilder.send;

public class UpdateOrderSaga implements SimpleSaga<UpdateOrderSagaData> {

  private Logger logger = LoggerFactory.getLogger(getClass());

  private SagaDefinition<UpdateOrderSagaData> sagaDefinition =
          step()
            .invokeLocal(this::validate)
          .step()
            .invokeLocal(this::update)
          .step()
            .invokeParticipant(this::updateCredit)
          .step()
            .invokeLocal(this::approveUpdate)
          .build();

  @Override
  public String getSagaType() {
    return "update-order-saga";
  }

  @Override
  public SagaDefinition<UpdateOrderSagaData> getSagaDefinition() {
    return this.sagaDefinition;
  }

  private CommandWithDestination updateCredit(UpdateOrderSagaData data) {
    long orderId = data.getOrderId();
    Long customerId = data.getOrderDetails().getCustomerId();
    Money orderTotal = data.getOrderDetails().getOrderTotal();
    return send(new UpdateCreditCommand(customerId, orderId, orderTotal))
            .to("customerService")
            .build();
  }

  private void validate(UpdateOrderSagaData updateOrderSagaData) {
    logger.info("--- Validated! ---");
  }

  private void update(UpdateOrderSagaData data) {
    logger.info("--- Updated! ---");
  }

  private void approveUpdate(UpdateOrderSagaData createOrderSagaData) {
    logger.info("--- Update Approved! ---");
  }
}
