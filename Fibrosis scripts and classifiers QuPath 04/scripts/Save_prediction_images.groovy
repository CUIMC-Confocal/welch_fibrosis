// Write the full prediction image, downsampled 20x
//Code originally from https://forum.image.sc/t/qupath-script-with-pixel-classifier/45597/10
// modified by Theresa Swayne 2023
//This script, if Run for project, will create a subfolder in the Project directory, and place a PNG file of each image with the current overlay settings in place.

// STATUS: saves the annothation that is visible
// TODO: get the prediction on there

// setup
downsample = 2
opacity = 0.5


def imageData = getCurrentImageData()
//def classifier = loadPixelClassifier('Tissue regions')
def classifier = loadPixelClassifier('FibrosisQuant')
name = GeneralTools.getNameWithoutExtension(getCurrentImageData().getServer().getMetadata().getName())
mkdirs(buildFilePath(PROJECT_BASE_DIR, "snapshots"))


def predictionServer = PixelClassifierTools.createPixelClassificationServer(imageData, classifier)
def viewer = getCurrentViewer()
options = viewer.getOverlayOptions()
options.setOpacity(opacity)
options.setShowAnnotations(true)

def path = buildFilePath(PROJECT_BASE_DIR, "snapshots", name+'_prediction.png')

writeRenderedImage(viewer, path)

//writeImageRegion(predictionServer, RegionRequest.createInstance(predictionServer, downsample), path)

