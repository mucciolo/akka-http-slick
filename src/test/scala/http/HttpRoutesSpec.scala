package com.mucciolo
package http

import core.UserService

import akka.http.scaladsl.model.StatusCodes.OK

final class HttpRoutesSpec extends RouteTest {

  private val userService = mock[UserService]
  private val routes = HttpRoutes.build(userService)

  "HttpRoutes" when {

    "GET /healthcheck" should {
      "return 200" in {
        Get("/healthcheck") ~> routes ~> check {
          response.status shouldBe OK
          responseAs[String] shouldBe "OK"
        }
      }
    }

  }
}
