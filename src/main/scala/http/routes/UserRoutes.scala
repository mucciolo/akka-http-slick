package com.mucciolo
package http.routes

import core.UserService
import http.json.JsonProtocols

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

object UserRoutes extends JsonProtocols {

  def build(userService: UserService): Route =
    pathPrefix("users") {
      concat(
        pathEnd {
          concat(
            get {
              complete(
                userService.listUsers()
              )
            }
          )
        }
      )
    }
}
