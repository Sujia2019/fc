//package kafka;
//
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.*;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.TimeUnit;
//
//import static org.junit.Assert.assertTrue;
//
//@SpringBootTest
//public class KafkaTest {
//    @Autowired
//    private Listener listener;
//
//    @Autowired
//    private KafkaTemplate<Integer, String> template;
//
//    @Test
//    public void testSimple() throws Exception {
//        template.send("test2", 0, "fc");
//        template.flush();
//        assertTrue(this.listener.latch1.await(10, TimeUnit.SECONDS));
//    }
//
//    @Configuration
//    @EnableKafka
//    public class Config {
//
//        @Bean
//        ConcurrentKafkaListenerContainerFactory<Integer, String>
//        kafkaListenerContainerFactory() {
//            ConcurrentKafkaListenerContainerFactory<Integer, String> factory =
//                    new ConcurrentKafkaListenerContainerFactory<>();
//            factory.setConsumerFactory(consumerFactory());
//            return factory;
//        }
//
//        @Bean
//        public ConsumerFactory<Integer, String> consumerFactory() {
//            return new DefaultKafkaConsumerFactory<>(consumerConfigs());
//        }
//
//        @Bean
//        public Map<String, Object> consumerConfigs() {
//            Map<String, Object> props = new HashMap<>();
//            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, EmbeddedgetBrokersAsString());
//            return props;
//        }
//
//        @Bean
//        public Listener listener() {
//            return new Listener();
//        }
//
//        @Bean
//        public ProducerFactory<Integer, String> producerFactory() {
//            return new DefaultKafkaProducerFactory<>(producerConfigs());
//        }
//
//        @Bean
//        public Map<String, Object> producerConfigs() {
//            Map<String, Object> props = new HashMap<>();
//            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, embeddedKafka.getBrokersAsString());
//            return props;
//        }
//
//        @Bean
//        public KafkaTemplate<Integer, String> kafkaTemplate() {
//            return new KafkaTemplate<Integer, String>(producerFactory());
//        }
//
//    }
//    public class Listener {
//
//        private final CountDownLatch latch1 = new CountDownLatch(1);
//
//        @KafkaListener(id = "foo", topics = "annotated1")
//        public void listen1(String foo) {
//            this.latch1.countDown();
//        }
//
//    }
//}
