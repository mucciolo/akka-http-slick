package com.mucciolo
package repository

import com.dimafeng.testcontainers.DockerComposeContainer.ComposeFile
import com.dimafeng.testcontainers.{DockerComposeContainer, ExposedService}
import org.testcontainers.containers.wait.strategy.Wait

import java.io.File

object PostgresContainer {
  val Def: DockerComposeContainer.Def =
    DockerComposeContainer.Def(
      ComposeFile(Left(new File("src/it/resources/postgres-docker-compose.yml"))),
      tailChildContainers = true,
      exposedServices = Seq(
        ExposedService(
          "postgres",
          5433,
          Wait.forLogMessage(".*database system is ready to accept connections.*", 2)
        )
      )
    )
}
