package com.mucciolo
package util

import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import org.flywaydb.core.Flyway
import org.flywaydb.core.api.output.MigrateResult

import scala.util.Using

object DatabaseMigrator {

  def migrate(hikariConfig: HikariConfig): MigrateResult = {
    Using(new HikariDataSource(hikariConfig)) { migrationDataSource =>
      Flyway.configure().dataSource(migrationDataSource).load().migrate()
    }.get
  }

}
