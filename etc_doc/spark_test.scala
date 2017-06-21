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
val catalog = scala.xml.XML.loadString(books.first)

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
val purchaseFlat = sc.textFile("purchase.txt").map(line => line.split(' ')).map(fields => (fields(0), fields(1))).flatMapValues(prod=> prod.split(';'))
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

purchaseCnt.collect
purchaseCnt.foreach(print)

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

다운로드 진행
--------------------------------------------------------------------------
Package Explorer > lecture05 > Scala Library container > 우클릭 Properties > 

Fixed Scala Library container : 2.10.6으로 변경
안되면 C:\Users\Administrator\.m2\repository 여기 내용 지우고 하면 됨
안되면 lecture05에서 우클릭 -> Maven > Update Project(Alt+F5) > OK 클릭

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
/////////////////////////////////////////////////////////
// 다운로드
// 참조: https://github.com/skholdings/spark_lecture_09
// scala-SDK-4.6.0-vfinal-2.12-win32.win32.x86_64.zip
// HDFS ambari 설치
//
// ubuntu-16.04.1-desktop-amd64.iso
// mongodb-win32-x86_64-enterprise-windows-64-3.4.0.zip
///////////////////////////////////////////////////////
// Spark 스트밍과 Kafka 통합
https://github.com/skholdings/spark_lecture_09.git
github에서 연다.
이 파일에 spark 소스 삽입 : WeblogStreamCounterUpdate.scala
----------------------------------------------------------------------------------------------------------------------
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
    
    val itemName = sc.textFile("item.txt")
      .map(line => line.split(','))
      .map(fields => (fields(0), fields(1)))

    val purchaseCnt = weblog.transform(rdd => {
      rdd.filter(line => line.contains("action=purchase"))
        .map(line => line.split("&")(1))
        .map(line => (line.split("=")(1), 1))
        .reduceByKey((v1, v2) => v1 + v2)
    })

    val purchaseTotal = purchaseCnt.updateStateByKey(updateCount)

    purchaseTotal.foreachRDD(rdd => {
      val result = itemName.join(rdd).values
      result.saveAsTextFile("/tmp/result")
    })

    ssc.checkpoint("/tmp/checkpoint")
    
    //////////////////////////////////////
    ssc.start()

    ssc.awaitTermination()
  }
}

---------------------------------------------------------------------------------------
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
      .setMaster("local[2]")
      .set("spark.ui.port", "4040")

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

    ////////////////////////////////////////////////
    val itemName = sc.textFile("item.txt")
      .map(line => line.split(','))
      .map(fields => (fields(0), fields(1)))

    val purchaseCnt = weblog.transform(rdd => {
      rdd.filter(line => line.contains("action=purchase"))
        .map(line => line.split("&")(1))
        .map(line => (line.split("=")(1), 1))
        .reduceByKey((v1, v2) => v1 + v2)
    })

    val purchaseTotal = purchaseCnt.updateStateByKey(updateCount)

    purchaseTotal.foreachRDD(rdd => {
      val result = itemName.join(rdd).values
      result.saveAsTextFile("/tmp/result")
    })

    ssc.checkpoint("/tmp/checkpoint")
    //////////////////////////////////////
    
    ssc.start()

    ssc.awaitTermination()
  }

  def updateCount = (newCounts: Seq[Int], state: Option[Int]) => {
    val newCount = newCounts.foldLeft(0)(_ + _)
    val previousCount = state.getOrElse(0)
    Some(newCount + previousCount)
  }
}

-----------------------------------------------------------------------------------------------------------------

//////////////////////////////////
// 환경설정 2017-06-14 (수)
/////////////////////////////////
// spark 2.0 버전으로 실행
export SPARK_MAJOR_VERSION=2
./spark-shell

//실습 파일 다운로드 및 HDFS에 put
wget https://raw.githubusercontent.com/sagara00/spark_lecture/master/dataset/iris.txt
wget https://raw.githubusercontent.com/sagara00/spark_lecture/master/dataset/pages.txt
hadoop fs -put iris.txt
hadoop fs -put pages.txt
hadoop fs -put /usr/hdp/2.6.0.3-8/spark2/README.md
==> ambari에서 확인
* 가이드 페이지 ==> spark programming guide
* github addr: https://github.com/sagara00/spark_lecture

