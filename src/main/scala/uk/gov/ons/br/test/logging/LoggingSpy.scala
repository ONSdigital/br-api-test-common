package uk.gov.ons.br.test.logging

import java.util.concurrent.CopyOnWriteArrayList

import com.github.ghik.silencer.silent
import org.slf4j.event.EventConstants._
import org.slf4j.event.Level
import org.slf4j.event.Level._
import org.slf4j.helpers.MessageFormatter
import org.slf4j.{Logger, Marker}

case class LogEntry(level: Level, msg: String, t: Option[Throwable])

/*
 * A fake logger that captures log messages in a 'log' collection for subsequent inspection.
 *
 * This was implemented out of a desire for the implementation code to be able to take full advantage of slf4j's
 * higher performance string formatting, without logging-focused tests having to make assertions at such a low
 * level.  With this class invoking slf4j's formatting expansion - client tests are free to focus on the semantics
 * of logging i.e. that a log entry was created containing particular information (rather than a log entry was
 * built from a specific format and arguments).
 *
 * This implementation does not support slf4j markers.
 */
class LoggingSpy(name: String, val level: Level) extends Logger {
  val log = new CopyOnWriteArrayList[LogEntry]()

  private def addLogEntry(level: Level, msg: String): Unit = {
    log.add(LogEntry(level, msg, None))
    ()
  }

  private def addLogEntry(level: Level, msg: String, t: Throwable): Unit = {
    log.add(LogEntry(level, msg, Some(t)))
    ()
  }

  override def getName: String = name

  override def isTraceEnabled: Boolean = level == TRACE
  override def trace(msg: String): Unit = addLogEntry(TRACE, msg)
  override def trace(format: String, arg: AnyRef): Unit = addLogEntry(TRACE, MessageFormatter.format(format, arg).getMessage)
  override def trace(format: String, arg1: AnyRef, arg2: AnyRef): Unit = addLogEntry(TRACE, MessageFormatter.format(format, arg1, arg2).getMessage)
  override def trace(format: String, arguments: AnyRef*): Unit = addLogEntry(TRACE, MessageFormatter.arrayFormat(format, arguments.toArray).getMessage)
  override def trace(msg: String, t: Throwable): Unit = addLogEntry(TRACE, msg, t)

  override def isTraceEnabled(@silent marker: Marker): Boolean = false
  @silent override def trace(marker: Marker, msg: String): Unit = ()
  @silent override def trace(marker: Marker, format: String, arg: AnyRef): Unit = ()
  @silent override def trace(marker: Marker, format: String, arg1: AnyRef, arg2: AnyRef): Unit = ()
  @silent override def trace(marker: Marker, format: String, argArray: AnyRef*): Unit = ()
  @silent override def trace(marker: Marker, msg: String, t: Throwable): Unit = ()

  override def isDebugEnabled: Boolean = level.toInt <= DEBUG_INT
  override def debug(msg: String): Unit = addLogEntry(DEBUG, msg)
  override def debug(format: String, arg: AnyRef): Unit = addLogEntry(DEBUG, MessageFormatter.format(format, arg).getMessage)
  override def debug(format: String, arg1: AnyRef, arg2: AnyRef): Unit = addLogEntry(DEBUG, MessageFormatter.format(format, arg1, arg2).getMessage)
  override def debug(format: String, arguments: AnyRef*): Unit = addLogEntry(DEBUG, MessageFormatter.arrayFormat(format, arguments.toArray).getMessage)
  override def debug(msg: String, t: Throwable): Unit = addLogEntry(DEBUG, msg, t)

  override def isDebugEnabled(@silent marker: Marker): Boolean = false
  @silent override def debug(marker: Marker, msg: String): Unit = ()
  @silent override def debug(marker: Marker, format: String, arg: AnyRef): Unit = ()
  @silent override def debug(marker: Marker, format: String, arg1: AnyRef, arg2: AnyRef): Unit = ()
  @silent override def debug(marker: Marker, format: String, arguments: AnyRef*): Unit = ()
  @silent override def debug(marker: Marker, msg: String, t: Throwable): Unit = ()

