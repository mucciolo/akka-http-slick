package com.mucciolo
package core

import java.util.UUID

object Domain {
  final case class User(name: String, email: String, id: Option[UUID] = None)
}
