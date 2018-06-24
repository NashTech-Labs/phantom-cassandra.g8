package edu.knoldus.knolx.cassandra

import org.cassandraunit.utils.EmbeddedCassandraServerHelper
import org.scalatest.{AsyncFlatSpec, BeforeAndAfterAll}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt

trait TestSuite extends AsyncFlatSpec with AppDbProvider with BeforeAndAfterAll {
  override def beforeAll(): Unit = {
    super.beforeAll()
    EmbeddedCassandraServerHelper.startEmbeddedCassandra("test-cassandra.yaml", 1000000L)
    database.create(100 seconds)
  }
}
