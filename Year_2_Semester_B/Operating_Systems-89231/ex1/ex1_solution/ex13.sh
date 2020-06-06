#!/bin/bash
#Zur Omri 206575854

#$1 is a path
#$2 is a filename

cd "$1" # move to the wanted directory

#foreach file with the name that given, max depth of search is 2. and sort it lexogical
for filename in $(find . -maxdepth 2 -name $2 | sort); do
	if ! [ -d $filename ]; then
		cat $filename #print each folder name
	fi
done
