package io.github.xbay.jbproto

/**
 * Created by uni.x.bell on 10/8/15.
 */
object Main {

  def main(args: Array[String]) = {
    val params = parseParams(args)
    val files = parseFiles(args)
    val messages = files.map(file => {
      BeanClassLoader.fromFile(file)
    }).foldLeft(List[Message]())((a, b) => {
      a ++ b
    })
    sortMessage(messages).foreach(message => {
      println(message.toProtoDef())
    })
  }

  def dependenceSolved(message: Message, dependenceTable: Set[String]): Boolean = {
    message.depenences.filter(dep => {
      dependenceTable.contains(dep)
    }).isEmpty
  }

  def doSortMessage(
    messageStack: List[Message],
    messages: List[Message],
    dependenceTable: Set[String]): List[Message] = {
    val solvedMessages = messages.filter(message => {
      dependenceSolved(message, dependenceTable)
    })
    val unsolvedMessages = messages.filter(message => {
      !dependenceSolved(message, dependenceTable)
    })
    if(solvedMessages.isEmpty) {
      messageStack
    } else {
      doSortMessage(
        solvedMessages ++ messageStack,
        unsolvedMessages,
        solvedMessages.foldLeft(dependenceTable)((a, b) => { a ++ Set(b.name)}))
    }
  }

  def sortMessage(messages: List[Message]): List[Message] = {
    val messageStack = List[Message]()
    val dependenceTable = Set[String]()

    doSortMessage(messageStack, messages, dependenceTable).reverse
  }

  def parseParams(args: Array[String]): Map[String, String] = {
    args.grouped(2)
    .filter(arg => { arg.head.startsWith("-") && arg.size == 2})
    .foldLeft(Map[String, String]())((a, b) => {
      a ++ Map[String, String](Tuple2(b.head.substring(1), b.tail.head))
    })
  }

  def parseFiles(args: Array[String]): List[String] = {
    args.grouped(2)
    .filter(arg => { !(arg.head.startsWith("-") && arg.size == 2) })
    .foldLeft(List[String]())((a, b) => {
      a ++ b
    })
  }
}
