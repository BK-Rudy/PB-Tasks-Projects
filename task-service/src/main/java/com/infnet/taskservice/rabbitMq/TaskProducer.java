package com.infnet.taskservice.rabbitMq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.infnet.taskservice.model.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class    TaskProducer {
    private final AmqpTemplate amqp;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .setDateFormat(new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss"));

    public void send(Task task) throws JsonProcessingException {
        try {
            String taskJson = objectMapper.writeValueAsString(task);
            log.debug("Tarefa serializada para JSON: {}", taskJson);

            amqp.convertAndSend("task-exc", "task-rk", taskJson);
            log.info("Tarefa enviada com sucesso para o RabbitMQ com a chave de roteamento 'task-rk'");
        } catch (JsonProcessingException e) {
            log.error("Falha ao serializar a tarefa: {}", task, e);
            throw e;
        }
    }
}
