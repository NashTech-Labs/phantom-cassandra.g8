package edu.knoldus.knolx.cassandra

import java.util.UUID
import scala.concurrent.Future
import com.outworkers.phantom.dsl._
import org.joda.time.DateTime

case class Employee(id: UUID, email: String, name: String, joiningDate: DateTime)

abstract class Employees extends Table[Employees, Employee] with RootConnector {
  object id extends UUIDColumn(this) with PartitionKey
  object email extends StringColumn(this)
  object name extends StringColumn(this)
  object joiningDate extends DateTimeColumn(this)

  def getById(id: UUID): Future[Option[Employee]] = {
    select.where(_.id eqs id).one()
  }

  def getNameById(id: UUID): Future[Option[String]] = {
    select(_.name).where(_.id eqs id).one()
  }

  def getNameAndEmailById(id: UUID): Future[Option[(String, String)]] = {
    select(_.name, _.email).where(_.id eqs id).one()
  }

  def getAll(): Future[List[Employee]] = {
    select.fetch()
  }

  def getByIds(ids: List[UUID]): Future[List[Employee]] = {
    select.where(_.id in ids).fetch()
  }

  def getNameByIds(ids: List[UUID]): Future[List[String]] = {
    select(_.name).where(_.id in ids).fetch()
  }

  def getNameAndEmailByIds(ids: List[UUID]): Future[List[(String, String)]] = {
    select(_.name, _.email).where(_.id in ids).fetch()
  }

  def getCount(): Future[Option[Long]] = {
    select.count().one()
  }

  def updateEmail(id: UUID, email: String): Future[ResultSet] = {
    update.where(_.id eqs id).modify(_.email setTo email).future()
  }
}
