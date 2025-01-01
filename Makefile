.PHONY: clean all
all:
	mvn -U clean install
clean:
	mvn -U clean