#!/bin/bash

set -e

VENDOR=samsung
DEVICE=j3xnlte

cd ../../../..

BASE=`pwd`
PATCHES=$BASE/device/$VENDOR/$DEVICE/patches/sprd-diff

# remove all patches (only 2 subdirectories)
for FILE in $PATCHES/*.diff
do
	FILENAME=$(echo ${FILE}|rev|cut -d'/' -f1|rev)
	SNAME=$(echo ${FILENAME}|cut -d'.' -f1)
	RELPATH=${SNAME/_/\/}

	cd $BASE/$RELPATH
	echo -e "\e[36m* Select directory ${RELPATH}\e[39m"
	patch -R -p1 < $PATCHES/$FILENAME
	echo -e "\e[32mDirectory ${RELPATH} unpatched\e[39m\n"
done

echo -e "\e[32m-- All mods removed --\e[39m\n"

