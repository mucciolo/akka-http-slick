package com.mucciolo
package repository

import core.Domain.User

import scala.concurrent.Future

trait UserRepository {
  def listUsers(): Future[Seq[User]]
}
