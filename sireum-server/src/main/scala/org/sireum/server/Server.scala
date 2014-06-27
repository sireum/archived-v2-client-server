/*
Copyright (c) 2014 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.server

import java.io._
import java.net._
import org.eclipse.jetty.server.{ Server => JettyServer }
import org.eclipse.jetty.server.handler._
import org.eclipse.jetty.servlet._
import org.eclipse.jetty.webapp._
import org.eclipse.jetty.websocket.jsr356.server.deploy._
import org.sireum.option.LaunchServerMode
import org.sireum.util._
import org.sireum.option.LaunchServerMode

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object Server {
  val defaultPlugins : ISeq[Plugin] = {
    libPath(getClass) match {
      case Some(libPath) =>
        val libDir = FileUtil.toFile(libPath)
        ivector(
          "ace", "extjs", "font-awesome", "mathjax", "miscjs"
        ).map { x =>
            DefaultPlugin(x, "/" + x,
              FileUtil.toUri(new File(libDir, x + ".jar")))
          }.+:(DefaultPlugin("sireum-play", "/",
            FileUtil.toUri(new File(libDir, "sireum-play.jar")))).
          filter { p =>
            FileUtil.toFile(p.uri).exists
          }
      case _ =>
        ivector()
    }
  }

  val additionalPlugins : ISeq[Plugin] = {
    ivector(
      "EchoProcessPlugin", "EchoWsPlugin",
      "SymbologicsPlugin", "SymbologicsWsPlugin",
      "BakarKiasanPlugin", "BakarKiasanProcessPlugin", "BakarKiasanWsPlugin"
    ).flatMap(x =>
        try {
          Some(Class.forName("org.sireum.server.plugin." + x).newInstance.
            asInstanceOf[Plugin])
        } catch {
          case e : Exception => None
        }
      )
  }

  def main(args : Array[String]) {
    val o =
      args match {
        case Array(p) => new LaunchServerMode(p.toInt)
        case _        => new LaunchServerMode()
      }

    run(o)
  }

  def run(option : LaunchServerMode) {
    run(option, {})
  }

  def run(option : LaunchServerMode, f : => Unit) {
    val pw = System.out
    val server = new Server(option.port)
    if (server.start) {
      if (!option.quiet) {
        pw.println(s"Running server at port: ${option.port}")
        pw.println("Press 'x' and hit 'Enter' to exit.")
        pw.flush
      }
      f
      val lnr = new LineNumberReader(new InputStreamReader(System.in))
      var l = lnr.readLine
      while (l != null && l.trim != "x") {
        server.process(l)
        l = lnr.readLine
      }
      server.stop
    }
  }

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  trait Plugin {
    def name : String
    def enabled = true
  }

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  abstract class ProcessPlugin extends Plugin {
    var out : PrintStream
    def run(message : String)
  }

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  abstract class ResourcePlugin extends Plugin {
    def contextPath : String
    def uri : ResourceUri
  }

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  abstract class WsPlugin extends Plugin {
    def eventClass : Class[_]
  }

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  final case class DefaultPlugin(
      name : String,
      contextPath : String,
      uri : ResourceUri) extends ResourcePlugin {
  }

  def sireumHomeLibPath = {
    val sireumHome = System.getenv("SIREUM_HOME")
    if (sireumHome != null) {
      Some(FileUtil.toUri(new File(new File(sireumHome), "lib")))
    } else None
  }

  def libPath(c : Class[_]) : Option[ResourceUri] = {
    val relPath =
      "/" + c.getPackage.getName.split('.').map(x => "..").mkString("/") +
        "/../../lib"
    val fUri = c.getResource("Server.class").toURI + relPath
    try {
      if (fUri.contains(".jar")) {
        sireumHomeLibPath
      } else {
        Some(FileUtil.toUri(FileUtil.toFile(fUri)))
      }
    } catch {
      case _ : Exception => sireumHomeLibPath
    }
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
class Server(port : Int) extends Logging {

  private var server : Option[JettyServer] = None

  private var processPlugins : IMap[String, Server.ProcessPlugin] = imapEmpty

  def this() = this(LaunchServerMode().port)

  private def portAvailable(port : Int) : Boolean = {
    try {
      val ignored = new Socket("localhost", port)
      ignored.close
      false
    } catch {
      case _ : IOException =>
        true
    }
  }

  def start : Boolean = {

    import Server._

    val server = new JettyServer(port)

    logger.debug(s"Sireum server is starting at port $port")

    val handlers = new ContextHandlerCollection
    server.setHandler(handlers)

    val plugins = defaultPlugins ++ additionalPlugins

    val webSocketContext =
      new ServletContextHandler(ServletContextHandler.SESSIONS)
    webSocketContext.setContextPath("/ws")
    handlers.addHandler(webSocketContext)
    val webSocketContainer = WebSocketServerContainerInitializer.
      configureContext(webSocketContext)

    for (p <- plugins if p.enabled) {
      logger.debug(s"Found plugin: ${p.name}")
      p match {
        case p : ProcessPlugin =>
          p.out = System.out
          processPlugins += (p.name -> p)
        case p : ResourcePlugin =>
          if (p.uri.endsWith(".jar")) {
            val webAppContext = new WebAppContext
            webAppContext.setInitParameter(
              "org.eclipse.jetty.servlet.Default.dirAllowed", "false")
            webAppContext.setServer(server)
            webAppContext.setContextPath(p.contextPath)
            webAppContext.setWar(FileUtil.toFile(p.uri).toURI.toURL.toString)
            handlers.addHandler(webAppContext)
          } else {
            val resourceHandler = new ResourceHandler
            resourceHandler.setDirectoriesListed(false)
            resourceHandler.setResourceBase(FileUtil.toFile(p.uri).getCanonicalPath)
            val contextHandler = new ContextHandler(p.contextPath)
            contextHandler.setHandler(resourceHandler)
            handlers.addHandler(contextHandler)
          }
        case p : WsPlugin =>
          webSocketContainer.addEndpoint(p.eventClass)
      }
    }

    try {
      server.start
      logger.debug("Sireum server has been started")
      this.server = Some(server)
      true
    } catch {
      case _ : java.net.BindException =>
        System.err.println(s"Port $port is unavailable")
        System.err.flush
        server.stop
        false
    }
  }

  def process(message : String) {
    val i = message.indexOf(':')
    if (i < 0) None
    else {
      val name = message.substring(0, i).trim
      val msg = message.substring(i + 1)
      processPlugins.get(name) match {
        case Some(p) => p.run(msg)
        case _ =>
          logger.debug(s"Process plugin named '$name' is not available")
          None
      }
    }
  }

  def stop {
    server.foreach(_.stop)
    logger.debug(s"Sireum server at port $port has been stopped")
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
class ServerDaemon {
  var args : Array[String] = _
  var server : Option[Server] = None

  def init(args : Array[String]) {
    this.args = args
  }

  def start {
    val o =
      args match {
        case Array(p) => new LaunchServerMode(p.toInt)
        case _        => new LaunchServerMode()
      }

    stop
    val server = new Server(o.port)
    server.start
    this.server = Some(server)
  }

  def stop {
    server.foreach(_.stop)
    server = None
  }

  def destroy {
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
class NoLogger extends org.eclipse.jetty.util.log.Logger {
  def getName = "none"
  override def warn(msg : String, args : Object*) {}
  override def warn(thrown : Throwable) {}
  override def warn(msg : String, thrown : Throwable) {}
  override def info(msg : String, args : Object*) {}
  override def info(thrown : Throwable) {}
  override def info(msg : String, thrown : Throwable) {}
  override def isDebugEnabled = false
  override def setDebugEnabled(enabled : Boolean) {}
  override def debug(msg : String, args : Object*) {}
  override def debug(thrown : Throwable) {}
  override def debug(msg : String, thrown : Throwable) {}
  override def debug(msg : String, value : Long) {}
  override def getLogger(name : String) = this
  override def ignore(ignored : Throwable) {}
}
