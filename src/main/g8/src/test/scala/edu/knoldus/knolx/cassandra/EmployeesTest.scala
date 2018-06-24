package edu.knoldus.knolx.cassandra

import java.util.UUID

import org.joda.time.DateTime

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.{DurationInt, FiniteDuration}

class EmployeesTest extends TestSuite {
  private val truncateTimeout: FiniteDuration = 10 seconds
  private val employee1 = Employee(UUID.randomUUID(), "employee1@knolx.edu", "employee1", DateTime.now())
  private val employee2 = Employee(UUID.randomUUID(), "employee2@knolx.edu", "employee2", DateTime.now())

  it should "insert a new record in the employees table and retrieve it" in {
    database.truncate(truncateTimeout)
    val result = for {
      _ <- database.employees.store(employee1).future()
      get <- database.employees.getById(employee1.id)
    } yield get

    result.map(res => assert(res.isDefined && res.get.name === employee1.name))
  }

  it should "insert a new record in the employees table and retrieve name" in {
    database.truncate(truncateTimeout)

    val result = for {
      _ <- database.employees.store(employee1).future()
      get <- database.employees.getNameById(employee1.id)
    } yield get

    result.map(res => assert(res.isDefined && res.get === employee1.name))
  }

  it should "insert a new record in the employees table and retrieve name and email" in {
    database.truncate(truncateTimeout)

    val result = for {
      _ <- database.employees.store(employee1).future()
      get <- database.employees.getNameAndEmailById(employee1.id)
    } yield get

    result.map(res => {
      assert(res.isDefined && res.get._1 === employee1.name && res.get._2 === employee1.email)
    })
  }

  it should "insert a new record in the employees table and retrieve all" in {
    database.truncate(truncateTimeout)

    val result = for {
      _ <- database.employees.store(employee1).future()
      _ <- database.employees.store(employee2).future()
      get <- database.employees.getAll()
    } yield get

    result.map(res => assert(res.size === 2))
  }

  it should "insert new records in the employees table and retrieve them by ids" in {
    database.truncate(truncateTimeout)

    val result = for {
      _ <- database.employees.store(employee1).future()
      _ <- database.employees.store(employee2).future()
      get <- database.employees.getByIds(List(employee1.id, employee2.id))
    } yield get

    result.map(res => assert(res.size === 2))
  }

  it should "insert new records in the employees table and retrieve name by ids" in {
    database.truncate(truncateTimeout)

    val result = for {
      _ <- database.employees.store(employee1).future()
      _ <- database.employees.store(employee2).future()
      get <- database.employees.getNameByIds(List(employee1.id, employee2.id))
    } yield get

    result.map(res => {
      assert(res.size === 2 && res.contains(employee1.name) && res.contains(employee2.name))
    })
  }

  it should "insert new records in the employees table and retrieve name and email by ids" in {
    database.truncate(truncateTimeout)

    val result = for {
      _ <- database.employees.store(employee1).future()
      _ <- database.employees.store(employee2).future()
      get <- database.employees.getNameAndEmailByIds(List(employee1.id, employee2.id))
    } yield get

    result.map(res => {
      assert(res.size === 2 &&
        res.contains((employee1.name, employee1.email)) &&
        res.contains((employee2.name, employee2.email)))
    })
  }

  it should "insert new records in the employees table and retrieve count" in {
    database.truncate(truncateTimeout)

    val result = for {
      _ <- database.employees.store(employee1).future()
      _ <- database.employees.store(employee2).future()
      get <- database.employees.getCount()
    } yield get

    result.map(res => assert(res.isDefined && res.get === 2L))
  }

  it should "insert new records in the employees table and update it" in {
    database.truncate(truncateTimeout)

    val newEmail = "new@knolx.edu"
    val result = for {
      _ <- database.employees.store(employee1).future()
      _ <- database.employees.updateEmail(employee1.id, newEmail)
      get <- database.employees.getById(employee1.id)
    } yield get

    result.map(res => assert(res.isDefined && res.get.email === newEmail))
  }
}
