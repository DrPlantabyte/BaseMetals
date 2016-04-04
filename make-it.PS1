# Windows Power-Shell Build Script
$PROJECT_FOLDER="$PSScriptRoot"
Set-Location "$PROJECT_FOLDER"
Add-Type -A System.IO.Compression.FileSystem
# make dev folders
mkdir -p "$PROJECT_FOLDER/build/dev"
mkdir -p "$PROJECT_FOLDER/build/dist"

# remove stale files and build API jar
rm -v -Recurse build/libs/*.jar
rm -v -Recurse build/dev/* 
rm -v -Recurse build/dist/*
echo "Compiling API"
Start-Process -wait gradlew.bat jar
# remove stale files and build mod jar and javadoc
cp -v build/libs/*.jar build/dev/
echo "Compiling Mod"
Start-Process -wait gradlew.bat build
rm -v build/libs/*-sources.jar
echo "Compiling JavaDocs"
Start-Process -wait gradlew.bat javadoc

# zip the sourcecode and javadoc
[IO.Compression.ZipFile]::CreateFromDirectory("$PROJECT_FOLDER/build/docs/javadoc", "$PROJECT_FOLDER/build/dev/BaseMetals-javadoc.zip")
[IO.Compression.ZipFile]::CreateFromDirectory("$PROJECT_FOLDER/src/main/java", "$PROJECT_FOLDER/build/dev/BaseMetals-src.zip")

# remove assets from API jar
$array=Get-ChildItem "$PROJECT_FOLDER/build/dev" -Filter "BaseMetals*.jar"
$myFile=$array[0]
mkdir -p "$PROJECT_FOLDER/build/dev/extract"
[IO.Compression.ZipFile]::ExtractToDirectory("$PROJECT_FOLDER/build/dev/$myfile","$PROJECT_FOLDER/build/dev/extract")
rm -Recurse $PROJECT_FOLDER/build/dev/extract/assets/*
rmdir "$PROJECT_FOLDER/build/dev/extract/assets"
rm "$PROJECT_FOLDER/build/dev/$myfile"
$myAPIfile=$myFile -replace "-","-API-"
& $env:JAVA_HOME\bin\jar.exe c0f "$PROJECT_FOLDER/build/dev/$myAPIfile"  -C "$PROJECT_FOLDER/build/dev/extract" .
rm -Recurse $PROJECT_FOLDER/build/dev/extract

# copy final files to dist folder
cp -v build/libs/*.jar build/dist
cp -v build/dev/*.jar build/dist
cp -v build/dev/*.zip build/dist


#done
$time=date
echo "Build script competed on $time"