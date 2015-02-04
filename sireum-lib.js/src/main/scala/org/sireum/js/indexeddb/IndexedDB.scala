package org.sireum.js.indexeddb

import org.sireum.js._
import scala.scalajs.js.annotation.JSName
import org.scalajs.dom.idb.VersionChangeEvent
import org.scalajs.dom.idb.Database

/**
 * @author <a href="mailto:jake.h.ehrlich@santos.org">Jake Ehrlich</a>
 */
trait KeyValueStore[T] {
  def store(value : T)
  def store(value : T, oncomplete : DomEvent => Unit)
  def load(func : T => Unit)
}

/**
 * @author <a href="mailto:jake.h.ehrlich@santos.org">Jake Ehrlich</a>
 */
object KeyValueDB {
  var names : Set[String] = Set()

  private[indexeddb] val storeName = "__system"
  private[indexeddb] val keyIndexName = "by_key"
  private[indexeddb] val keyName = "key"
  private[indexeddb] val valueName = "value"

  def apply[T](dbname : String) : KeyValueDB = {
    new KeyValueDB(dbname)
  }
}

/**
 * @author <a href="mailto:jake.h.ehrlich@santos.org">Jake Ehrlich</a>
 */
class KeyValueDB(dbname : String) {
  private[indexeddb] var db : Database = null
  import KeyValueDB._

  {
    val request = window.indexedDB.open(dbname, 1)

    request.onupgradeneeded = (event : VersionChangeEvent) => {
      console.log("database '" + dbname + "' has been created")
      console.log("oldVersion: " + event.oldVersion)
      console.log("newVersion: " + event.newVersion)
      val db = request.result.asInstanceOf[Database]

      var store = db.createObjectStore(storeName, obj(keyPath = keyName))
      val key_index = store.createIndex(keyIndexName, keyName)
    }

    request.onsuccess = (event : DomEvent) => {
      db = request.result.asInstanceOf[Database]
      console.log("database '" + dbname + "' has been opened")
      console.log(db)
      //console.log("object stores: " + db.objectStoreNames.toString())
    }

  }

  def by[T](key : String)(implicit conv : JsConvertable[T]) : KeyValueStore[T] = {
    return new KeyValueStoreImpl[T](key, this, conv)
  }
}

private class KeyValueStoreImpl[T](key : String, dbHandle : KeyValueDB, conv : JsConvertable[T]) extends KeyValueStore[T] {
  import KeyValueDB._

  def store(value : T) {
    this.store(value, (e : DomEvent) => Unit)
  }

  def store(value : T, oncomplete : DomEvent => Unit) {
    console.log(dbHandle.db)
    val trans = dbHandle.db.transaction(JsArray(storeName), "readwrite")
    val store = trans.objectStore(storeName)
    store.put(obj(keyName -> key, valueName -> conv.to(value)))
    trans.oncomplete = (e : DomEvent) => {
      console.log(s"stored: $key -> ${value.toString()}")
      oncomplete(e)
    }
  }

  def load(func : T => Unit) {
    val trans = dbHandle.db.transaction(JsArray(storeName), "readonly")
    val store = trans.objectStore(storeName)
    val index = store.index("by_key")
    val request = index.get(key)
    request.onsuccess = (event : DomEvent) => {
      console.log(s"loaded: $key ->", request.result)
      if (request.result.dyn != undefined.dyn) {
        func(conv.from(request.result.dyn.selectDynamic(valueName)))
      }
    }
  }
}
