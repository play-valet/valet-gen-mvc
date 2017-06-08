package module.twirlScaffoldThemes.utils

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

import scala.collection.JavaConverters._

trait CustomJsoupElementLike {

  def getElement: org.jsoup.nodes.Element
  def copySelfBefore: CustomJsoupElementLike
  def copySelfAfter: CustomJsoupElementLike
  def scopeRoot: CustomJsoupElementLike
  def scopeBefore: CustomJsoupElementLike
  def scopeAfter: CustomJsoupElementLike
  def scopeClosestElement(cssQuery: String): CustomJsoupElementLike
  def scopeClosestElements[T](cssQuery: String)(nextFunction: Element => T): CustomJsoupElementLike
  def scopeClosestElement(cssQuery: String, matchTextContent: String): CustomJsoupElementLike
  def scopeParent: CustomJsoupElementLike
  def scopeClosestParent(tagName: String): CustomJsoupElementLike
  def replace(cssQuery: String, html: String): CustomJsoupElementLike
  def replace(cssQuery: String, arg: Element): CustomJsoupElementLike
  def replace(cssQuery: String, args: Seq[Element]): CustomJsoupElementLike
  def replaceAttr(cssQuery: String, attrMap:Map[String, String]): CustomJsoupElementLike
  def replaceAttr(cssQuery: String, attrName: String, attrValue:String): CustomJsoupElementLike
  def replaceAttrValue(attrName: String, targetAttrValue: String, replacementAttrValue: String): CustomJsoupElementLike
  def replaceAttrValue(attrName: String, targetAttrValue: String, replacementAttrValue: String, cssQuery: String): CustomJsoupElementLike
  def replaceTextContent(cssQuery: String): CustomJsoupElementLike
  def replaceTextContent(cssQuery: String, replacement: String): CustomJsoupElementLike
  def replaceElementAndOverwriteAttr(cssQuery: String, arg: Element): CustomJsoupElementLike
  def renameTag(tagName:String): CustomJsoupElementLike
  def remove: CustomJsoupElementLike
  def removeElement(cssQuery: String): CustomJsoupElementLike
  def removeAttr(attrName: String): CustomJsoupElementLike
  def removeAttrValue(attrName: String, targetAttrValue: String): CustomJsoupElementLike
  def removeAttrValue(attrName: String, targetAttrValue: String, cssQuery: String): CustomJsoupElementLike
  def removeClassAttrValueForDescendant(targetAttrValueList: Seq[String]): CustomJsoupElementLike
  def removeAttrSearched(attrName: String, cssQuery: String): CustomJsoupElementLike
  def getAttr: Map[String, String]
  def getAttr(attrName: String): Option[String]
  def getTagName: String
  def setAttr(attrMap:Map[String, String]):CustomJsoupElementLike
  def getTextContent: Option[String]
  def setTextContent(text :String, cssQuery: String): CustomJsoupElementLike
  def appendAttr(attrName: String, attrValue: String): CustomJsoupElementLike
  def appendAttr(cssQuery: String, attrName: String, attrValue: String): CustomJsoupElementLike
  def addBefore(arg: Element): CustomJsoupElementLike
  def addBefore(arg: String): CustomJsoupElementLike
  def addBeforeSearched(cssQuery: String, arg: Element): CustomJsoupElementLike
  def addAfter(arg: Element): CustomJsoupElementLike
  def addAfter(arg: String): CustomJsoupElementLike
  def addAfterSearched(cssQuery: String, arg: Element): CustomJsoupElementLike
  def addAfterSearched(cssQuery: String, arg: String): CustomJsoupElementLike
  def addParent(arg: Element): CustomJsoupElementLike
  def addParent(arg: String): CustomJsoupElementLike
  def addChildFromLast(arg: Element): CustomJsoupElementLike
  def addChildFromFirst(arg: Element): CustomJsoupElementLike
  def addChildSelectedBefore(cssQuery: String, arg: Element): CustomJsoupElementLike
  def addChildSelectedAfter(cssQuery: String, arg: Element): CustomJsoupElementLike
  def addDescendantFromLast(cssQuery: String, arg: Element): CustomJsoupElementLike
  def addDescendantFromFirst(cssQuery: String, arg: Element): CustomJsoupElementLike

}

