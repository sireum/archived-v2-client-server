/*
Copyright (c) 2014 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.server.plugin

import javax.websocket._
import javax.websocket.server._

import org.sireum.server._
import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
class EchoProcessPlugin extends Server.ProcessPlugin with Logging {
  override def enabled = false
  val name = "echo"
  var out : java.io.PrintStream = _
  def run(message : String) {
    out.println(message)
    out.flush
  }
  def close {}
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@ServerEndpoint(value = "/echo/")
class EchoWsPlugin extends Server.WsPlugin with Logging {
  override def enabled = false
  def name = "echo"

  val eventClass : Class[_] = classOf[EchoWsPlugin]

  @OnOpen
  def onConnect(sess : Session) {
    logger.info("Socket Connected: " + sess)
  }

  @OnMessage
  def onText(message : String) : String = {
    logger.info("Received TEXT message: " + message)
    message
  }

  @OnClose
  def onClose(reason : CloseReason) {
    logger.info("Socket Closed: " + reason)
  }

  @OnError
  def onError(cause : Throwable) {
    cause.printStackTrace(System.err)
  }

  def close {}
}