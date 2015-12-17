package io.github.xbay.jbproto

import scala.collection.JavaConverters._
import com.github.javaparser._
import com.github.javaparser.ast._
import com.github.javaparser.ast.`type`._
import com.github.javaparser.ast.body._

/**
 * Created by uni.x.bell on 12/9/15.
 */

sealed trait TypePrefix
case object Required extends TypePrefix
case object Optional extends TypePrefix
case object Repeated extends TypePrefix

object TypePrefix {
  def apply(value: String): TypePrefix = value match {
    case "required" => Required
    case "repeated" => Repeated
    case _ => Optional
  }

  def stringOf(typePrefix: TypePrefix): String = typePrefix match {
    case Required => "required"
    case Repeated => "repeated"
    case Optional => "optional"
  }
}

sealed trait FieldType
case object DoubleType extends FieldType
case object FloatType extends FieldType
case object Int32Type extends FieldType
case object Int64Type extends FieldType
case object BoolType extends FieldType
case object StringType extends FieldType
case class MessageType(name: String) extends FieldType

object FieldType {
  def apply(value: String): FieldType = value match {
    case "double" => DoubleType
    case "float" => FloatType
    case "int32" => Int32Type
    case "int64" => Int64Type
    case "bool" => BoolType
    case "string" => StringType
    case name: String => MessageType(name)
  }

  def stringOf(fieldType: FieldType): String = fieldType match {
    case DoubleType => "double"
    case FloatType => "float"
    case Int32Type => "int32"
    case Int64Type => "int64"
    case BoolType => "bool"
    case StringType => "string"
    case message: MessageType => message.name
  }
}

case class Field (
  prefix: TypePrefix,
  fieldType: FieldType,
  fieldName: String
)

class Message(_name: String) {
  val name = _name
  var fields: List[Field] = List()
  var depenences: Set[String] = Set()

  def addBeanField(fieldType: Type, fieldName: String) = {
    val children = fieldType.getChildrenNodes()

    if(fieldType.isInstanceOf[PrimitiveType]){
      val primitiveType = fieldType.asInstanceOf[PrimitiveType]
      primitiveType.getType() match {
        case PrimitiveType.Primitive.Boolean => addField(Required, BoolType, fieldName)
        case PrimitiveType.Primitive.Char => addField(Required, Int32Type, fieldName)
        case PrimitiveType.Primitive.Byte => addField(Required, Int32Type, fieldName)
        case PrimitiveType.Primitive.Short => addField(Required, Int32Type, fieldName)
        case PrimitiveType.Primitive.Int => addField(Required, Int32Type, fieldName)
        case PrimitiveType.Primitive.Long => addField(Required, Int64Type, fieldName)
        case PrimitiveType.Primitive.Float => addField(Required, FloatType, fieldName)
        case PrimitiveType.Primitive.Double => addField(Required, DoubleType, fieldName)
      }
    } else if(fieldType.isInstanceOf[ReferenceType]) {
      val referenceType = fieldType.asInstanceOf[ReferenceType]
      val referencedType = referenceType.getChildrenNodes().asScala.toList.head
      if(referencedType.isInstanceOf[ClassOrInterfaceType]) {
        val classType = referencedType.asInstanceOf[ClassOrInterfaceType]
        classType.getName match {
          case "String" => addField(Required, StringType, fieldName)
          case "List" => {
            addField(Repeated, {
              val listClassType = classType.getChildrenNodes.asScala.toList.head
                .asInstanceOf[ReferenceType].getChildrenNodes.asScala.toList.head
                .asInstanceOf[ClassOrInterfaceType]
              listClassType.getName match {
                case "String" => StringType
                case className: String => MessageType(className)
              }
            }, fieldName)
          }
          case className: String => addField(Required, MessageType(className), fieldName)
        }
      }
    }
  }

  def addField(prefix: TypePrefix, fieldType: FieldType, fieldName: String): Option[Field] = {
    val field = Field(prefix, fieldType, fieldName)
    fields = fields ++ List(field)
    fieldType match {
      case t: MessageType => depenences = depenences ++ Set(t.name)
      case _ =>
    }
    Some(field)
  }

  def toProtoDef(): String = {
    val fieldsProto = fields.map(field => {
      "%s %s %s".format(
        TypePrefix.stringOf(field.prefix),
        FieldType.stringOf(field.fieldType),
        field.fieldName)
    })

    val filedsString = fieldsProto.foldLeft(("", 1))((a, b) => {
      (a._1 + "\n\t" + b + " = %d;".format(a._2), a._2 + 1)
    })._1
    "message %s {\n%s\n}\n".format(name, filedsString)
  }
}
