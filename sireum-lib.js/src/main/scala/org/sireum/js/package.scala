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

package org.sireum

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
package object js {
  import scala.scalajs.js
  import org.scalajs.dom

  type JsAny = js.Any
  type JsObject = js.Object
  type JsUndefined = js.Undefined
  type JsNumber = js.Number
  type JsString = js.String
  type JsArray[T] = js.Array[T]
  type JsFunction = js.Function
  type JsFunction0[T0] = js.Function0[T0]
  type JsFunction1[T0, T1] = js.Function1[T0, T1]
  type JsFunction2[T0, T1, T2] = js.Function2[T0, T1, T2]
  type JsFunction3[T0, T1, T2, T3] = js.Function3[T0, T1, T2, T3]
  type JsFunction4[T0, T1, T2, T3, T4] = js.Function4[T0, T1, T2, T3, T4]
  type JsFunction5[T0, T1, T2, T3, T4, T5] = js.Function5[T0, T1, T2, T3, T4, T5]
  type JsDynamic = js.Dynamic

  type DomEvent = dom.Event
  type HTMLElement = dom.HTMLElement

  val undefined : js.Undefined = ()
  val document = dom.document
  val window = dom.window
  val console = dom.console
  val location = dom.location

  val JsObject = js.Object
  val JsArray = js.Array

  val obj = js.Dynamic.literal

  def array[T](o : T*) : js.Array[T] = js.Array[T](o : _*)
  def arraySeq[T](o : Seq[T]) : js.Array[T] = js.Array[T](o : _*)
  def emptyArray = js.Array()

  def fun[T0](x : Function0[T0]) : js.Function = x
  def fun[T0, T1](x : Function1[T0, T1]) : js.Function = x
  def fun[T0, T1, T2](x : Function2[T0, T1, T2]) : js.Function = x
  def fun[T0, T1, T2, T3](x : Function3[T0, T1, T2, T3]) : js.Function = x
  def fun[T0, T1, T2, T3, T4](x : Function4[T0, T1, T2, T3, T4]) : js.Function = x
  def fun[T0, T1, T2, T3, T4, T5](x : Function5[T0, T1, T2, T3, T4, T5]) : js.Function = x

  def fun0[T0](x : Function0[T0]) : js.Function0[T0] = x
  def fun1[T0, T1](x : Function1[T0, T1]) : js.Function1[T0, T1] = x
  def fun2[T0, T1, T2](x : Function2[T0, T1, T2]) : js.Function2[T0, T1, T2] = x
  def fun3[T0, T1, T2, T3](x : Function3[T0, T1, T2, T3]) : js.Function3[T0, T1, T2, T3] = x
  def fun4[T0, T1, T2, T3, T4](x : Function4[T0, T1, T2, T3, T4]) : js.Function4[T0, T1, T2, T3, T4] = x
  def fun5[T0, T1, T2, T3, T4, T5](x : Function5[T0, T1, T2, T3, T4, T5]) : js.Function5[T0, T1, T2, T3, T4, T5] = x

  implicit class IsUndefJs(val o : JsAny) extends AnyVal {
    def isUndef = o.isInstanceOf[JsUndefined]
  }

  implicit class IsUndef(val o : Any) extends AnyVal {
    def isUndef = o.isInstanceOf[JsUndefined]
  }

  trait RichNode extends dom.Node {
    override def parentNode : RichNode = ???
    def getElementsByClassName(className : String) : dom.HTMLCollection = ???
  }

  implicit class DynamicNode(val o : dom.Node) extends AnyVal {
    def style : js.Dynamic = o.asInstanceOf[js.Dynamic].style
  }

  implicit class Dynamic(val o : Any) extends AnyVal {
    def dyn : js.Dynamic = o.asInstanceOf[js.Dynamic]
  }
}