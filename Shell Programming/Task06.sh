#! /bin/sh

LIST= ls *.txt
LIST2= ls *.c

for file in $LIST; do
	if [ -f $file ]; then
		$(mv $file "new-"$file)
	fi
done

for file2 in $LIST2; do
        if [ -f $file2 ]; then
                mv $file2 "new-"$file2
        fi
done   


