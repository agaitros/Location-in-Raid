# Location-in-Raid
Finding the Location in Raid from the result of an SQL query pulled out of Clipboard. 


To create shortcut key

1. Copy files in to common location, extract LocationInRaidFinder zip, create shortcut for LocationInRaidFinder.py. 
2. right click on shortcut and select properties
3. in Shortcut tab of properties window, create a shortcut key combination

To use the application, copy a single query result from the Images table in the CAMM archive and then either click on ShowMePath.py or hit the shortcut key combination. The full file system path should display and you can click "Copy to Clipboard" to copy the path. 



Future enhancements: 
--improvements to algorithm
--being able to perform the same for MMF documents
--being able to copy back multiple rows in the images table
--better error handling for when no query is copied
--Close application on "Copy to Clipboard" to prevent extra click
