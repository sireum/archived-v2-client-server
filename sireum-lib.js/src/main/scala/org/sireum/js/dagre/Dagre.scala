/*
Copyright (c) 2014, Robby, Kansas State University
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice,
   this list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright
   notice, this list of conditions and the following disclaimer in the
   documentation and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its
   contributors may be used to endorse or promote products derived from
   this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package org.sireum.js.dagre

import org.sireum.js._

object Dagre {
  def newD3Digraph =
    new `dagreD3.graphlib.Graph`(obj()).
      setGraph(obj()).
      setDefaultEdgeLabel(fun0 { () => obj() })
  def newD3Digraph(gOpt : JsObject, fEdgeLabel : JsFunction0[JsDynamic]) =
    new `dagreD3.graphlib.Graph`(gOpt).
      setGraph(obj()).
      setDefaultEdgeLabel(fEdgeLabel)
  def newD3Renderer = new `dagreD3.render`
}

trait Graph extends JsObject

class `dagreD3.graphlib.Graph`(opt : JsObject) extends Graph {
  def setGraph(o : JsDynamic) : `dagreD3.graphlib.Graph` = scala.scalajs.js.native
  def setDefaultEdgeLabel(f : JsFunction0[JsDynamic]) : `dagreD3.graphlib.Graph` = scala.scalajs.js.native
  def setNode(id : String, label : JsDynamic) : JsAny = scala.scalajs.js.native
  def setEdge(sourceId : String, targetId : String, attr : JsDynamic = null) : JsAny = scala.scalajs.js.native
}

class `dagreD3.render` extends JsObject {
  def apply(target : JsAny, g : Graph) : JsAny = scala.scalajs.js.native
}
