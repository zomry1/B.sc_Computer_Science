#!/bin/bash
#Zur Omri 206575854

#$1 is the clien name
#$2 is the path of the file

money=0
while IFS='' read -r line #foreach line in the file
do
	if [[ $line == "$1 "[0-9]* ]] || [[ $line == "$1 -"* ]]; then #if the line start with the name
		echo $line
		without=${line//"$1"} #remove the name from the string
		transraction="$(cut -d' ' -f2 <<<"$without")" # get the first "word" -  the money
		money=$[$money+$transraction] #sum the balance
	fi
done < "$2"

echo Total balance: $money

