#! /bin/sh

function crawlThroughDirectories() {
	for dir in /home/ammar/myfolder/*
       	do
		if [ -d "$dir" ]
	       	then
			ls $dir/*.txt
		fi
	done
}

crawlThroughDirectories
