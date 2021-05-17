package io.konekto.backoffice.service.message;

import io.konekto.backoffice.config.properties.BackOfficeProperties;
import io.konekto.backoffice.domain.enumration.VerifyStatusEnum;
import io.konekto.backoffice.util.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RabbitBusinessProducer {

    private final Logger log = LoggerFactory.getLogger(RabbitBusinessProducer.class);

    @Autowired
    AmqpTemplate amqpTemplate;

    @Autowired
    BackOfficeProperties backOfficeProperties;

    public void send(String id, VerifyStatusEnum verifyStatus) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("_type_s", "client");
        map.put("is_verified_i", verifyStatus.equals(VerifyStatusEnum.ACTIVE) ? 1 : 0);
        map.put("status_s", verifyStatus.name());

        String exchange = backOfficeProperties.getRabbitmq().getExchange();
        String routingKey = backOfficeProperties.getRabbitmq().getBusinessRoutingKey();

        log.info(String.format("Rabbit push message update verifyStatus with exchange %s, routingKey %s and request data: %s", exchange, routingKey, JSONUtil.objToString(map)));
        amqpTemplate.convertAndSend(exchange, routingKey, map);
    }

}
