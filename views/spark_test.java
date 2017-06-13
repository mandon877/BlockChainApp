/*

// 환경 설정
1. Oracle VM VirturalBox로 아래 linux환경을 가져오기 한다.
   (http://www.oracle.com/technetwork/server-storage/virtualbox/downloads/index.html)
   - 파일 -> 가상시스템 가져오기-> 1번 파일 선택
     HDP_2.6_virtualbox_05_05_2017_14_46_00_hdp.ova
     (https://ko.hortonworks.com/downloads/)
2. PuTTY로 터미널 접속
   (https://www.chiark.greenend.org.uk/~sgtatham/putty/latest.html)
   - localhost , 2222
   - su - spark
3. 기타 파일
   - scala-SDK-4.6.0-vfinal-2.12-win32.win32.x86_64.zip

4. 아래 깃 허브에서 순서대로 신행한다.    
    

// 교육 자료 다운로드 //
https://github.com/skholdings/spark_lecture
su - spark
wget https://github.com/skholdings/spark_lecture/raw/master/sample_data.tar.gz
wget https://github.com/skholdings/spark_lecture/raw/master/script.tar.gz

tar xvf script.tar.gz
tar xvf sample_data.tar.gz
hadoop fs -put lecture/data/* /user/spark

//spark 시작 명령어//
./start.sh
sc.appName
val logfile = "/user/spark/web.log"
val logs = sc.textFile(logfile)
logs.map(_.split('[')(1)).map(_.split(']')(0)).collect
logs.map(_.split('[')(1)).map(_.split(']')(0)).saveAsTextFile("/user/spark/logdate")

hadoop fs -ls /user/spark/logdate
hadoop fs -cat /user/spark/iplist


// 실습내용 //
val books = sc.?("books.xml")
val books = sc.wholeTextFiles("books.xml")

val catalog = scals.xml.XML.loadString(books.first_2)

val title = (catalog \\ "book" \\ ?.map(_.text)
val title = (catalog \\ "book" \\ "title".map(_.text)

val publish = (catalog \\ "book" \\ ?).map(_.text)
val publish = (catalog \\ "book" \\ "publish_date").map(_.text)

val titleRdd = sc.parallelize(title)
val publishRdd = sc.parallelize(publish)

val result = ?
val result = titleRdd.zip(publishRdd)

result.foreach(record => println(record))
-------------------------------------------------------------------------------------------------------
val products = sc.textFile("item.txt")
val productsPair = products.map(line => line.split(',')).map(fields => (fields(0), fields(1)))
productsPair.collect
-----------------------------------------------------------------------------------------------------
val purchaseFlat = sc.textFile("purchase.txt").map(line => line.split(' ')).map(fields => (fields(0), fields

(1))).flatMapValues(prod=> prod.split(';'))
val user = sc.textFile("user.txt").map(line => line.split(' ')).map(fields => (fields(0), fields(1)))
val result = purchaseFlat.join(user)
result.collect
--------------------------------------------------------------------------------------------------------------
val items = sc.textFile("item.txt").map(_.split(',')).map(f => (f(0),f(1)))
items.collect

var purchaseCnt = sc.textFile("web.log").
filter(line => line.contains("action=purchase")).
map(line => line.split("&")(1)).
map(line => (line.split("=")(1), 1)).
reduceByKey((v1, v2) => v1 + v2)

puchaseCnt.collect
purchase.foreach(print)

val result = items.join(purchaseCnt)
result.values.foreach(println)
result.foreach(println)

-------------------------------------------------------------------------------------------------------------
http://localhost:4040/jobs/
http://localhost:18081/ or http://localhost:18080/

val proverb = sc.textFile("proverbs.txt")

----------------------------------------------------------------------
spark Application 개발 환경 설정
--------------------------------------------------------------------------
https://github.com/skholdings/
https://github.com/skholdings/spark_lecture_05/
==> Clone or downdoad==> eclipse에서 불러온다.
==> 우클릭 ==> import ==> Git ==> Projects from Git ==> next ==> 

Clone URI
==> next ==> URI : https://github.com/skholdings/spark_lecture_05
             ==> Host : github.com
             ==> Repository : /skholdings/spark_lectre_05 ==> 쭉 next ==> 다

운로드 진행
--------------------------------------------------------------------------
Package Explorer > lecture05 > Scala Library container > 우클릭 Properties > 

Fixed Scala Library container : 2.10.6으로 변경
안되면 C:\Users\Administrator\.m2\repository 여기 내용 지우고 하면 됨

------------------------------------------------------------------------------------
sc.textFile("wordcount.txt", 4).
flatMap(line => line.split(' ')).
map(word => (word(0), word.length)).
groupByKey(2).
map(v => (v._1, v._2.sum / v._2.size.toDouble)).
toDebugString

-------------------------------------------------------------------------------------------------------------

sc.textFile("wordcount.txt", 4).
flatMap(line => line.split(' ')).
map(word => (word(0), word.length)).
groupByKey(2).
map(v => (v._1, v._2.sum / v._2.size.toDouble)).
collect

sc.textFile("wordcount.txt", 4).
flatMap(line => line.split(' ')).
map(word => (word(0), word.length)).
groupByKey(2).
map(v => (v._1, v._2.sum / v._2.size.toDouble)).
saveAsTextFile("result")

hadoop fs -ls /user/spark/result
hadoop fs -cat /user/spark/result/*
------------------------------------------------------------------------------
val itemName = sc.textFile("item.txt").map(_.split(',')).map(f => (f(0),f(1)))

var purchaseCnt = sc.textFile("web.log",5).
filter(line => line.contains("action=purchase")).
map(line => line.split("&")(1)).
map(line => (line.split("=")(1), 1)).
reduceByKey((v1, v2) => v1 + v2)

val result = itemName.join(purchaseCnt).values

result.toDebugString
result.collect

---------------------------------------------------------------------------
///////////////////////////////////////////////////////////////////////////
//스칼라 소스
// lecture05/src/main/scala/lecture.sk.com/WeblogCounter.scala
///////////////////////////////////////////////////////////////////////////
package lecture.sk.com

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object WeblogCounter {
  def main(args: Array[String]) {
    val conf = new SparkConf()
      .setAppName("StreamingTest")

    val sc = new SparkContext(conf)
    

    ///////////////////////////////////////////////////////////////////////
    // spark 소스
    //////////////////////////////////////////////////////////////////////
    var item = sc.textFile("item.txt")
    val itemName = item.map(_.split(',')).map(f => (f(0),f(1)))
  	
    val weblog = sc.textFile("web.log")
    val purchaseCnt = weblog.filter(line => line.contains("action=purchase")).
    map(line => line.split("&")(1)).
    map(line => (line.split("=")(1), 1)).
    reduceByKey((v1, v2) => v1 + v2)
    
    val result = itemName.join(purchaseCnt).values
    
    result.saveAsObjectFile("item_count")
    
    sc.stop()
  }
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////
// Run As 에서 Maven build > Goals: package 넣고 > Run > 많은 것을 download 받고 > BUILD 

SUCCESS 
// Refresh > target에 jar 파일 생성 
// lecture05-1.0-jar-with-dependencies.jar
// lecture05-1.0.jar
////////////////////////////////////////////////////////////////////////////////////////////////////////
05. 애플리케이션 개발 및 배포

실습용 예제 파일 다운로드
wget https://raw.githubusercontent.com/skholdings/spark_lecture_05/master/src/main/resources/item.txt

wget https://raw.githubusercontent.com/skholdings/spark_lecture_05/master/src/main/resources/web.log


빌드 파일 HDFS 업로드
http://localhost:8080/#/main/view/FILES/auto_files_instance
(Ambari에서 확인)

ID : raj_ops

Password : raj_ops

/user/spark에 빌드한 JAR파일 업로드

HDFS에서 jar 파일 확인 확인
hadoop fs -ls /user/spark/lecture05-1.0-jar-with-dependencies.jar

리눅스 로컬로 다운로드
hadoop fs -get /user/spark/lecture05-1.0-jar-with-dependencies.jar


예제 실행
spark-submit --master yarn-client --class lecture.sk.com.WeblogCounter lecture05-1.0-jar-with-dependencies.jar

확인(질문 출력 내용 이상함)
hadoop fs -cat /user/spark/item_count/*
http://localhost:8088/cluster

------------------------------------------------------------------------------------------------------------------
// 영속화 실습
var item = sc.textFile("item.txt")
val itemName = item.map(_.split(',')).map(f => (f(0),f(1)))
  	
val weblog = sc.textFile("web.log")
val purchaseCnt = weblog.filter(line => line.contains("action=purchase")).
map(line => line.split("&")(1)).
map(line => (line.split("=")(1), 1)).
reduceByKey((v1, v2) => v1 + v2)
    
val result = itemName.join(purchaseCnt).values

val filtered = result.filter(pair => pair._2 > 5)
result.persist()

filtered.toDebugString

filtered.collect

filtered.toDebugString

filtered.collect

result.unpersist()
-------------------------------------------------------------------------------------------------------
// Spark 스트리밍 처리(5초단위로 끈어서 RDD에 저장한다.)
tcp 서버 구동(리눅스 창 하나 더 띄운다)
nc -lk 8282
==> 기다리고 있다.
scala> import org.apache.spark.streaming.StreamingContext
scala> import org.apache.spark.streaming.Seconds
scala> val ssc = new StreamingContext(sc,Seconds(5))
scala> val mystream = ssc.socketTextStream("localhost",8282)
scala> val words = mystream.map(line => (line, 1)).reduceByKey((v1, v2) => (v1+ v2))
scala> words.print()
scala> ssc.start()
==> tcp 서버에서 아무 데이터나 넣는다(5초 단위 끊어서 RDD에 저장한다.)
scala> ssc.stop()

------------------------------------------------------------------------------------------------------------------
//단어 누적 개수 구하기(질문 에러)
var mystream = ssc.socketTextStream("localhost", 8282)
var words = mystream.map(line => (line, 1)).reduceByKey((v1, v2) => (v1 + v2))
def updateCount = (newCounts:Seq[Int], state:Option[Int]) => {
  val newCount = newCounts.foldLeft(0)(_+_)
  val previousCount = state.getOrElse(0)
  Some(newCount + previousCount)
}
val totalWords = words.updateStateByKey(updateCount)
val totalWords.print()
ssc.checkpoint("checkpoint")
ssc.start()
------------------------------------------------------------------------------------------------------------------
// Spark 스트밍과 Kafka 통합
https://github.com/skholdings/spark_lecture_09.git
github에서 연다.
이 파일에 spark 소스 삽입 : WeblogStreamCounterUpdate.scala
----------------------
package lecture.sk.com

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.Seconds

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe

object WeblogStreamCounterUpdate {
  def main(args: Array[String]) {
    val conf = new SparkConf()
      .setAppName("StreamingTest")

    val sc = new SparkContext(conf)

    val ssc = new StreamingContext(sc, Seconds(5))

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "sandbox:6667",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "sk",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean))

    val topics = Array("weblog")

    val stream = KafkaUtils.createDirectStream[String, String](
      ssc,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams))

    val weblog = stream.map(record => record.value)
    
    /////////////////////////////////////////
    // 여기 삽입
    ///////////////////////////////////////
    
    var mystream = ssc.socketTextStream("localhost", 8282)
    var words = mystream.map(line => (line, 1)).reduceByKey((v1, v2) => (v1 + v2))
    
    def updateCount = (newCounts:Seq[int], state:Option[int]) => {
        val newCount = newCounts.foldLeft(0)(_+_)
        val previousCount = state.getOrElse(0)
        Some(newCount + previousCount)
    }
    
    .....
    .....
    
    //////////////////////////////////////
    ssc.start()

    ssc.awaitTermination()
  }
}



-----------------------------------------------------------------------------------------------------------------*/








