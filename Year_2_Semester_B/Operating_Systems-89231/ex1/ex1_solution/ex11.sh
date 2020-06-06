#!/bin/bash
#Zur Omri 206575854

#$1 is the path of the file
#$2 string for search

#save the line numbers
linecounter=1

while IFS= read -r line #foreach line in the file
do
	#foreach word in the line	
	for word in $line
	do
		if [[ $word == "$2" ]]; then
			echo $linecounter $line #print the line number and the line
		fi
	done
	linecounter=$[$linecounter+1] #add one to the line counter
done < "$1"

#cat $1 | grep  -w -n $2 - print lineNumber: line
