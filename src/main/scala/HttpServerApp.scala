package com.mucciolo

import config.Config
import core.impl.UserServiceImpl
import http.{HttpRoutes, HttpServer}
import repository.UserRepository
import repository.impl.SlickUserRepository
import util.DatabaseMigrator

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.server.Route
import akka.stream.alpakka.slick.scaladsl.SlickSession

import scala.concurrent.ExecutionContext

object HttpServerApp extends App {

  Config.parse(onSuccess = migrateDatabase _ andThen boot)

  private def migrateDatabase(config: Config): Config = {
    DatabaseMigrator.migrate(config.persistence.db.asHikariConfig)
    config
  }

  private def boot(config: Config): Unit = {

    implicit val system: ActorSystem[_] = ActorSystem(Behaviors.empty, "akka-http-stream-slick")
    implicit val ec: ExecutionContext = system.executionContext

    val slickSession: SlickSession = SlickSession.forConfig(config.persistence.asRawCamelCase)
    system.whenTerminated.map(_ => slickSession.close())

    val userRepository: UserRepository = new SlickUserRepository()(slickSession)
    val userService = new UserServiceImpl(userRepository)
    val routes: Route = HttpRoutes(userService)

    HttpServer.runForever(config.server, routes)

  }
}
