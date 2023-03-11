package com.mucciolo
package http

import core.UserService
import http.routes.UserRoutes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._

object HttpRoutes {

  def apply(userService: UserService): Route = {

    val userRoutes = UserRoutes.build(userService)

    cors() {
      concat(
        pathPrefix("api" / "v1") {
          userRoutes
        },
        pathPrefix("ready") {
          get {
            complete("OK")
          }
        }
      )
    }
  }
}