// 10장
// broadcast, accumulator 예제
// 1. collection으로부터 RDD 생성 후
// 2. pws를 참조하여 key에 해당하는 값을 참조, map partition RDD 생성
// 3. collect로 array[String] 생성
--------------------
val pws = Map("Apache Spark" -> "http://spark.apache.org/", "Scala" -> 

"http://www.scala-lang.org/")
val websites = sc.parallelize(Seq("Apache Spark", "Scala")).map(pws).collect
----------------------------
val pws = Map("Apache Spark" -> "http://spark.apache.org/", "Scala" -> 

"http://www.scala-lang.org/")
val pwsB = sc.broadcast(pws)
val websites = sc.parallelize(Seq("Apache Spark", "Scala")).map(pwsB.value).collect
==> woker node 단위로 보내서 속도 향상
//pws가 대량의 데이터일 경우 속도저하 유발함. 분산처리하는 모든 executor에 pws 네트워

크전송 유발
--------------------------
//모든 단어의 평균 길이 구하기
val words = sc.textFile("/Users/Sagara/spark/README.md").flatMap(line => line.split(' '))
//Double = 5.73015873015873

//words.map(word => (word, word.length)).reduceByKey((key,value) => key + value).map(x 
=> x._2).reduce((x,y)=>x+y).toDouble / words.count
words.map(word => word.length).reduce((x,y)=>x+y).toDouble / words.count
//words.map(word => (word, word.length)).reduceByKey((key,value) => key + value).collect


//accumulator 로 구하기
------ 파이선 --
def addTotals (word, words, letters) :
     words += 1
     letters += len(word)
totalWords = sc.accumulator(0)
totalWords = sc.accumulator(0.0)
print "Average word length: ", totalLetters.value/totalWords.value
------ scala --

import org.apache.spark.Accumulator
def addTotals (word:String, words:Accumulator[Int], letters:Accumulator[Double]) {
    words += 1
    letters += word.length
}
var totalWords = sc.accumulator(0)
var totalLetters = sc.accumulator(0.0)

var words = sc.textFile("README.md").flatMap(line => line.split(' '))
words.foreach(word => addTotals(word, totalWords, totalLetters))
print ("Average word length: ", totalLetters.value/totalWords.value)
---------------------------------------------------------------------------------------------------------------------
//11장 실습
import org.apache.spark.sql.SQLContext
import scala.collection.mutable.ArrayBuffer
import org.apache.spark.sql.SparkSession

//val spark = SparkSession.builder().appName("Spark ML example").config

("spark.some.config.option", "some-value").getOrCreate()
//val sc = spark.sparkContext

val sqlContext = new org.apache.spark.sql.SQLContext(sc)

// csv파일에 header유무 확인, delimiter 확인
// Input file loading..
val df = sqlContext.read.format("com.databricks.spark.csv").option("header", "true").option

("inferSchema", "true").load("iris.txt")

// df. 탭
df.

// schema 정보 및 data확인
df.printSchema

// 컬럼 개수 확인
df.schema.length

// 데이터 내용 흝어보기
df.show

import org.apache.spark.storage.StorageLevel

//메모리 영속화
df.persist(StorageLevel.MEMORY_ONLY)

//영속화 해제
df.unpersist()
//컬럼명 리턴
df.columns
//타입 array 리턴
df.dtypes
//디버그정보 출력
df.explain

//Row object의 array 반환
df.collect

//10개 출력하기
df.take(10).foreach(println)
df.take(10).show //오류 

// dataframe을 리턴하는 것은 limit
df.limit(10).show

//아래는 같음
df.take(1)
df.first

//sepal length의 평균
df.select(avg($"sepal_length")).show
//val mean = df.select(avg($"sepal_length")).first.getDouble(0)

//df에서 rdd생성
val rdd = df.rdd

//sepal length의 평균 (RDD)
rdd.map(x=> (x.getDouble(0))).reduce((x,y)=>x+y) / rdd.count

//종(species)의 중복 제거
df.select("species").distinct.show

//정렬
df.sort("sepal_width").show
df.orderBy("sepal_width").show  //orderBy는 sort의 alias임
//def orderBy(sortCol: String, sortCols: String*): Dataset[T] = sort(sortCol, sortCols : _*)

//내림차순 정렬
df.sort($"sepal_width".desc).show


