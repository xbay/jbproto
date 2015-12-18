package io.github.xbay.jbproto

/**
 * Created by triplex on 12/14/15.
 */

import scala.collection.JavaConverters._
import java.io.FileInputStream
import com.github.javaparser._
import com.github.javaparser.ast._
import com.github.javaparser.ast.body._

object BeanClassLoader {
  def parseBean(t: TypeDeclaration): List[Message] = {
    var message = new Message(t.getName)
    t.getMembers.asScala.toList.filter(m => {
      m.isInstanceOf[FieldDeclaration]
    }).map(f => {
      val field = f.asInstanceOf[FieldDeclaration]
      val fieldType = field.getType
      field.getVariables.asScala.toList.foreach(v => {
        message.addBeanField(fieldType, v.getId.getName)
      })
    })
    val subMesseges = t.getMembers.asScala.toList.filter(m => {
      m.isInstanceOf[TypeDeclaration]
    }).map(t => {
      parseBean(t.asInstanceOf[TypeDeclaration])
    }).foldLeft(List[Message]())((a, b) => {
      a ++ b
    })
    List(message) ++ subMesseges
  }

  def fromFile(filename: String): List[Message] = {
    val in: FileInputStream = new FileInputStream(filename)
    val cu: CompilationUnit = try {
      JavaParser.parse(in)
    } finally {
      in.close();
    }
    cu.getTypes.asScala.toList.map(t => {
      parseBean(t)
    }).foldLeft(List[Message]())((a, b) => {
      a ++ b
    })
  }
}
