test:
	rm -f ./plugins/*.jar 
	rm -f ./target/original-*.jar
	cp ./target/*.jar ./plugins/

build:
	mvn package

init:
	mkdir -p ./plugins

clean: 
	rm -rf ./plugins/*