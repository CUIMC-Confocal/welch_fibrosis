//This .Groovy script is for automated batch detection of fibrosis within tissue sections.//
// Modified by Theresa Swayne from LiverQuant script by Dominick Hellen //
// https://github.com/DominickHellen/LiverQuant/ and DOI: 10.21769/BioProtoc.477 //

//This line of code is placed before and after analyzing each slide//
//It is used to pause the current thread for 100 milliseconds//
//It also clears accumulated RAM for batch projects//
Thread.sleep(100)
javafx.application.Platform.runLater {
getCurrentViewer().getImageRegionStore().cache.clear()
System.gc()
}
Thread.sleep(100)

//Allows Qupath to make decisions for positive staining based on staining that is residual to DAB and hematoxylin//
setImageType('BRIGHTFIELD_OTHER');

// This should be deleted if you want to annotate by hand
//clearSelectedObjects();

// This should be deleted if you want to annotate by hand
//Increase or decrease the 'threshold' number below to improve accuracy of automated tissue annotation
//runPlugin('qupath.imagej.detect.tissue.SimpleTissueDetection2', '{"threshold":178,"requestedPixelSizeMicrons":200.0,"minAreaMicrons":10000.0,"maxHoleAreaMicrons":1000000.0,"darkBackground":false,"smoothImage":false,"medianCleanup":true,"dilateBoundaries":false,"smoothCoordinates":true,"excludeOnBoundary":false,"singleAnnotation":true}')

// detect the tissue
//createAnnotationsFromPixelClassifier("PSR-FG-tissue", 10000.0, 50.0, "DELETE_EXISTING", "INCLUDE_IGNORED")
createAnnotationsFromPixelClassifier("PSR-FG-Tissue-4x", 10000.0, 50.0, "DELETE_EXISTING")

//Selects annotations on each slide//
selectAnnotations();

//Quantifies positive pixel amount (Fibrotic area) for all annotations//
addPixelClassifierMeasurements("FibrosisQuant", "FibrosisQuant")

//Clears RAM and thread after each slide//
Thread.sleep(100)
javafx.application.Platform.runLater {
getCurrentViewer().getImageRegionStore().cache.clear()
System.gc()
}
Thread.sleep(100)
