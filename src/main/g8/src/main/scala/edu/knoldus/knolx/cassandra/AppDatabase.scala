package edu.knoldus.knolx.cassandra

import com.outworkers.phantom.connectors.CassandraConnection
import com.outworkers.phantom.dsl._

class AppDatabase(override val connector: CassandraConnection) extends Database[AppDatabase](connector) {
  object employees extends Employees with Connector
}

object AppDatabase extends AppDatabase(DBConnector.getOrCreate())
