package io.eventuate.examples.tram.sagas.ordersandcustomers.customers.service;

import io.eventuate.examples.tram.sagas.ordersandcustomers.customers.api.messaging.commands.ReserveCreditCommand;
import io.eventuate.examples.tram.sagas.ordersandcustomers.customers.api.messaging.commands.UpdateCreditCommand;
import io.eventuate.examples.tram.sagas.ordersandcustomers.customers.api.messaging.replies.CustomerCreditLimitExceeded;
import io.eventuate.examples.tram.sagas.ordersandcustomers.customers.api.messaging.replies.CustomerCreditReserved;
import io.eventuate.examples.tram.sagas.ordersandcustomers.customers.api.messaging.replies.CustomerNotFound;
import io.eventuate.examples.tram.sagas.ordersandcustomers.customers.domain.CustomerCreditLimitExceededException;
import io.eventuate.examples.tram.sagas.ordersandcustomers.customers.domain.CustomerNotFoundException;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withFailure;
import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;

public class CustomerCommandHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

  private CustomerService customerService;

  public CustomerCommandHandler(CustomerService customerService) {
    this.customerService = customerService;
  }

  public CommandHandlers commandHandlerDefinitions() {
    return SagaCommandHandlersBuilder
            .fromChannel("customerService")
            .onMessage(ReserveCreditCommand.class, this::reserveCredit)
            .onMessage(UpdateCreditCommand.class, this::updateCredit)
            .build();
  }

  public Message reserveCredit(CommandMessage<ReserveCreditCommand> cm) {
    ReserveCreditCommand cmd = cm.getCommand();
    try {
      customerService.reserveCredit(cmd.getCustomerId(), cmd.getOrderId(), cmd.getOrderTotal());
      return withSuccess(new CustomerCreditReserved());
    } catch (CustomerNotFoundException e) {
      return withFailure(new CustomerNotFound());
    } catch (CustomerCreditLimitExceededException e) {
      return withFailure(new CustomerCreditLimitExceeded());
    }
  }

  public Message updateCredit(CommandMessage<UpdateCreditCommand> cm) {
	  logger.info("--- Received update credit command {}", cm.getCommand());
      return withSuccess(new CustomerCreditReserved());
  }

}