// petal_width가 0.5 이상인 것
df.where("petal_width > 0.5").show

//통계정보 보기 (숫자타입)
df.select("sepal_width").describe().collect().mkString(",")

//통계정보 보기 (문자 타입)
df.groupBy("species").count.sort(desc("count")).collect().mkString(",")

//sql문 작성
// setosa종의 sepal 길이, petal 길이 출력
df.registerTempTable("iris") //테이블 생성
spark.sql("""select sepal_length, petal_length, species from iris where species = "setosa" 

""").show

//rdd에서 df 생성 스키마+rdd = dataframe
val schema = df.schema
val df2 = spark.createDataFrame(rdd, schema)
--------------------------------------------------------------------------------------------------------------------
//12장 자주 사용되는 Spark 데이터처리 패턴
//1. 반복알고리즘 실습
val iters = 10
val lines = spark.read.textFile("pages.txt").rdd

val links = lines.map{ s => 
     val parts = s.split("\\s+") 
     (parts(0), parts(1))
}.distinct().groupByKey().cache()

//compactBuffer (scala의 arrayBuffer, java의 AyList)
links.collect.foreach(println)

//links.mapValues(f) 는 pair RDD구조에서, links.map {case (k, v) => (k, f(v))}의 축약
var ranks = links.mapValues(v => 1.0)
ranks.collect.foreach(println)

//반복 알고리즘 동작
for (i <- 1 to iters) {
 //rank를 이웃으로 분배
  val contribs = links.join(ranks).values.flatMap{ case (urls, rank) =>
                   //TODO: 구현1 기여도 계산
                  val size = urls.size
                  urls.map(url, rank /size))
  }
  //TODO: 구현2. 새로운 rank 계산
  ranks = contribs.reduceByKey(_ + _).mapValues(0.15 + 0.85 * _)

  //출력
  println("iters:"+i)
  ranks.collect().foreach(tup => println(tup._1 + " has rank: " + tup._2 + "."))
  println("\n")
}


------------------------------------------------------------------------------------------------------------------
//2. classification 실습
import org.apache.spark.sql.SQLContext
import org.apache.spark.ml.feature.Normalizer
import scala.collection.mutable.ArrayBuffer
import org.apache.spark.ml.classification.{RandomForestClassificationModel, 

RandomForestClassifier, DecisionTreeClassifier}
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer, 

VectorAssembler}
import org.apache.spark.ml.classification.{GBTClassificationModel, GBTClassifier}
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.tuning.{ParamGridBuilder, CrossValidator, 

TrainValidationSplit}
import org.apache.spark.ml.{Pipeline, PipelineModel}
import org.apache.spark.ml.classification.DecisionTreeClassificationModel
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer, 

VectorAssembler}
import org.apache.spark.sql.SparkSession

//val spark = SparkSession.builder().appName("Spark ML example").config

("spark.some.config.option", "some-value").getOrCreate()
//val sc = spark.sparkContext

val sqlContext = new org.apache.spark.sql.SQLContext(sc)

// csv파일에 header유무 확인, delimiter 확인
// Input file loading..
val df = sqlContext.read.format("com.databricks.spark.csv").option("header", 

"true").option("inferSchema", "true").load("iris.txt")

// schema 정보 및 data확인
df.printSchema

// 컬럼 개수 확인
df.schema.length

// 데이터 내용 흝어보기
df.show


// Training 과 Test 셋으로 Split Data (7:3비율)
val Array(trainingData, testData) = df.randomSplit(Array(0.7, 0.3))


// input columns로 feature 생성
val assembler = new VectorAssembler().setInputCols(Array("sepal_length", 

"sepal_width", "petal_length", "petal_width")).setOutputCol("features")

// LabelIndexer로 Label컬럼을 LabelIndex로 변경
val labelIndexer = new StringIndexer().setInputCol("species").setOutputCol

("indexedLabel")

// FeatureIndexer
val featureIndexer = new VectorIndexer().setInputCol("features").setOutputCol

("indexedFeatures").setMaxCategories(4)

// param descriptions
//     maxCategory : Threshold for the number of values a categorical feature can take. 

// If a feature is found to have > maxCategories values, then it is declared continuous. 

// Must be greater than or equal to 2.
//     impurity : Criterion used for information gain calculation. Supported: "entropy" 