  override def isInfoEnabled: Boolean = level.toInt <= INFO_INT
  override def info(msg: String): Unit = addLogEntry(INFO, msg)
  override def info(format: String, arg: AnyRef): Unit = addLogEntry(INFO, MessageFormatter.format(format, arg).getMessage)
  override def info(format: String, arg1: AnyRef, arg2: AnyRef): Unit = addLogEntry(INFO, MessageFormatter.format(format, arg1, arg2).getMessage)
  override def info(format: String, arguments: AnyRef*): Unit = addLogEntry(INFO, MessageFormatter.arrayFormat(format, arguments.toArray).getMessage)
  override def info(msg: String, t: Throwable): Unit = addLogEntry(INFO, msg, t)

  override def isInfoEnabled(@silent marker: Marker): Boolean = false
  @silent override def info(marker: Marker, msg: String): Unit = ()
  @silent override def info(marker: Marker, format: String, arg: AnyRef): Unit = ()
  @silent override def info(marker: Marker, format: String, arg1: AnyRef, arg2: AnyRef): Unit = ()
  @silent override def info(marker: Marker, format: String, arguments: AnyRef*): Unit = ()
  @silent override def info(marker: Marker, msg: String, t: Throwable): Unit = ()

  override def isWarnEnabled: Boolean = level.toInt <= WARN_INT
  override def warn(msg: String): Unit = addLogEntry(WARN, msg)
  override def warn(format: String, arg: AnyRef): Unit = addLogEntry(WARN, MessageFormatter.format(format, arg).getMessage)
  override def warn(format: String, arg1: AnyRef, arg2: AnyRef): Unit = addLogEntry(WARN, MessageFormatter.format(format, arg1, arg2).getMessage)
  override def warn(format: String, arguments: AnyRef*): Unit = addLogEntry(WARN, MessageFormatter.arrayFormat(format, arguments.toArray).getMessage)
  override def warn(msg: String, t: Throwable): Unit = addLogEntry(WARN, msg, t)

  override def isWarnEnabled(@silent marker: Marker): Boolean = false
  @silent override def warn(marker: Marker, msg: String): Unit = ()
  @silent override def warn(marker: Marker, format: String, arg: AnyRef): Unit = ()
  @silent override def warn(marker: Marker, format: String, arg1: AnyRef, arg2: AnyRef): Unit = ()
  @silent override def warn(marker: Marker, format: String, arguments: AnyRef*): Unit = ()
  @silent override def warn(marker: Marker, msg: String, t: Throwable): Unit = ()

  override def isErrorEnabled: Boolean = level.toInt <= ERROR_INT
  override def error(msg: String): Unit = addLogEntry(ERROR, msg)
  override def error(format: String, arg: AnyRef): Unit = addLogEntry(ERROR, MessageFormatter.format(format, arg).getMessage)
  override def error(format: String, arg1: AnyRef, arg2: AnyRef): Unit = addLogEntry(ERROR, MessageFormatter.format(format, arg1, arg2).getMessage)
  override def error(format: String, arguments: AnyRef*): Unit = addLogEntry(ERROR, MessageFormatter.arrayFormat(format, arguments.toArray).getMessage)
  override def error(msg: String, t: Throwable): Unit = addLogEntry(ERROR, msg, t)

  override def isErrorEnabled(@silent marker: Marker): Boolean = false
  @silent override def error(marker: Marker, msg: String): Unit = ()
  @silent override def error(marker: Marker, format: String, arg: AnyRef): Unit = ()
  @silent override def error(marker: Marker, format: String, arg1: AnyRef, arg2: AnyRef): Unit = ()
  @silent override def error(marker: Marker, format: String, arguments: AnyRef*): Unit = ()
  @silent override def error(marker: Marker, msg: String, t: Throwable): Unit = ()
}

object LoggingSpy {
  private val DefaultName = "logging-spy"

  def trace(name: String = DefaultName): LoggingSpy =
    new LoggingSpy(name, TRACE)

  def debug(name: String = DefaultName): LoggingSpy =
    new LoggingSpy(name, DEBUG)

  def info(name: String = DefaultName): LoggingSpy =
    new LoggingSpy(name, INFO)

  def warn(name: String = DefaultName): LoggingSpy =
    new LoggingSpy(name, WARN)

  def error(name: String = DefaultName): LoggingSpy =
    new LoggingSpy(name, ERROR)
}