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
  def edit(id : String) : AceEditor = ???
  def require(s : String) : JsDynamic = ???
}

trait AceEditor extends JsObject {
  def getSession() : AceEditSession
  def focus()
  def setTheme(theme : String)
  def keyBinding : AceKeyBinding
  def setKeyboardHandler(kh : String)
  def getCursorPosition() : Position
}

trait Position extends JsObject {
  def row : Int
  def column : Int
}

@JSName("Range")
class AceRange(startRow : Int, startCol : Int, endRow : Int, endCol : Int) extends JsObject {
  
}

trait AceEditSession extends JsObject {
  def setMode(mode : String)
  def setValue(text : String)
  def getValue() : String
  //these methods allow the addtion of css classes to gutter cells
  def addGutterDecoration(line : Int, className : String)
  def clearBreakpoint(line : Int)
  def clearBreakpoints()
  def removeGutterDecoration(line : Int, className : String)
  def setBreakpoint(line : Int, className : String)
  def addMarker(rng : AceRange, cls : String, typ : String, inFront : Boolean)
  def getMarkers(inFront : Boolean) : JsObject
  def removeMarker(id : JsNumber)
}

trait AceKeyBinding extends JsObject {
  var onCommandKey : JsFunction3[AceKeyEvent, Int, Int, _]
}

trait AceKeyEvent extends JsObject {
}
