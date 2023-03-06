package com.mucciolo
package core

import core.Domain.User

import scala.concurrent.Future

trait UserService {
  def listUsers(): Future[Seq[User]]
}
