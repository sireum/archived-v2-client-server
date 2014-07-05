import java.io._
import org.sireum.option._
import org.sireum.tools.upickler._

object ProjectResourcePicklerGenerator {
  def main(args : Array[String]) {
    val projectResourcePickler = new UPickler().pickler(
      UPicklerMode("org.sireum.project", "ProjectResource",
        Vector(),
        Array(
          "ProjectImpl", "ProjectFileImpl", "ProjectFolderImpl"
        )))

    val i = projectResourcePickler.indexOf("def")

    var f = new File("src/main/scala/org/sireum/project/ProjectResourcePickler.scala")
    var fw = new FileWriter(f)
    fw.write(projectResourcePickler.substring(0, i).trim)
    fw.close
    System.out.println("Wrote " + f.getCanonicalPath)
  }
}