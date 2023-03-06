package com.mucciolo
package repository

import config.{DatabaseConfig, PersistenceConfig}
import util.DatabaseMigrator

import akka.stream.alpakka.slick.scaladsl.SlickSession
import com.dimafeng.testcontainers.DockerComposeContainer
import com.dimafeng.testcontainers.scalatest.TestContainerForAll
import org.scalatest.BeforeAndAfter
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AsyncWordSpec
import slick.jdbc.JdbcBackend

trait RepositoryTest extends AsyncWordSpec
with Matchers with TestContainerForAll with BeforeAndAfter {

  override val containerDef: DockerComposeContainer.Def = PostgresContainer.Def
  private val databaseConfig = DatabaseConfig("jdbc:postgresql://localhost:5433/test", "test", "test")
  private val persistenceConfig = PersistenceConfig("slick.jdbc.PostgresProfile$", databaseConfig)
  protected val slickSession: SlickSession = SlickSession.forConfig(persistenceConfig.asRawCamelCase)
  protected val db: JdbcBackend#DatabaseDef = slickSession.db

  object TestTables extends Tables {
    override protected implicit val session: SlickSession = slickSession
  }

  before {
    DatabaseMigrator.migrate(persistenceConfig.db.asHikariConfig)
  }

}
