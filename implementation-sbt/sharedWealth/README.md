JSON-TO-CSV Converter
==============================
This project is built in reference to the format of realtime.cache(located under resource folder) <br/>

Note:The output files are tab(\t) delimeted

Info
====================================
When you build the jar the test case uses smaller file(test2.sample) which is a smaller version of realtime.cache and the outputfile generated is outTest.txt <br/>
When you run the jar, the main method calls JsonToCsvTest.scala(located under src/main/scala) which uses the file realtime.cache and the output file generated is outMain.txt <br/>


Skip TestCases
====================================
To skip testcases during assembly incude the following in sbt.build <br />
test in assembly := {} 

Steps
====================================
1.Build the jar<br />
sbt assembly 

2.Run the jar<br/>
java -jar target/scala-2.11/json-to-csv-assembly-0.1.1-SNAPSHOT.jar







