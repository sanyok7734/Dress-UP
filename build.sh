#!/bin/bash

USAGE=$'Run this script from the root directory of the poject\nFirst argument: resources directory\nSecond argument: package name\nThird argument: version code\nFourth argument: version name\n'


args=("$@")

if [ $# -ne 4 ] ; then
	echo "$USAGE"
	exit 1
fi

RES_DIR=${args[0]}
ANDROID_PACKAGE_NAME=${args[1]}
ANDROID_VERSION_CODE=${args[2]}
ANDROID_VERSION_NAME=${args[3]}

DEFAULT_FLAVOR='quickAppNinja'
BUILD_TASK='assemble'${DEFAULT_FLAVOR^}'Release'
BUILD_APK_NAME='app-'${DEFAULT_FLAVOR}'-release.apk'

if [ ! -d $RES_DIR ]; then
	echo "Directory [' $RES_DIR ' ] not found."
	exit 1
fi

echo 'Res directory : ' $RES_DIR
echo 'Package name : ' $ANDROID_PACKAGE_NAME

RESULT_APK='dressup.apk'

sudo rm $RESULT_APK

sudo chmod +x gradlew

echo "Gralde cleaning project"
sudo ./gradlew clean

echo "Gralde build started"
sudo ./gradlew $BUILD_TASK -P RES_DIR_PATH=$RES_DIR -P PACKAGE_NAME=$ANDROID_PACKAGE_NAME -P VERSION_NAME=$ANDROID_VERSION_NAME -P VERSION_CODE=$ANDROID_VERSION_CODE
echo "Gralde build finished"

sudo cp -R app/build/outputs/apk/$BUILD_APK_NAME ./

sudo mv $BUILD_APK_NAME ./$RESULT_APK

echo 'Generated file: ' $RESULT_APK
echo 'Done!'