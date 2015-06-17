javac *.java
echo Main-Class: Mat261Proj >manifest.txt
jar cvfm Mat261Proj.jar manifest.txt *.class
