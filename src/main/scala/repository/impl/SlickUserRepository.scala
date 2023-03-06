package com.mucciolo
package repository.impl

import core.Domain.User
import repository.{Tables, UserRepository}

import akka.stream.alpakka.slick.scaladsl._

import scala.concurrent.Future

final class SlickUserRepository(implicit val session: SlickSession)
  extends UserRepository with Tables {

  import session.profile.api._

  private val db = session.db

  override def listUsers(): Future[Seq[User]] = {
    db.run(Users.result)
  }

}
