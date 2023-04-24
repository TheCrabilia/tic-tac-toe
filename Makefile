FX_VERSION=20.0.1

.PHONY: all install_fx build run clean

all: build

install_fx:
ifeq (,$(wildcard lib/javafx-sdk-${FX_VERSION}))
	mkdir lib
	curl -LO https://download2.gluonhq.com/openjfx/${FX_VERSION}/openjfx-${FX_VERSION}_osx-aarch64_bin-sdk.zip
	unzip openjfx-${FX_VERSION}_osx-aarch64_bin-sdk.zip -d lib
	rm -f openjfx-${FX_VERSION}_osx-aarch64_bin-sdk.zip
endif

build: App.java

run: build
	java -cp build --module-path lib/javafx-sdk-${FX_VERSION}/lib --add-modules javafx.controls net.abracadabralab.App

App.java: install_fx
	javac -d build --module-path lib/javafx-sdk-${FX_VERSION}/lib --add-modules javafx.controls src/net/abracadabralab/App.java


clean:
	rm -rf build lib
