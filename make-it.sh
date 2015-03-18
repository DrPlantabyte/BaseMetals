#!/bin/sh
CURRENT_DIR="`dirname "$0"`"
cd "${CURRENT_DIR}"

mkdir -p build/dev
mkdir -p build/dist
rm -v build/libs/*.jar build/dev/* build/dist/*
./gradlew jar
cp -vf build/libs/*.jar build/dev/
./gradlew build javadoc
cd build/docs/javadoc/
zip -r --quiet ../../dev/javadoc *
cd "../../../src/main/java/"
zip -r --quiet ../../../build/dev/src *
cd "../../.."
cd "build/dev"
ORIG_NAME="`ls basemetals-*.jar`"
API_INSERT="-API-"
zip -d "${ORIG_NAME}" assets/\*
mv -v "${ORIG_NAME}" "${ORIG_NAME/-/$API_INSERT}"
mv -v src.zip basemetals-src.zip
mv -v javadoc.zip basemetals-javadoc.zip
cd "../.."
cp -vf build/libs/*.jar build/dev/* build/dist
echo "Build script competed on `date`"

