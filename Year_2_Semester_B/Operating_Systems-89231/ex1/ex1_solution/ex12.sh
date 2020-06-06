#!/bin/bash
#Zur Omri 206575854

#path is $1

cd $1 #move to that folder

for filename in $(ls -v *.txt); do
	echo $filename is a file #print each file text
done

for foldername in $(ls -v); do
	#check if its a folder
	if [[ -d $foldername ]]; then
		echo ${foldername} is a directory #print each folder name
	fi
done
