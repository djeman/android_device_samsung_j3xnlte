#!/bin/bash

set -e

VENDOR=samsung
DEVICE=j3xnlte

cd ../../../..

BASE=`pwd`
PATCHES=$BASE/device/$VENDOR/$DEVICE/patches/sprd-diff

# bionic need a binary file
cp -f $PATCHES/libsprd_jemalloc.a $BASE/bionic/libc/libsprd_jemalloc.a
echo -e "\e[32m* File bionic/libc/libsprd_jemalloc.a added\e[39m"

# apply all patches (only 2 subdirectories)
for FILE in $PATCHES/*.diff
do
	FILENAME=$(echo ${FILE}|rev|cut -d'/' -f1|rev)
	SNAME=$(echo ${FILENAME}|cut -d'.' -f1)
	RELPATH=${SNAME/_/\/}

	cd $BASE/$RELPATH
	patch -p1 < $PATCHES/$FILENAME
	echo -e "\e[32m* Folder ${RELPATH} patched\e[39m"
done

echo -e "\e[32m* All mods added"

