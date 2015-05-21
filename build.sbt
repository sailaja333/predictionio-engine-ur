name := "template-scala-parallel-recommendation"

organization := "io.prediction"

val mahoutVersion = "0.10.1-SNAPSHOT"

libraryDependencies ++= Seq(
  "io.prediction"    %% "core" % pioVersion.value % "provided",
  "org.apache.spark" %% "spark-core" % "1.3.0" % "provided",
  "org.apache.spark" %% "spark-mllib" % "1.3.0" % "provided",
  "org.xerial.snappy" % "snappy-java" % "1.1.1.7",
  // Mahout's Spark code
  "org.apache.mahout" % "mahout-math-scala_2.10" % mahoutVersion
    from "file:///usr/local/mahout/math-scala/mahout-math-scala_2.10-0.10.1-SNAPSHOT.jar"
    excludeAll(ExclusionRule(organization = "org.apache.lucene")),
  "org.apache.mahout" % "mahout-spark_2.10" % mahoutVersion
    from "file:///usr/local/mahout/spark/mahout-spark-scala_2.10-0.10.1-SNAPSHOT.jar"
    excludeAll(ExclusionRule(organization = "org.apache.lucene")),
  "org.apache.mahout" % "mahout-math" % mahoutVersion
    from "file:///usr/local/mahout/math/mahout-math-0.10.1-SNAPSHOT.jar"
    excludeAll(ExclusionRule(organization = "org.apache.lucene")),
  "org.apache.mahout" % "mahout-hdfs" % mahoutVersion
    from "file:///usr/local/mahout/hdfs/mahout-hdfs-0.10.1-SNAPSHOT.jar"
    excludeAll(ExclusionRule(organization = "org.apache.lucene")))
  /*"org.apache.mahout" % "mahout-math-scala_2.10" % mahoutVersion,
  "org.apache.mahout" % "mahout-spark_2.10" % mahoutVersion,
  "org.apache.mahout" % "mahout-math" % mahoutVersion,
  "org.apache.mahout" % "mahout-hdfs" % mahoutVersion)*/

//resolvers += Resolver.mavenLocal