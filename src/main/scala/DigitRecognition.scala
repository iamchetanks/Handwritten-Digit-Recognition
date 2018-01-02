import org.apache.spark.ml.classification.DecisionTreeClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.sql.SparkSession

object DigitRecognition {
  def main(args: Array[String]): Unit = {

    try {

        val spark: SparkSession = SparkSession.builder.getOrCreate
        // spark config to run locally
//      val spark = SparkSession.builder().appName("Handwritten Digit Recognition")
//        .config("spark.master", "local")
//        .getOrCreate();
      import spark.implicits._

        // to run locally
      //val training = spark.read.format("libsvm").load("E:/Google_drive_personal/chetan/winter_break_2017/handwritten-recognition/data/libsvm/mnist")
      //val test = spark.read.format("libsvm").load("E:/Google_drive_personal/chetan/winter_break_2017/handwritten-recognition/data/libsvm/mnist.t")

        // to run on Google Cloud dataproc
      val training = spark.read.format("libsvm").load(s"gs://mnist-handwritten-digit-recognition/mnist")
      val test = spark.read.format("libsvm").load(s"gs://mnist-handwritten-digit-recognition/mnist.t")
      val dtc = new DecisionTreeClassifier()
      val variedMaxDepthModels = (8 until 13).map { maxDepth =>
        dtc.setMaxBins(32)
        dtc.setMaxDepth(maxDepth)
        dtc.fit(training)
      }
      val evaluator = new MulticlassClassificationEvaluator().setMetricName("accuracy")
      val accuracies = (8 until 13).map { maxDepth =>
        val model = variedMaxDepthModels(maxDepth - 8)
        val predictions = model.transform(test)
        (maxDepth, evaluator.evaluate(predictions))
      }.toDF("maxDepth", "accuracy")
      accuracies.show()
    } catch {
      case t: Throwable => {
        t.printStackTrace()
      }
    }
  }
}
