package com.mucciolo
package repository

import core.Domain.User

import akka.stream.alpakka.slick.scaladsl.SlickSession
import slick.lifted.ProvenShape

import java.util.UUID

private[repository] trait Tables {

  protected implicit val session: SlickSession

  import session.profile.api._

  final class Users(_tableTag: Tag) extends Table[User](_tableTag, "users") {
    val id: Rep[UUID] = column[UUID]("id", O.PrimaryKey, O.AutoInc)
    val name: Rep[String] = column[String]("name", O.Length(255, varying = true))
    val email: Rep[String] = column[String]("email", O.Length(255, varying = true), O.Unique)

    override def * : ProvenShape[User] = (name, email, id.?) <> (User.tupled, User.unapply)
  }

  lazy val Users = TableQuery[Users]
}
