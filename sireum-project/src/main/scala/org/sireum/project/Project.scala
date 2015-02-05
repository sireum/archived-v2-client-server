/*
Copyright (c) 2014 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.project

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait ProjectRoot {
  var projects : Map[String, Project]
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class ProjectRootImpl(var projects : Map[String, Project])
  extends ProjectRoot

final object ProjectResource {
  val jsMap = Seq(
    classOf[ProjectFileImpl],
    classOf[ProjectFolderImpl],
    classOf[ProjectImpl]
  )
}
  
/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
abstract class ProjectResource {
  def name : String
  def uri : String
  def impl : ProjectResource
  def toSimpleString : String
  def isFolder : Boolean
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait ProjectFolderResource extends ProjectResource {
  var resources : Map[String, ProjectResource]
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait Project extends ProjectFolderResource {
  var pathUri : String
  var isExpanded : Boolean
  var resources : Map[String, ProjectResource]

  def isFolder = true
  def name = {
    val i = pathUri.lastIndexOf('/', pathUri.length - 2)
    if (i < 0) pathUri
    else pathUri.substring(i + 1, pathUri.length - 1)
  }
  def uri = name
  def impl = ProjectImpl(pathUri, isExpanded, resources.map(p => (p._1, p._2.impl)))
  def toSimpleString = s"Project($name, $uri, $pathUri)"
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class ProjectImpl(
  var pathUri : String,
  var isExpanded : Boolean,
  var resources : Map[String, ProjectResource])
    extends Project {
  override def impl = this
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait ProjectFile extends ProjectResource {
  var name : String
  var uri : String
  var mimeType : String

  def isFolder = false
  def impl = ProjectFileImpl(name, uri, mimeType)
  def toSimpleString = s"File($name, $uri, $mimeType)"
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class ProjectFileImpl(
  var name : String,
  var uri : String,
  var mimeType : String)
    extends ProjectFile {
  override def impl = this
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait ProjectFolder extends ProjectFolderResource {
  var name : String
  var uri : String
  var isExpanded : Boolean
  var resources : Map[String, ProjectResource]

  def isFolder = true
  def impl = ProjectFolderImpl(name, uri, isExpanded,
    resources.map(p => (p._1, p._2.impl)))
  def toSimpleString = s"Folder($name, $uri)"
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class ProjectFolderImpl(
  var name : String,
  var uri : String,
  var isExpanded : Boolean,
  var resources : Map[String, ProjectResource])
    extends ProjectFolder {
  override def impl = this
}