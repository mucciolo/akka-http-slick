package com.mucciolo
package http.json

import core.Domain.User

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._

import java.util.UUID

trait JsonProtocols extends SprayJsonSupport with DefaultJsonProtocol {

  implicit object UuidFormat extends JsonFormat[UUID] {

    def write(uuid: UUID): JsValue = JsString(uuid.toString)

    def read(value: JsValue): UUID = value match {
      case JsString(uuid) => UUID.fromString(uuid)
      case _ => deserializationError("UUID expected")
    }

  }

  implicit val userJsonFormat: RootJsonFormat[User] = jsonFormat3(User.apply)

}
