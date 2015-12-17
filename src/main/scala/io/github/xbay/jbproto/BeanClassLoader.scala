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
  def fromFile(filename: String): List[Message] = {
    val in: FileInputStream = new FileInputStream(filename)
    val cu: CompilationUnit = try {
      JavaParser.parse(in)
    } finally {
      in.close();
    }
    cu.getTypes.asScala.toList.map(t => {
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
      message
    })
  }
}
