package edu.knoldus.knolx.cassandra

object Config {
  val cassandraHosts = Option(System.getenv("CASSANDRA_HOSTS")).getOrElse("localhost")
  val keyspace = Option(System.getenv("CASSANDRA_KEYSPACE")).getOrElse("knolx")
}
