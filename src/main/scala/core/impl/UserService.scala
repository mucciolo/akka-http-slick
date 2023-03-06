package com.mucciolo
package core.impl

import core.Domain.User
import core.UserService
import repository.UserRepository

import scala.concurrent.Future

final class UserServiceImpl(userRepository: UserRepository) extends UserService {
  def listUsers(): Future[Seq[User]] = {
    userRepository.listUsers()
  }
}
