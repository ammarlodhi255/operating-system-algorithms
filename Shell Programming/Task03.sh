#! /bin/sh

read -p "Write a path to a file: " $PATHNAME
if [ -e $PATHNAME ]; then
	echo "file exists"
else 
	echo "file does not exist"
fi
