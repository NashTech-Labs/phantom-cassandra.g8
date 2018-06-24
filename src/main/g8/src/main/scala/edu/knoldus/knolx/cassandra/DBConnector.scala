package edu.knoldus.knolx.cassandra

import com.outworkers.phantom.connectors.{CassandraConnection, ContactPoints}

object DBConnector {
  import Config._

  private var connector: CassandraConnection = _

  def getOrCreate(): CassandraConnection = {
    val hosts = cassandraHosts.split(",").map(_.trim).toSeq
    connector = ContactPoints(hosts).keySpace(keyspace)
    connector
  }
}
