#!/bin/sh
CURRENT_DIR="`dirname "$0"`"
cd "${CURRENT_DIR}"

rm -v build/libs/*.jar build/dev/*
./gradlew jar
mkdir -p build/dev
cp -vf build/libs/*.jar build/dev/
./gradlew build javadoc
cd build/docs/javadoc/
zip -r --quiet ../../dev/javadoc *
cd "../../../src/main/java/"
zip -r --quiet ../../../build/dev/src *
cd "../../.."
echo "Build script competed on `date`"

