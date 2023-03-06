package com.mucciolo
package util

import org.slf4j.{Logger, LoggerFactory}

trait Log {
  protected implicit val log: Logger = LoggerFactory.getLogger(getClass)
}
