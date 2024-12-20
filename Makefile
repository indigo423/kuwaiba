.DEFAULT_GOAL := kuwaiba

SHELL               := /bin/bash -o nounset -o pipefail -o errexit

.PHONY help:
help:
	@echo "Makefile to build artifacts for Kuwaiba"
	@echo ""
	@echo ""
	@echo "Requirements:"
	@echo "  * OpenJDK 11 Development Kit"
	@echo "  * Maven"
	@echo ""

.PHONY deps:
deps:
	@command -v java
	@command -v javac
	@command -v mvn
	@echo "Check Java version 11"
	@java -version 2>&1 | grep -e "\"11\..*\""

.PHONY oci-deps:
oci-deps:
	@command -v docker

.PHONY kuwaiba:
kuwaiba: deps
	@mvn install
	@mvn --also-make --projects webclient -f server/ -Pproduction install

.PHONY sample-db:
sample-db:
	@unzip server/dbs/01_empty_kuwaiba.db.zip

.PHONY oci:
oci: oci-deps sample-db
	@docker build -t kuwaiba.local .

.PHONY clean:
clean: deps
	@mvn clean

