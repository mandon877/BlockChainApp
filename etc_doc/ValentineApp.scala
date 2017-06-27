object ValentineApp extends App {
  println("Hello world!!!!")
  
  var i = 10
  i = 1
  i.toString
  val name = "Jung"

  println(name + i)
  println("=====================================")
  
//  val item = Item(1L, "A", 11.11)
//  item.name = "B"
  
  def info(item: Item): Unit = item match {
    case Item(1L, _, _) =>
      println("The item id is 1")
      
    case Item(_, "B", price) =>
      println(s"B is $$$price")
  
    case Item(id, name, price) => 
      println(s"id: $id, name: $name, price: $price")
  }
  
  info(Item(1L, "A", 11.11))
  info(Item(2L, "B", 11.11))
  info(Item(3L, "C", 11.11))
  
  println("============================")
  val nums = List(1,2,3,4,5)
  
  nums.foreach(println)
  println("============================")
  
  val doubled = nums.map(_ * 2).filter(_ > 4) 
  
  
  println(s"doubled: $doubled")
  println("------------------------------")
  
  val twoTwoNums = "([\\d]{2})-([\\d]{2})-([\\d]{2})".r  
  
  def info(x: String): Unit = x match {
    case twoTwoNums(a, b, c) if a == b && b == c =>
      println(s"a == b == c")
      
    case twoTwoNums("12", b , c) =>
      println(s"a is 12, b: $b, c: $c")
      
    case twoTwoNums(a, b, c) =>
      println(s"a: $a, b: $b, c: $c")
      
    case _ =>
      println   ("Nothing matched")
  }
  
  println("------------------------------")
  info("11-11-11")
  info("12-34-56")
  info("12-34-567")
  info("abcd")
}  

class ValentineApp {
  import ValentineApp.name
  
  def getName: String = name
  def greet(name: String): Unit =  println("Hello " + name);
  println(name)
//  
//  val item2 = new Item2(2L, "B", 12)
//  val item2_1 = new Item2(2L, "B", 12)
}

