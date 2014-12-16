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

package org.sireum.js.extjs

import org.sireum.js._
import scala.scalajs.js.annotation.JSName

object Ext extends JsObject {
  def application(o : JsDynamic) : Unit = scala.scalajs.js.native
  def application(o : String) : Unit = scala.scalajs.js.native

  def define(kind : String, o : JsAny*) : JsObject = scala.scalajs.js.native

  @JSName("create")
  def createT[T <: JsAny](kind : String, o : JsAny*) : T = scala.scalajs.js.native
  def create(kind : String, o : JsAny*) : JsObject = scala.scalajs.js.native
  def onReady(fn : JsFunction, scope : JsDynamic = null, options : JsDynamic = null) : JsAny = scala.scalajs.js.native
  def onDocumentReady(fn : JsFunction, scope : JsDynamic = null, options : JsDynamic = null) : JsAny = scala.scalajs.js.native

  def ComponentQuery : ExtComponentQuery = scala.scalajs.js.native

  def browser : `Ext.browser` = scala.scalajs.js.native

  def os : `Ext.os` = scala.scalajs.js.native

  def Msg : `Ext.Msg` = scala.scalajs.js.native

  def tip : `Ext.tip` = scala.scalajs.js.native
}

trait `Ext.tab.Panel` extends JsObject {
  def add(component : JsDynamic) : JsObject = scala.scalajs.js.native
  def add(component : JsArray[JsDynamic]) : JsArray[JsObject] = scala.scalajs.js.native
  def setActiveTab(tab : JsDynamic) : JsObject = scala.scalajs.js.native
  def getActiveTab() : JsObject = scala.scalajs.js.native
  def remove(component : JsDynamic, autoDestroy : Boolean = true) : Unit = scala.scalajs.js.native
  def setTitle(title : String) : Unit = scala.scalajs.js.native
}

trait `Ext.form.field.TextArea` extends JsObject {
  def getValue() : String = scala.scalajs.js.native
  def setValue(text : String) : Unit = scala.scalajs.js.native
}

trait `Ext.tree.Panel` extends JsObject {
  def getSelection() : JsArray[JsObject] = scala.scalajs.js.native
}

trait `Ext.tip` extends JsObject {
  def QuickTipManager : `Ext.tip.QuickTipManager` = scala.scalajs.js.native
}

trait `Ext.tip.QuickTipManager` extends JsObject {
  def init() : Unit = scala.scalajs.js.native
  def register(o : JsDynamic) : Unit = scala.scalajs.js.native
}

trait `Ext.Msg` extends JsObject {
  def alert(title : String, message : String) : Unit = scala.scalajs.js.native
  def confirm(title : String, message : String, fn : JsFunction1[String, Unit]) : Unit = scala.scalajs.js.native
  def prompt(title : String, message : String, fn : JsFunction2[String, String, Unit]) : Unit = scala.scalajs.js.native
}

trait ExtComponentQuery extends JsObject {
  def query[T](q : String) : JsArray[T] = scala.scalajs.js.native
}

trait `Ext.Button` extends JsObject {
  def pressed : Boolean = scala.scalajs.js.native
  def setGlyph(x : String) : Unit = scala.scalajs.js.native
  def setTooltip(tip : String) : Unit = scala.scalajs.js.native
  def setText(text : String) : Unit = scala.scalajs.js.native
  def setDisabled(b : Boolean) : Unit = scala.scalajs.js.native
  def toggle(b : Boolean) : Unit = scala.scalajs.js.native
}

trait `Ext.EventObject` extends JsObject {
  val ENTER : Int = scala.scalajs.js.native
  val TAB : Int = scala.scalajs.js.native
  def getKey() : Int = scala.scalajs.js.native
}

trait `Ext.data.Store` extends JsObject {
  def count(grouped : Boolean) : Int = scala.scalajs.js.native
  def getAt(index : Int) : JsObject = scala.scalajs.js.native
  def findBy(fn : JsFunction1[JsDynamic, Boolean]) : Int = scala.scalajs.js.native
  def findRecord(key : String, value : String) : JsObject = scala.scalajs.js.native
  def remove(record : JsDynamic) : Unit = scala.scalajs.js.native
  def removeAt(index : Int, count : Int = 1) : Unit = scala.scalajs.js.native
  def add(o : JsDynamic) : Unit = scala.scalajs.js.native
  def removeAll() : Unit = scala.scalajs.js.native
  def sync() : Unit = scala.scalajs.js.native
  def loadRawData[T](a : JsArray[JsArray[T]]) : Unit = scala.scalajs.js.native
}

trait `Ext.data.TreeStore` extends `Ext.data.Store` {
  def root : `Ext.data.NodeInterface` = scala.scalajs.js.native
  def setRoot(root : JsObject) : Unit = scala.scalajs.js.native
}

trait `Ext.data.ArrayStore` extends `Ext.data.Store` {
}

trait `Ext.data.NodeInterface` extends JsObject {
  def parentNode : JsObject = scala.scalajs.js.native
  def childNodes : JsArray[JsObject] = scala.scalajs.js.native
  def findChild(attr : String, value : JsDynamic) : `Ext.data.NodeInterface` = scala.scalajs.js.native
  def sort(sortFn : JsFunction = null, recursive : Boolean = false,
           suppressEvents : Boolean = false) : Unit = scala.scalajs.js.native
  def appendChild(
    node : JsDynamic, suppressEvents : Boolean = false,
    commit : Boolean = false) : JsAny = scala.scalajs.js.native
}

trait `Ext.Img` extends JsObject {
  def getEl() : HTMLElement = scala.scalajs.js.native
  def setSrc(src : String) : Unit = scala.scalajs.js.native
}

trait `Ext.menu.Item` extends JsObject {
  def text : String = scala.scalajs.js.native
  def setDisabled(disabled : Boolean) : Unit = scala.scalajs.js.native
  def setText(text : String) : Unit = scala.scalajs.js.native
}

trait `Ext.menu.CheckItem` extends `Ext.menu.Item` {
  def checked : Boolean = scala.scalajs.js.native
  def setChecked(checked : Boolean, suppressEvents : Boolean = false) : Unit = scala.scalajs.js.native
}

trait `Ext.form.field.ComboBox` extends JsObject {
  var displayField : String = scala.scalajs.js.native
  var displayTpl : JsAny = scala.scalajs.js.native
  var delimiter : String = scala.scalajs.js.native

  def isDirty() : Boolean = scala.scalajs.js.native
  def reset() : Unit = scala.scalajs.js.native
  def getValue[T]() : T = scala.scalajs.js.native
  def getEl() : HTMLElement = scala.scalajs.js.native
  def setValue(v : JsAny) : Unit = scala.scalajs.js.native
  def bindStore(store : `Ext.data.Store`, preventFilter : Boolean, initial : Boolean) : Unit = scala.scalajs.js.native
  def isValid() : Boolean = scala.scalajs.js.native
  def validator : JsFunction1[String, JsAny] = scala.scalajs.js.native
  def setDisabled(b : Boolean) : Unit = scala.scalajs.js.native
}

trait `Ext.tip.ToolTip` extends JsObject {
  def update(tip : String) : Unit = scala.scalajs.js.native
}

trait `Ext.os` extends JsObject {
  def is(name : String) : Boolean = scala.scalajs.js.native
}

trait `Ext.browser` extends JsObject {
  def is(name : String) : Boolean = scala.scalajs.js.native
}
