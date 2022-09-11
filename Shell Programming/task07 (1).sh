#!/bin/bash

LIST= ls
for dir in $LIST
do 
	if [[ -d $dir ]]
	then 
		for subdir in $dir
		do
			if [ -f subdir ]
			then 
				echo $subdir
			fi
		done
	fi
done
