
compile-test: 
	javac Test.java 

run-test: compile-test 
	java Test

clean: 
	find . -type f -iname "*.class" | xargs rm 

default: run-test
run: default