// and "gini". (default = gini)
//     maxBins : Maximum number of bins used for discretizing continuous features 

// and for choosing how to split on features at each node.
//     maxDepth : Maximum depth of the tree (>= 0).
//     seed : Param for random seed.
//     automodel : choose whether to use autoML
//     terminateAccu : terminate automl when accuracy gain more than this value.
//   default value
//     maxCategory=20
//     impurity="gini"
//         impurity={gini,entropy}
//     maxBins=32
//     maxDepth=5
//     seed = 1234L

val algorithm = new DecisionTreeClassifier().setLabelCol

("indexedLabel").setFeaturesCol("indexedFeatures") //.setImpurity

("entropy").setMaxBins(32)
//val algorithm = new RandomForestClassifier().setLabelCol

("indexedLabel").setFeaturesCol("indexedFeatures").setNumTrees(10)


// Label Converter
val labelConverter = new IndexToString().setInputCol("prediction").setOutputCol

("predictedLabel").setLabels(labelIndexer.fit(trainingData).labels)

// Pipeline 만들기
val pipeline = new Pipeline().setStages(Array(assembler, labelIndexer, featureIndexer, 

algorithm, labelConverter))

// Training 
val model = pipeline.fit(trainingData)

// Test
val result = model.transform(testData)

// 결과 보기
result.printSchema
result.show


// Evaluation
val evaluator = new MulticlassClassificationEvaluator().setLabelCol

("indexedLabel").setPredictionCol("prediction").setMetricName("accuracy")
val accuracy = evaluator.evaluate(result)

val evaluator = new MulticlassClassificationEvaluator().setLabelCol

("indexedLabel").setPredictionCol("prediction").setMetricName("f1")
val f1 = evaluator.evaluate(result)

val evaluator = new MulticlassClassificationEvaluator().setLabelCol

("indexedLabel").setPredictionCol("prediction").setMetricName("weightedPrecision")
val weightedPrecision = evaluator.evaluate(result)

val evaluator = new MulticlassClassificationEvaluator().setLabelCol

("indexedLabel").setPredictionCol("prediction").setMetricName("weightedRecall")
val weightedRecall = evaluator.evaluate(result)

