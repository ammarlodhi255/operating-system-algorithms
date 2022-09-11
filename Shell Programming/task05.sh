#! /bin/bash

#read -p "Enter filename: " original


LIST="abc.txt def.txt"
for file in $LIST
do
	if [ -f $file ]; then 
		$(mv $file "new-"$file)
		echo "File renamed"
	fi
done

echo $1" "$2
