#! /bin/sh

num1=1
num2=2

echo $num1 $num2

read -p "Enter your name: " name
echo "Welcome:" $name

## IF STATEMENT

if [ $num1 -ge $num2 ] 
then
	echo $num1 "is greater than" $num2
elif [ $num2 -ge $num1 ]
then
	echo $num2 "is greater than" $num1
else 
	echo "else block executed"
fi

## CASE STATEMENT

read -p "Are you familiar with propositional logic?: " answer

case $answer in
	"yes" | "y" | "YES")
		echo "Youre allowed to take database systems course";;
	"no" | "NO" | "n")
		echo "Youre only allowed to take calculus 1";;
	*)
		echo "Please answer yes or no";;
esac

## FOR LOOP

list="Ammar aziz adnan mohammad ali"

for var1 in $list
do
	echo $var1
done

## WHILE LOOP

num=0

while read -r current_line
do
	echo "$num: $current_line"
	((num++))
done < ./appended




# task 1

read -p "Enter filename: " filename
if [ -f $filename ]
then 
	echo "$filename file exists"
fi

# task 2

read -p "Enter a file: " nameoffile

if [ -e $nameoffile ]
then
	echo "$nameoffile exists"
fi
