package org.sireum.js.project

import org.sireum.project._
import org.sireum.js._
import org.sireum.js.extjs._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object JsProjectRoot {
  import JsProjectResource._
  implicit class ToJs(val p : ProjectRoot) extends AnyVal {
    def js(implicit mode : Mode.Type = JsProjectResource.Mode.ALL) = obj(
      expanded = true,
      children = arraySeq(p.projects)
    )
  }
  def apply(o : JsObject) : ProjectRoot = apply(o.dyn)
  def apply(o : JsDynamic) : ProjectRoot = new ProjectRoot {
    def projects : Map[String, Project] = {
      var m = Map[String, Project]()
      for (r <- o.data.children.asInstanceOf[JsArray[JsDynamic]]) {
        val pr : Project = JsProject(r)
        m += (pr.name -> pr)
      }
      m
    }
    def projects_=(ps : Map[String, Project]) = {
      implicit val mode = JsProjectResource.Mode.ALL
      o.data.children = arraySeq(ps)
    }
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object JsProjectResource {

  object Mode extends Enumeration {
    type Type = Value
    val ALL, FOLDER, PROJECT = Value
  }

  def initModel {
    Ext.define("sireum.ProjectResource", obj(
      extend = "Ext.data.TreeModel",
      fields = array(
        obj(name = "name", `type` = "string"),
        obj(name = "uri", `type` = "string")
      )
    ))

    Ext.define("sireum.Project", obj(
      extend = "sireum.ProjectResource",
      fields = array(obj(name = "pathUri", `type` = "string"))
    ))

    Ext.define("sireum.ProjectFile", obj(
      extend = "sireum.ProjectResource",
      fields = array(obj(name = "mimeType", `type` = "string"))
    ))

    Ext.define("sireum.ProjectFolder", obj(
      extend = "sireum.ProjectResource",
      fields = array()
    ))
  }

  def apply(o : JsObject) : ProjectResource = apply(o.dyn)
  def apply(o : JsDynamic) : ProjectResource = {
    val mtype = if (o.data.isUndef) o.mtype else o.data.mtype
    string(mtype) match {
      case "Project"       => JsProject(o)
      case "ProjectFile"   => JsProjectFile(o)
      case "ProjectFolder" => JsProjectFolder(o)
    }
  }
  def js(r : ProjectResource, mode : Mode.Type) : JsDynamic =
    r match {
      case r : Project       => JsProject.js(r, mode)
      case r : ProjectFile   => JsProjectFile.js(r)
      case r : ProjectFolder => JsProjectFolder.js(r, mode)
    }
  def js(it : Iterable[ProjectResource], mode : Mode.Type) : Seq[JsDynamic] =
    it.toSeq.map(r => js(r, mode))

  import language.implicitConversions
  implicit def map2seq(m : Map[String, ProjectResource])(
    implicit mode : Mode.Type) : Seq[JsDynamic] =
    m.toSeq.sortWith((kv1, kv2) =>
      (kv1._2.isFolder, kv2._2.isFolder) match {
        case (false, true) => false
        case (true, false) => true
        case _             => kv1._1.compareTo(kv2._1) <= 0
      }
    ).map(kv => js(kv._2, mode))
  implicit def string(v : JsDynamic) = v.asInstanceOf[String]
  implicit def int(v : JsDynamic) = v.asInstanceOf[Int]
  implicit def boolean(v : JsDynamic) = v.asInstanceOf[Boolean]
  implicit def long(v : JsDynamic) = v.asInstanceOf[JsNumber].toLong
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object JsProject {
  import JsProjectResource._
  def js(p : Project, mode : Mode.Type) : JsDynamic = {
    obj(
      mtype = "Project",
      text = p.name,
      pathUri = p.pathUri,
      name = p.name,
      uri = p.uri,
      children = {
        implicit val m = mode
        mode match {
          case Mode.PROJECT => array()
          case Mode.FOLDER =>
            arraySeq[JsDynamic](
              p.resources.filter(p => p._2.isInstanceOf[ProjectFile]))
          case _ =>
            arraySeq[JsDynamic](p.resources)
        }
      },
      expanded = p.isExpanded,
      qtip = p.pathUri)
  }
  def apply(p : JsObject) : Project = apply(p.dyn)
  def apply(p : JsDynamic) : Project = new Project {
    val data = if (p.data.isUndef) p else p.data
    def pathUri : String = data.pathUri
    def pathUri_=(v : String) { data.pathUri = v }
    def resources : Map[String, ProjectResource] = {
      var m = Map[String, ProjectResource]()
      for (r <- data.children.asInstanceOf[JsArray[JsDynamic]]) {
        val pr : ProjectResource = JsProjectResource(r)
        m += (pr.name -> pr)
      }
      m
    }
    def resources_=(m : Map[String, ProjectResource]) {
      implicit val mode = Mode.ALL
      val a = arraySeq(m)
      data.children = a
    }
    def isExpanded : Boolean = data.expanded
    def isExpanded_=(b : Boolean) { data.expanded = b }
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object JsProjectFile {
  def apply(o : JsObject) : ProjectFile = apply(o.dyn)
  def apply(o : JsDynamic) : ProjectFile = new ProjectFile {
    val data = if (o.data.isUndef) o else o.data
    import JsProjectResource._
    def name : String = data.name
    def name_=(s : String) { data.name = s }
    def uri : String = data.uri
    def uri_=(s : String) { data.uri = s }
    def mimeType : String = data.mimeType
    def mimeType_=(s : String) { data.mimeType = s }
  }
  def js(p : ProjectFile) : JsDynamic = obj(
    mtype = "ProjectFile",
    text = p.name,
    name = p.name,
    uri = p.uri,
    mimeType = p.mimeType,
    qtip = p.mimeType,
    leaf = true
  )
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object JsProjectFolder {
  import JsProjectResource._
  def apply(o : JsObject) : ProjectFolder = apply(o.dyn)
  def apply(o : JsDynamic) : ProjectFolder = new ProjectFolder {
    val data = if (o.data.isUndef) o else o.data
    def name : String = data.name
    def name_=(s : String) = data.name = s
    def uri : String = data.uri
    def uri_=(s : String) = data.uri = s
    def resources : Map[String, ProjectResource] = {
      var m = Map[String, ProjectResource]()
      for (r <- data.children.asInstanceOf[JsArray[JsDynamic]]) {
        val pr : ProjectResource = JsProjectResource(r)
        m += (pr.name -> pr)
      }
      m
    }
    def resources_=(m : Map[String, ProjectResource]) {
      implicit val mode = Mode.ALL
      val a = arraySeq(m)
      data.children = a
    }
    def isExpanded : Boolean = data.expanded
    def isExpanded_=(b : Boolean) = data.expanded = b
  }
  def js(p : ProjectFolder, mode : Mode.Type) : JsDynamic = obj(
    mtype = "ProjectFolder",
    text = p.name,
    name = p.name,
    uri = p.uri,
    children = {
      val rs =
        if (mode != Mode.ALL)
          p.resources.filter(p => p._2.isInstanceOf[ProjectFile])
        else p.resources
      implicit val m = mode
      arraySeq[JsDynamic](rs)
    },
    expanded = p.isExpanded
  )
}
