package com.mucciolo
package config

import util.Log

import com.typesafe.config.{ConfigFactory, Config => RawConfig}
import com.zaxxer.hikari.HikariConfig
import pureconfig.ConfigReader.Result
import pureconfig._
import pureconfig.error.ConfigReaderFailures
import pureconfig.generic.ProductHint
import pureconfig.generic.auto._

final case class Config(server: ServerConfig, persistence: PersistenceConfig)
final case class ServerConfig(host: String, port: Int)
final case class PersistenceConfig(profile: String, db: DatabaseConfig) {
  lazy val asRawCamelCase: RawConfig = {
    implicit def productHint[T]: ProductHint[T] = ProductHint[T](ConfigFieldMapping(CamelCase, CamelCase))
    ConfigFactory.parseString(ConfigWriter[PersistenceConfig].to(this).render())
  }
}
final case class DatabaseConfig(jdbcUrl: String, username: String, password: String) {
  lazy val asHikariConfig: HikariConfig = {
    val hikariConfig = new HikariConfig()

    hikariConfig.setJdbcUrl(jdbcUrl)
    hikariConfig.setUsername(username)
    hikariConfig.setPassword(password)

    hikariConfig
  }
}

object Config extends Log {

  private def load(): Result[Config] = ConfigSource.default.load[Config]

  def parse[T](onFailure: ConfigReaderFailures => T, onSuccess: Config => T): T = {
    Config.load().fold(onFailure, onSuccess)
  }

  def parse(onSuccess: Config => Unit): Unit = {
    parse(failures => log.error(failures.prettyPrint()), onSuccess)
  }
}
