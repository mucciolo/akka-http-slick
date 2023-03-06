package com.mucciolo
package http.server

import config.ServerConfig

import akka.actor.typed.ActorSystem
import akka.http.scaladsl.{Http, server}

import scala.concurrent.duration.DurationInt
import scala.util.{Failure, Success}

object HttpServer {

  def runForever(config: ServerConfig, route: server.Route)(implicit system: ActorSystem[_]): Unit = {

    Http()
      .newServerAt(config.host, config.port)
      .bind(route)
      .onComplete {

        case Success(binding) =>
          system.log.info("Server started on http://{}:{}",
            binding.localAddress.getHostName, binding.localAddress.getPort)
          binding.addToCoordinatedShutdown(10.seconds)

        case Failure(exception) =>
          system.log.error(exception.getMessage)
          system.terminate()

      }(system.executionContext)

  }

}
