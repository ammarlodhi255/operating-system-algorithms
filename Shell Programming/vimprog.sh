#! /bin/bash

num1=1
num2=2

echo num1 is: $num1 and num2 is: $num2

if [ $num1 -gt $num2 ]
then
	echo $num1 greater is than $num2
elif [ $num2 -gt $num1 ]
then
	echo $num2 is greater than $num1
fi

#-r

filename="ammar"
if [ -f $filename ]
then
	echo file exists
fi

read -p "Enter your name: " name

case name in 
"Ammar")
	echo welcome ammar;;
"Prem")
	echo welcome prem sagar;;
*)
	echo welcome $name
esac

list="Ammar AHmed PRem sagar"

for name in $list
do 
	echo $name
done

for i in {1..10}
do
	echo $i
done

line=1

while read -r CURRENT_LINE
do
	echo $line: $CURRENT_LINE
	((line++))
done < "./appended"	


#TASK 2

fname="file.txt"

if [ -f $fname ]
then
	echo file of name $fname exists
fi

# TASK 3

if [ -e $fname ]
then
	echo file $fname exists
fi


#TASK 4

#names="$1 $2 $3"

#for name in $names
#do
#	echo welcome $name
#done

#TASK 5

fnames="$1 $2 $3"

for fname in $fnames
do
	mv $fname $fname"-new"
done

#TASK 5 SIMPLE

filenames="f1.txt-new f2.txt-new f3.txt-new"
iterator=1

for fname in $filenames
do
	mv $fname "new "$fname"$iterator"
	((iterator++))
done

#TASK 6


read -p "Enter the filename: " ffname
read -p "When was the file created for today write Today otherwise write Before: " stat

case $stat in
"Today")
	echo case A
	mv $ffname "new "$ffname;;
"Before")
	echo case B
	mv $ffname "old "$ffname;;
esac


#TASK 7

for dir in /home/ammar/myfolder/ammars/*
do
	if [ -d "$dir" ]
	then 
		ls $dir 
	fi
done
