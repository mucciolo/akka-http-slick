package com.mucciolo
package repository

import core.Domain.User
import repository.impl.SlickUserRepository

final class UserRepositorySpec extends RepositoryTest {

  import slickSession.profile.api._

  private val userRepository = new SlickUserRepository()(slickSession)

  after {
    db.run(TestTables.Users.delete)
  }

  "SlickUserRepository" when {
    "listUsers" should {
      "return all users" in {

        for {
          maybeUser1 <- db.run(TestTables.Users.returning(TestTables.Users).insertOrUpdate(User("1", "1@test.com")))
          maybeUser2 <- db.run(TestTables.Users.returning(TestTables.Users).insertOrUpdate(User("2", "2@test.com")))
          users <- userRepository.listUsers()
          actualUsers = users.map(Some(_))
          expectedUsers = Seq(maybeUser1, maybeUser2)
        } yield actualUsers shouldBe expectedUsers

      }
    }
  }
}
