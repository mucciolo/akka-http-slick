package com.mucciolo
package http

import core.UserService

import akka.http.scaladsl.model.StatusCodes.OK

final class HttpRoutesSpec extends RouteTest {

  private val userService = mock[UserService]
  private val routes = HttpRoutes(userService)

  "HttpRoutes" when {

    "GET /ready" should {
      "return 200" in {
        Get("/ready") ~> routes ~> check {
          response.status shouldBe OK
          responseAs[String] shouldBe "OK"
        }
      }
    }

  }
}
