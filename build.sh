#!/bin/bash

USAGE=$'Run this script from the root directory of the poject\nFirst argument: game title\nSecong argument: resources directory\nThird argument: package name\nFourth argument: version code\nFifth argument: version name\nSixth argument: White label (values: "on/off")\n'


args=("$@")

if [ $# -ne 6 ] ; then
	echo "$USAGE"
	exit 1
fi

RES_DIR=${args[1]}
ANDROID_GAME_TITLE=${args[0]}
ANDROID_PACKAGE_NAME=${args[2]}
ANDROID_VERSION_CODE=${args[3]}
ANDROID_VERSION_NAME=${args[4]}
APPLICATION_WHITE_LABEL=${args[5]}

DEFAULT_FLAVOR='quickAppNinja'
BUILD_TASK='assemble'${DEFAULT_FLAVOR^}'Release'
BUILD_APK_NAME='app-'${DEFAULT_FLAVOR}'-release.apk'

if [ ! -d $RES_DIR ]; then
	echo "Directory [' $RES_DIR ' ] not found."
	exit 1
fi

echo 'Res directory : ' $RES_DIR
echo 'Package name : ' $ANDROID_PACKAGE_NAME
echo 'GAME_TITLE : ' $ANDROID_GAME_TITLE

sudo chmod +x gradlew

echo "Gralde cleaning project"
sudo ./gradlew clean

echo "Gralde build started"
sudo ./gradlew $BUILD_TASK -P RES_DIR_PATH=$RES_DIR -P GAME_TITLE=$ANDROID_GAME_TITLE -P PACKAGE_NAME=$ANDROID_PACKAGE_NAME -P VERSION_NAME=$ANDROID_VERSION_NAME -P VERSION_CODE=$ANDROID_VERSION_CODE -P WHITE_LABEL=$APPLICATION_WHITE_LABEL
echo "Gralde build finished"

sudo cp -R app/build/outputs/apk/$BUILD_APK_NAME ~/Documents/

echo 'Generated file: ' $BUILD_APK_NAME
echo 'Done!'