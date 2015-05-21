package com.finderbots

import io.prediction.controller.PPreparator
import org.apache.mahout.math.indexeddataset.{IndexedDataset, BiDictionary}
import org.apache.mahout.sparkbindings.indexeddataset.IndexedDatasetSpark

//import org.apache.mahout.sparkbindings.indexeddataset.IndexedDatasetSpark
import org.apache.mahout.sparkbindings

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD

class Preparator
  extends PPreparator[TrainingData, PreparedData] {

  def prepare(sc: SparkContext, trainingData: TrainingData): PreparedData = {
    // now that we have all actions in separate RDDs we must merge any user dictionaries and
    // make sure the same user ids map to the correct events
    var d: Option[BiDictionary] = None
    val indexedDatasets = trainingData.actions.map{ t =>
      val ids = IndexedDatasetSpark(t._2, d)(sc) // passing in previous row dictionary will use the values if they exist
      // and append any new ids, so after all are constructed we have all user ids in the last dictionary
      d = Some(ids.rowIDs)
      (t._1, ids)
    }
    val rowAdjustedIds = indexedDatasets.map { t =>
      val numUsers = d.get.size
      (t._1, t._2.create(t._2.matrix, d.get, t._2.columnIDs).newRowCardinality(numUsers))
    }
    new PreparedData(rowAdjustedIds)
  }

}

class PreparedData(
  val actions: List[(String, IndexedDataset)]
) extends Serializable