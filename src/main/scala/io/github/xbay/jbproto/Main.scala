package io.github.xbay.jbproto

/**
 * Created by uni.x.bell on 10/8/15.
 */
object Main {
  def main(args: Array[String]) = {
    val messages: List[Message] = BeanClassLoader.fromFile(args(0))
    messages.foreach(message => {
      println(message.toProtoDef())
      //message.toProtoDef()
    })
  }
}
