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

package org.sireum.js.aceeditor

import org.sireum.js._
import scala.scalajs.js.annotation.JSName

object ace extends JsObject {
  def edit(id : String) : AceEditor = scala.scalajs.js.native
  def require(s : String) : JsDynamic = scala.scalajs.js.native
}

trait AceEditor extends JsObject {
  def getSession() : AceEditSession = scala.scalajs.js.native
  def focus() : Unit = scala.scalajs.js.native
  def setTheme(theme : String) : Unit = scala.scalajs.js.native
  def keyBinding : AceKeyBinding = scala.scalajs.js.native
  def setKeyboardHandler(kh : String) : Unit = scala.scalajs.js.native
  def getCursorPosition() : Position = scala.scalajs.js.native
  def setReadOnly(readOnly : Boolean) : Unit = scala.scalajs.js.native
  def getReadOnly() : Boolean = scala.scalajs.js.native
}

object AceDuctWorks {
  //JsName does not suffice here
  //addtionally mixin methods can't be used in JsObject traits
  //this forces us to use duct typing for this
  def onChange(editor : AceEditSession, func : JsObject => Unit) {
    editor.dyn.on("change", func)
  }
}

trait Position extends JsObject {
  def row : Int = scala.scalajs.js.native
  def column : Int = scala.scalajs.js.native
}

trait AceEditSession extends JsObject {
  def setMode(mode : String) : Unit = scala.scalajs.js.native
  def setValue(text : String) : Unit = scala.scalajs.js.native
  def getValue() : String = scala.scalajs.js.native
  //these methods allow the addtion of css classes to gutter cells
  def addGutterDecoration(line : Int, className : String) : Unit = scala.scalajs.js.native
  def clearBreakpoint(line : Int) : Unit = scala.scalajs.js.native
  def clearBreakpoints() : Unit = scala.scalajs.js.native
  def removeGutterDecoration(line : Int, className : String) : Unit = scala.scalajs.js.native
  def setBreakpoint(line : Int, className : String) : Unit = scala.scalajs.js.native
  def addMarker(rng : JsDynamic, cls : String, typ : String, inFront : Boolean) : Unit = scala.scalajs.js.native
  def getMarkers(inFront : Boolean) : JsObject = scala.scalajs.js.native
  def removeMarker(id : JsNumber) : Unit = scala.scalajs.js.native
}

trait AceKeyBinding extends JsObject {
  var onCommandKey : JsFunction3[AceKeyEvent, Int, Int, _] = scala.scalajs.js.native
}

trait AceKeyEvent extends JsObject {
}
