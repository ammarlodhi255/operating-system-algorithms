#! /bin/sh

read NUM1 NUM2

if [ $NUM1 -gt $NUM2 ]; then
       echo "$NUM1 is greater than $NUM2"
elif [ $NUM1 -lt $NUM2 ]; then
	echo "$NUM1 is less than $NUM2"
else
	echo "$NUM1 is equal to $NUM2"
fi	
