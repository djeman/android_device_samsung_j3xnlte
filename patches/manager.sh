#!/bin/bash

set -e

VENDOR=samsung
DEVICE=j3xnlte

usage() {
	echo "$0 usage:" && grep "[[:space:]].)\\ #" "$0" | sed 's/#//' | sed -r 's/([a-z])\)/-\1/'
	exit 0
}
[ $# -eq 0 ] && usage
while getopts "har" arg; do
	case $arg in
	a) # Apply patches.
		p=1
		;;
	r) # Remove patches.
		r=1
		;;
	h | *) # Display help.
		usage
		;;
	esac
done

cd ../../../..

BASE=$(pwd)
PATCHES=$BASE/device/$VENDOR/$DEVICE/patches/sprd-diff

# apply / remove all patches (only 2 subdirectories)
for FILE in $PATCHES/*.diff; do
	FILENAME=$(echo "${FILE}" | rev | cut -d'/' -f1 | rev)
	SNAME=$(echo "${FILENAME}" | cut -d'.' -f1)
	RELPATH=${SNAME/_/\/}

	cd "$BASE/$RELPATH"
	echo -e "\\e[36m* Select directory ${RELPATH}\\e[39m"
	if [ $p ]; then
		patch -p1 <"$PATCHES/$FILENAME"
		echo -e "\\e[32mDirectory ${RELPATH} patched\\e[39m\\n"
	elif [ $r ]; then
		patch -R -p1 <"$PATCHES/$FILENAME"
		echo -e "\\e[32mDirectory ${RELPATH} unpatched\\e[39m\\n"
	fi
done

if [ $p ]; then
	echo -e "\\e[32m-- All patches applied --\\e[39m\\n"
elif [ $r ]; then
	echo -e "\\e[32m-- All patches removed --\\e[39m\\n"
fi
