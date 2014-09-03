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
  def application(o : JsDynamic) : Unit = ???
  def application(o : String) : Unit = ???

  def define(kind : String, o : JsAny*) : JsObject = ???

  @JSName("create")
  def createT[T <: JsAny](kind : String, o : JsAny*) : T = ???
  def create(kind : String, o : JsAny*) : JsObject = ???
  def onReady(fn : JsFunction, scope : JsDynamic = null, options : JsDynamic = null) : JsAny = ???
  def onDocumentReady(fn : JsFunction, scope : JsDynamic = null, options : JsDynamic = null) : JsAny = ???

  def ComponentQuery : ExtComponentQuery = ???

  def browser : `Ext.browser` = ???

  def os : `Ext.os` = ???

  def Msg : `Ext.Msg` = ???

  def tip : `Ext.tip` = ???
}

trait `Ext.tab.Panel` extends JsObject {
  def add(component : JsDynamic) : JsObject
  def add(component : JsArray[JsDynamic]) : JsArray[JsObject]
  def setActiveTab(tab : JsDynamic) : JsObject
  def getActiveTab() : JsObject
  def remove(component : JsDynamic, autoDestroy : Boolean = true)
}

trait `Ext.form.field.TextArea` extends JsObject {
  def getValue() : String
  def setValue(text : String) 
}

trait `Ext.tree.Panel` extends JsObject {
  def getSelection() : JsArray[JsObject]
}

trait `Ext.tip` extends JsObject {
  def QuickTipManager : `Ext.tip.QuickTipManager`
}

trait `Ext.tip.QuickTipManager` extends JsObject {
  def init()
  def register(o : JsDynamic)
}

trait `Ext.Msg` extends JsObject {
  def alert(title : String, message : String)
  def confirm(title : String, message : String, fn : JsFunction1[String, Unit])
  def prompt(title : String, message : String, fn : JsFunction2[String, String, Unit])
}

trait ExtComponentQuery extends JsObject {
  def query[T](q : String) : JsArray[T] = ???
}

trait `Ext.Button` extends JsObject {
  def setGlyph(x : String)
  def setTooltip(tip : String)
  def setText(text : String)
  def setDisabled(b : Boolean)
}

trait `Ext.EventObject` extends JsObject {
  val ENTER : Int = ???
  val TAB : Int = ???
  def getKey() : Int
}

trait `Ext.data.Store` extends JsObject {
  def count(grouped : Boolean) : Int
  def getAt(index : Int) : JsObject
  def findBy(fn : JsFunction1[JsDynamic, Boolean]) : Int
  def findRecord(key : String, value : String) : JsObject
  def remove(record : JsDynamic)
  def removeAt(index : Int, count : Int = 1)
  def add(o : JsDynamic)
  def removeAll()
  def loadRawData[T](a : JsArray[JsArray[T]])
}

trait `Ext.data.TreeStore` extends `Ext.data.Store` {
  def root : `Ext.data.NodeInterface`
  def setRoot(root : JsObject)
}

trait `Ext.data.ArrayStore` extends `Ext.data.Store` {
}

trait `Ext.data.NodeInterface` extends JsObject {
  def parentNode : JsObject
  def childeNodes : JsArray[JsObject]
  def findChild(attr : String, value : JsDynamic) : `Ext.data.NodeInterface`
  def sort(sortFn : JsFunction = null, recursive : Boolean = false,
           suppressEvents : Boolean = false)
  def appendChild(
    node : JsDynamic, suppressEvents : Boolean = false,
    commit : Boolean = false) : JsAny
}

trait `Ext.Img` extends JsObject {
  def getEl() : HTMLElement
  def setSrc(src : String)
}

trait `Ext.menu.CheckItem` extends JsObject {
  def checked : Boolean
  def text : String
  def setDisabled(disabled : Boolean)
  def setChecked(checked : Boolean, suppressEvents : Boolean = false)
}

trait `Ext.form.field.ComboBox` extends JsObject {
  var displayField : String
  var displayTpl : JsAny
  var delimiter : String

  def isDirty() : Boolean
  def reset()
  def getValue[T]() : T
  def getEl() : HTMLElement
  def setValue(v : JsAny)
  def bindStore(store : `Ext.data.Store`, preventFilter : Boolean, initial : Boolean)
  def isValid() : Boolean
  def validator : JsFunction1[String, JsAny]
  def setDisabled(b : Boolean)
}

trait `Ext.tip.ToolTip` extends JsObject {
  def update(tip : String)
}

trait `Ext.os` extends JsObject {
  def is(name : String) : Boolean
}

trait `Ext.browser` extends JsObject {
  def is(name : String) : Boolean
}