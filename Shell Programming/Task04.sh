#! /bin/sh

read -a NAMES

for NAME in ${NAMES[@]}; do
	echo "Welcome, greetings to" $NAME
done	