case class CustomJsoupElement(underlying: org.jsoup.nodes.Element) extends CustomJsoupElementLike {

  override def getElement: org.jsoup.nodes.Element = underlying
  override def copySelfBefore: CustomJsoupElement = {
    if(underlying != null && !underlying.toString.isEmpty) {
      val tagname = underlying.tagName()
      underlying.tagName("div")
      val newElement = Jsoup.parse(underlying.toString)
      underlying.before(newElement.body().child(0))
      underlying.tagName(tagname)
      val befoe = underlying.previousElementSibling()
      befoe.tagName(tagname)
    }
    CustomJsoupElement(underlying)
  }
  override def copySelfAfter: CustomJsoupElement = {
    if(underlying != null && !underlying.toString.isEmpty) {
      val tagname = underlying.tagName()
      underlying.tagName("div")
      val newElement = Jsoup.parse(underlying.toString)
      underlying.after(newElement.body().child(0))
      underlying.tagName(tagname)
      val next = underlying.nextElementSibling()
      next.tagName(tagname)
    }
    CustomJsoupElement(underlying)
  }
  override def scopeClosestElement(cssQuery: String): CustomJsoupElement = {
    val scopes: Elements = underlying.select(cssQuery)
    if (!scopes.isEmpty) {
      CustomJsoupElement(scopes.first())
    } else {
      CustomJsoupElement(underlying)
    }
  }
  override def scopeClosestElements[T](cssQuery: String)(nextFunction: Element => T): CustomJsoupElement = {
    val scopes: Elements = underlying.select(cssQuery)
    if (!scopes.isEmpty) {
      for (scope <- scopes.asScala) {
        nextFunction(scope)
      }
    }
    CustomJsoupElement(underlying)
  }
  override def scopeClosestElement(cssQuery: String, matchTextContent: String): CustomJsoupElement = {
    val scopes: Elements = underlying.select(cssQuery)
    for (scope <- scopes.asScala) {
      if(scope.text() == matchTextContent) {
        return CustomJsoupElement(scope)
      }
    }
    CustomJsoupElement(underlying)
  }
  override def scopeParent: CustomJsoupElement = CustomJsoupElement(underlying.parent())
  override def scopeClosestParent(tagName: String): CustomJsoupElement = {
    for (scope <- underlying.parents().asScala) {
      if(scope.tagName() == tagName) {
        return CustomJsoupElement(scope)
      }
    }
    CustomJsoupElement(underlying)
  }
  override def scopeRoot: CustomJsoupElement = {
    val tmp = underlying.parents()
    if(!tmp.isEmpty) {
      CustomJsoupElement(tmp.last())
    } else {
      CustomJsoupElement(underlying)
    }
  }
  override def scopeBefore: CustomJsoupElement = {
    if(underlying.elementSiblingIndex() == 0) {
      CustomJsoupElement(underlying)
    } else {
      CustomJsoupElement(underlying.previousElementSibling())
    }
  }
  override def scopeAfter: CustomJsoupElement = {
    if(underlying.elementSiblingIndex() == 0) {
      CustomJsoupElement(underlying)
    } else {
      CustomJsoupElement(underlying.nextElementSibling())
    }
  }
  override def replace(cssQuery: String, html: String): CustomJsoupElement = {
    val scopes: Elements = underlying.select(cssQuery)
    for (scope <- scopes.asScala) {
      scope.before(html)
      scope.remove()
    }
    CustomJsoupElement(underlying)
  }
  override def replace(cssQuery: String, arg: Element): CustomJsoupElement = {
    val scopes: Elements = underlying.select(cssQuery)
    if (!scopes.isEmpty) {
      for (scope <- scopes.asScala) {
        scope.before(arg)
        scope.remove()
      }
    }
    CustomJsoupElement(underlying)
  }
  override def replace(cssQuery: String, args: Seq[Element]): CustomJsoupElement = {
    val scopes: Elements = underlying.select(cssQuery)
    if (!scopes.isEmpty) {
      for (scope <- scopes.asScala) {
        for(arg <- args) {
          scope.before(arg)
        }
        scope.remove()
      }
    }
    CustomJsoupElement(underlying)
  }
  override def replaceAttrValue(attrName: String, targetAttrValue: String, replacementAttrValue: String): CustomJsoupElement = {
    val tmpAttrValues: String = underlying.attr(attrName)
    val newAttrValues: String = tmpAttrValues.split(" ").map {
      case `targetAttrValue` => replacementAttrValue
      case x               => x
    }.mkString(" ")
    underlying.removeAttr(attrName)
    underlying.attr(attrName, newAttrValues.trim)
    CustomJsoupElement(underlying)
  }
  override def replaceAttrValue(attrName: String, targetAttrValue: String, replacementAttrValue: String, cssQuery: String): CustomJsoupElement = {
    val scopes: Elements = underlying.select(cssQuery)
    for (scope <- scopes.asScala) {
      val tmpAttrValues: String = scope.attr(attrName)
      val newAttrValues: String = tmpAttrValues.split(" ").map {
        case `targetAttrValue` => replacementAttrValue
        case x               => x
      }.mkString(" ")
      scope.removeAttr(attrName)
      scope.attr(attrName, newAttrValues.trim)
    }
    CustomJsoupElement(underlying)
  }
  override def replaceAttr(cssQuery: String, attrName: String, attrValue:String): CustomJsoupElement = {
    val scopes: Elements = underlying.select(cssQuery)
    for (scope <- scopes.asScala) {
      scope.attr(attrName,attrValue)
    }
    CustomJsoupElement(underlying)
  }
  override def replaceAttr(cssQuery: String, attrMap: Map[String, String]) :CustomJsoupElement = {
    val scopes: Elements = underlying.select(cssQuery)
    for (scope <- scopes.asScala) {
      for(attr <- attrMap) {
        scope.attr(attr._1,attr._2)
      }
    }
    CustomJsoupElement(underlying)
  }
  override def replaceTextContent(replacement:String): CustomJsoupElement = {
    underlying.text(replacement)
    CustomJsoupElement(underlying)
  }
  override def replaceTextContent(cssQuery: String, replacement:String): CustomJsoupElement = {
    val scopes: Elements = underlying.select(cssQuery)
    for (scope <- scopes.asScala) {
      scope.text(replacement)
    }
    CustomJsoupElement(underlying)
  }
  override def replaceElementAndOverwriteAttr(cssQuery: String, arg: Element) :CustomJsoupElement = {
    val scopes: Elements = underlying.select(cssQuery)
    for (scope <- scopes.asScala) {
      for(attr <- scope.attributes().asScala) {
        arg.attr(attr.getKey, attr.getValue)
      }
      scope.before(arg)
      scope.remove()
    }
    CustomJsoupElement(underlying)
  }
  override def renameTag(tagName:String) : CustomJsoupElement = {
    underlying.tagName(tagName)
    CustomJsoupElement(underlying)
  }
  override def remove: CustomJsoupElement = {
    val self = underlying
    self.remove()
    CustomJsoupElement(underlying)
  }
  override def removeElement(cssQuery: String): CustomJsoupElement = {
    val scopes: Elements = underlying.select(cssQuery)
    if (!scopes.isEmpty) {
      for (scope <- scopes.asScala) {
        scope.remove()
      }
    }
    CustomJsoupElement(underlying)
  }
  override def removeAttr(attrName: String): CustomJsoupElement = {
    underlying.removeAttr(attrName)
    CustomJsoupElement(underlying)
  }
  override def removeAttrValue(attrName: String, targetAttrValue: String): CustomJsoupElement = {
    replaceAttrValue(attrName, targetAttrValue, "")
  }
  override def removeAttrValue(attrName: String, targetAttrValue: String, cssQuery: String): CustomJsoupElement = {
    replaceAttrValue(attrName, targetAttrValue, "", cssQuery)
  }
  override def removeClassAttrValueForDescendant(targetAttrValueList: Seq[String]): CustomJsoupElement = {
    targetAttrValueList.foreach { targetAttrValue =>
      replaceAttrValue("class", targetAttrValue, "", "." + targetAttrValue)
    }
    CustomJsoupElement(underlying)
  }
  override def removeAttrSearched(attrName: String, cssQuery: String): CustomJsoupElement = {
    val scopes: Elements = underlying.select(cssQuery)
    for (scope <- scopes.asScala) {
      scope.removeAttr(attrName)
    }
    CustomJsoupElement(underlying)
  }
  override def getAttr: Map[String, String] = {
    val attr = underlying.attributes()
    (for (kv <- attr.asScala) yield {
      kv.getKey -> kv.getValue
    }).toMap
  }
  override def getAttr(attrName: String): Option[String] = {
    val a = underlying.attr(attrName)
    if (a.isEmpty) None else Some(a)
  }
  override def getTagName: String = underlying.tagName()
  override def getTextContent: Option[String] = {
    val a = underlying.text()
    if (a.isEmpty) None else Some(a)
  }
  override def setAttr(attrMap:Map[String, String]): CustomJsoupElement = {
    for(attr <- attrMap) {
      underlying.attr(attr._1,attr._2)
    }
    CustomJsoupElement(underlying)
  }
  override def setTextContent(text :String, cssQuery: String): CustomJsoupElement = {
    val scopes: Elements = underlying.select(cssQuery)
    for (scope <- scopes.asScala) {
      scope.text(text)
    }
    CustomJsoupElement(underlying)
  }
  def appendAttr(attrName: String, attrValue: String): CustomJsoupElement = {
    val attributes = underlying.attributes()
    val tmp: String = attributes.get(attrName)
    if (tmp.isEmpty) {
      attributes.put(attrName, attrValue)
    } else {
      attributes.put(attrName, tmp + " " + attrValue)
    }
    CustomJsoupElement(underlying)
  }
  def appendAttr(cssQuery: String, attrName: String, attrValue: String): CustomJsoupElement = {
    val scopes: Elements = underlying.select(cssQuery)
    for (scope <- scopes.asScala) {
      val attributes = underlying.attributes()
      val tmp: String = attributes.get(attrName)
      if (tmp.isEmpty) {
        attributes.put(attrName, attrValue)
      } else {
        attributes.put(attrName, tmp + " " + attrValue)
      }
    }
    CustomJsoupElement(underlying)
  }
  override def addBefore(arg: Element): CustomJsoupElement = CustomJsoupElement(underlying.before(arg))
  override def addBefore(html: String): CustomJsoupElement = CustomJsoupElement(underlying.before(html))
  override def addBeforeSearched(cssQuery: String, arg: Element): CustomJsoupElement = {
    val scopes: Elements = underlying.select(cssQuery)
    if (!scopes.isEmpty) {
      for (scope <- scopes.asScala) {
        scope.before(arg)
      }
    }
    CustomJsoupElement(underlying)
  }
  override def addAfter(arg: Element): CustomJsoupElement = CustomJsoupElement(underlying.after(arg))
  override def addAfter(html: String): CustomJsoupElement = CustomJsoupElement(underlying.after(html))
  override def addAfterSearched(cssQuery: String, arg: Element): CustomJsoupElement = {
    val scopes: Elements = underlying.select(cssQuery)
    if (!scopes.isEmpty) {
      for (scope <- scopes.asScala) {
        scope.after(arg)
      }
    }
    CustomJsoupElement(underlying)
  }
  override def addAfterSearched(cssQuery: String, html: String): CustomJsoupElement = {
    val scopes: Elements = underlying.select(cssQuery)
    for (scope <- scopes.asScala) {
      scope.after(html)
    }
    CustomJsoupElement(underlying)
  }
  override def addParent(arg: String): CustomJsoupElement = {
    val parent = underlying.parent()
    if(parent != null && !parent.toString.isEmpty) {
      underlying.before(arg)
      val afterCopy = underlying.parent().child(underlying.elementSiblingIndex() - 1)
      afterCopy.appendChild(Jsoup.parse(underlying.toString).body().child(0))
      underlying.remove()
      CustomJsoupElement(afterCopy.child(0))
    } else {
      CustomJsoupElement(underlying)
    }
  }
  override def addParent(arg: Element): CustomJsoupElement = {
    val parent = underlying.parent()
    if(parent != null && !parent.toString.isEmpty) {
      underlying.before(arg)
      val afterCopy = underlying.parent().child(underlying.elementSiblingIndex() - 1)
      afterCopy.appendChild(Jsoup.parse(underlying.toString).body().child(0))
      underlying.remove()
      CustomJsoupElement(afterCopy.child(0))
    } else {
      CustomJsoupElement(underlying)
    }
  }
  override def addChildFromLast(arg: Element): CustomJsoupElement = {
    underlying.appendChild(arg)
    CustomJsoupElement(underlying)
  }

  override def addChildFromFirst(arg: Element): CustomJsoupElement = {
    underlying.prependChild(arg)
    CustomJsoupElement(underlying)
  }
  override def addChildSelectedBefore(cssQuery: String, arg: Element): CustomJsoupElement = {
    for (child <- underlying.children().asScala) {
      for (selected <- underlying.select(cssQuery).asScala) {
        if (child.equals(selected)) {
          child.before(arg)
        }
      }
    }
    CustomJsoupElement(underlying)
  }
  override def addChildSelectedAfter(cssQuery: String, arg: Element): CustomJsoupElement = {
    for (child <- underlying.children().asScala) {
      for (selected <- underlying.select(cssQuery).asScala) {
        if (child.equals(selected)) {
          child.after(arg)
        }
      }
    }
    CustomJsoupElement(underlying)
  }
  override def addDescendantFromFirst(cssQuery: String, arg: Element): CustomJsoupElement = {
    val scopes: Elements = underlying.select(cssQuery)
    for (scope <- scopes.asScala) {
      scope.prependChild(arg)
    }
    CustomJsoupElement(underlying)
  }
  override def addDescendantFromLast(cssQuery: String, arg: Element): CustomJsoupElement = {
    val scopes: Elements = underlying.select(cssQuery)
    for (scope <- scopes.asScala) {
      scope.appendChild(arg)
    }
    CustomJsoupElement(underlying)
  }
}