println("accu:"+accuracy+" f1:"+f1+" wPrecision:"+weightedPrecision+" 

wRecall:"+weightedRecall)

//DTC model 뽑아내기
val mdl = model.stages.toList.filter(_.isInstanceOf

[DecisionTreeClassificationModel]).head.asInstanceOf

[DecisionTreeClassificationModel]

//입력 feature 중요도 보기
mdl.featureImportances

//깊이, 노드수, 입력 feature 수
mdl.depth 
mdl.numNodes
mdl.numFeatures

//세팅 정보 보기
mdl.extractParamMap

//트리모양 보기
mdl.toDebugString
-------------------------------------------------------------------------------------------------------------------
import org.apache.spark.ml.feature._
import org.apache.spark.ml.clustering._
import org.apache.spark.ml.classification._
import org.apache.spark.ml.regression._
import org.apache.spark.sql.SQLContext
import org.apache.spark.ml.{Pipeline, PipelineModel}
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions.udf
import org.apache.spark.sql.DataFrame


//programmatically defining schema 로 불러옴 (StructType() 직접작성)
var df = new SQLContext(sc).read.format("com.databricks.spark.csv").schema(StructType(Array(StructField("sepal_length",DoubleType,true),StructField("sepal_width",DoubleType,true),StructField("petal_length",DoubleType,true),StructField("petal_width",DoubleType,true),StructField("species",StringType,true)))).option("header", "true").option("delimiter", ",").load("iris.txt")

// species는 명목형이기 때문에, 수치로 바꿔줌
val StringIndexer_species = new StringIndexer().setInputCol("species").setOutputCol("si_species").setHandleInvalid("skip")

// vector assembler로 vector화
val VectorAssember_ = new VectorAssembler().setInputCols(Array("sepal_length","sepal_width","petal_length","petal_width","si_species")).setOutputCol("va_")

// P-norm 정규화 (1-> 유클리드, 2->멘해튼)
val Normalizer_ = new Normalizer().setInputCol("va_").setOutputCol("normedFeature").setP(2)


//  param descriptions
//     initMode: The initialization algorithm. Supported options: 'random' and 'k-means||'. (default: k-means||)
//     initSteps: The number of steps for k-means|| initialization mode. Must be > 0. (default: 5)
//     k:The number of clusters to create (k). Must be > 1. Default: 2.
//     maxIter:Set the maximum number of iterations. Default is 20.
//     toler: Set the convergence tolerance of iterations. Default is 1.0E-4
//     seed: random seed (default: -1689246527)
//   default value
//     initMode="k-means||"
//         initMode={ k-means||, random }
//     initSteps=5
//     k=2
//     maxIter=20
//     toler=1.0E-4
// 

//k-means clustering 수행
val KMeans_ = new KMeans().setFeaturesCol("normedFeature").setPredictionCol("kmeansOutput").setInitMode("k-means||").setInitSteps(5).setK(3).setMaxIter(20).setSeed(1234).setTol(0.0001)

//pipeline 디자인 (명목형 수치화 -> 벡터화 -> 정규화 -> 클러스터링)
val pline = new Pipeline().setStages(Array(StringIndexer_species,VectorAssember_,Normalizer_,KMeans_))

//디자인한 파이프라인 실행 (트레이닝, 모델생성)
val MDL = pline.fit(df)

//데이터 적용 (일 안함, laziness)
val TEST_DF = MDL.transform(df)

// 모델 정보를 보기위해 모델 추출
val model = MDL.stages.toList.filter(_.isInstanceOf[KMeansModel]).head.asInstanceOf[KMeansModel]

//입력 된 param이 무엇이었는지
val params = model.extractParamMap.toString

//cluster 센터가 어디인지
val clusterCenters = model.clusterCenters.mkString(",")

//cluster로 
val clusterSizes = model.summary.clusterSizes.mkString(",")



//모두 출력
TEST_DF.show(TEST_DF.count.toInt)

//어떻게 벡터화, 정규화 되었나?
TEST_DF.limit(1).select("va_", "normedFeature").show(false)

//잘 클러스터링 되었나?
TEST_DF.select("species", "kmeansOutput").show(150)

//모든 과정들
TEST_DF.limit(1).show(false)



//모델 및 파이프라인 관리

//파이프라인 저장
pline.save("/Users/spark/k_means_iris_pipeline")

//덮어쓰기
pline.write.overwrite.save(("k_means_iris_pipeline"))

//불러오기
val pline = Pipeline.load("k_means_iris_pipeline")

//학습 된 모델 저장
MDL.save("k_means_iris_model")

//불러오기
val MDL = PipelineModel.load("k_means_iris_model")
------------------------------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------
//힌트 시험
cat start.sh 
spark-shell --master local[*]

rdd 생성
var logs = sc.textFile("파일명")

action(일을 한다) / trasformation(일을 안한다.)
productsPair.collect

mapredure / pair rdd
val productsPair = products.map(line => line.split(',')).map(fields => (fields(0), fields(1)))

spark application jar 명령어 
spark-submit --master yarn-client --class lecture.sk.com.WeblogCounter lecture05-1.0-jar-with-dependencies.jar



셔플 발생 연산, 발생 안한는 연산
reduceByKey, groupByKey, join

trasformation 이전 상태 반복- Lineage

메모리에다 persist할때 두개로 복재 할때 어떤거 
df.persist(StorageLevel.MEMORY_ONLY)
DISK_ONLY_2 / MEMORY_AND_DISK_2 / MEMORY_ONLY_2 / 

persiste 해제
result.unpersist()

spar streamping traspormatio 사용 가능 rdd에서
val ssc = new StreamingContext(sc,Seconds(5))

Lineage가 길어지는걸 방지하기 위해서 한 작업
val pwsB = sc.broadcast(pws)

데이터 성능관점 전송을 잘할려면 튜닝 기법(10장 초장)
val pwsB = sc.broadcast(pws)

spark sql이 뛰어난 수십역 건이 연산
Accumulator

11장에서 다룬것 rdd보다 편리하다.
DataFrame

spark mllib과 ml의 차이점 
MLlib: 
RDD 기반 기존 라이브러리
제고 알고리즘, 평가 Metric이 ML대비 많음
pipeline 지원하지 않음

ML:
DataFrame 기반 라이브러리
pipeline 지원


mllib 복잡

ml 파이프 라인을 사용
-----------------------------------------------------------------------------------------------------------------*/




