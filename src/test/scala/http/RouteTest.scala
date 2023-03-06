package com.mucciolo
package http

import http.json.JsonProtocols

import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalamock.scalatest.MockFactory
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

trait RouteTest extends AnyWordSpec
  with Matchers with ScalatestRouteTest with MockFactory with JsonProtocols {}
