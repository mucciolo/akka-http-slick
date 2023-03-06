package com.mucciolo
package http

import core.Domain.User
import core.UserService
import http.routes.UserRoutes

import akka.http.scaladsl.model.StatusCodes.OK

import java.util.UUID.randomUUID
import scala.concurrent.Future

final class UserRoutesSpec extends RouteTest {

  private val userService = mock[UserService]
  private val userRoutes = UserRoutes.build(userService)

  "UserRoutes" when {

    "GET /users" should {
      "return 200" in {

        val expectedUsers = Seq(
          User("User One", "1@user.com", Some(randomUUID())),
          User("User Two", "2@user.com", Some(randomUUID()))
        )

        (userService.listUsers _).expects().returns(Future.successful(expectedUsers))

        Get("/users") ~> userRoutes ~> check {
          response.status shouldBe OK
          responseAs[Seq[User]] shouldBe expectedUsers
        }
      }
    }

  }
}
