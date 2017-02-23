package com.ovoenergy.serialization.kafka.client.consumer

import akka.util.Timeout
import com.ovoenergy.serialization.kafka.client.util.DurationUtils
import com.typesafe.config.Config

import scala.concurrent.duration.FiniteDuration

private[consumer] case class ConsumerConfig(initialDelay: FiniteDuration,
                                            interval: FiniteDuration,
                                            topics: Seq[String],
                                            clientId: String,
                                            gropuId: String,
                                            pollingTimeout: Long,
                                            consumerName: String,
                                            askTimeout: Timeout)

object ConsumerConfig extends DurationUtils {

  def apply(config: Config, consumerName: String, clientId: String, topics: String*): ConsumerConfig = {
    val initialDelay = getFiniteDuration(config.getString("kafka.consumer.initialDelay"))
    val interval = getFiniteDuration(config.getString("kafka.consumer.interval"))
    val groupId = config.getString("kafka.consumer.properties.group.id")
    val pollingTimeout = config.getLong("kafka.consumer.pollingTimeoutMs")
    val askTimeout = new Timeout(getFiniteDuration(config.getString("kafka.consumer.askTimeout")))
    ConsumerConfig(initialDelay, interval, topics, clientId, groupId, pollingTimeout, consumerName, askTimeout)
  }

}
