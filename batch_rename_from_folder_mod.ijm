# @File(label="Input directory", style="directory") src_dir
# @File(label="Output directory",style="directory") out_dir
# @String(label="Process files containing regex",description="Clear for no filtering",value=".*Merged.*") fileFilter

// ImageJ Macro to rename files according to the parent of their parent directory
// Also sets image scale
// Based on a post in the image.sc forum
// Theresa Swayne, 2023

// TODO: Allow user input of pixel size
// TODO: Adapt for different stitched folder names

// Get a list of all subdirectories (assumed to be the experiment folders)
exptList = getFileList(src_dir);

// Loop through each subdirectory
for (i = 0; i < exptList.length; i++) {
    currentFolder = exptList[i];
	print("Checking " + currentFolder);
	
    // Check if it's a directory
    if(File.isDirectory(src_dir + File.separator + currentFolder)) {

		exptFolder = src_dir + File.separator + currentFolder;
		
		// Loop through the plate directories (normally just one but can be more)
		plateList = getFileList(exptFolder);
		print("The directory " + exptFolder + " contains " + plateList.length + " items");
		
		for (plate = 0; plate < plateList.length; plate++) {
			
			currentPlate = plateList[plate];
			print("Checking item " + plate + ", " + currentPlate);
			
			if(File.isDirectory(exptFolder + File.separator + currentPlate)) {
			
				plateFolder = exptFolder + File.separator + currentPlate;
				
			    // Set the image directory
			    // For some reason Gen5 names the stitched folders differently sometimes. 
	        	imageDirectory = plateFolder +  File.separator + "#23C1942877_Stitched" + File.separator;
	        	// imageDirectory = plateFolder +  File.separator + "#2251844443_Stitched" + File.separator;
				print("Checking " + imageDirectory);
				
		        // Get the merged files in the stack1 folder
		        fileList = getFileList(imageDirectory);

		        // Check if the file exists before processing
		        if (fileList.length > 0) {
		        	for (j = 0; j < fileList.length; j++) {
		        
		            	currentFile = fileList[j];
		            	print("Checking " + currentFile);
						if (matches(currentFile, fileFilter)) {
							
							print(currentFile + " contains the search expression " + fileFilter);
				            // Open the merged file
				            //run("Bio-Formats Importer", "open=[" + imageDirectory + currentFile + "] color_mode=Default view=Hyperstack stack_order=XYCZT series_1");
							open(imageDirectory + currentFile);
							
							// print 
							// Set the scale properly
							run("Set Scale...","distance=1 known=1.612 unit=micron"); 
							
							// construct a file name
							exptName = substring(currentFolder, 0, lengthOf(currentFolder) - 1);
							fName = exptName + "_" + currentFile;      // location of currently imported file
							saveAs("Tiff", out_dir + File.separator + fName);   // .tif unneeded - done for you
							close();
						} // check for files matching filter
						else print(currentFile + " does not contain the search expression " + fileFilter);
		        	} //  j file processing loop
        		} // check for files in plate folder
			} // check for plate folder
    	} // loop through plate folders
	} // check for experiment folder
} // loop through experiment folders