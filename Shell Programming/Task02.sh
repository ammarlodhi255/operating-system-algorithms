#! /bin/sh

read -p "Enter filename: " $FILENAME

if [ -f $FILENAME ]; then
	echo "File exists"
else
	echo "File doesn't exist"
fi
