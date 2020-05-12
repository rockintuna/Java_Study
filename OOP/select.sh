export list=`ls OOP_*.md`

##export number=`echo $list | sed 's/OOP_//g' | sed 's/.md//g'`
##export title=`


echo "================================================"
##printf %-10s"\n" $number
cat $list | grep '####' | sed 's/####/== /g'
echo "================================================"
echo "번호를 입력하세요. "

read number 

if [ $number -lt 10 ];
then
cat OOP_0$number.md | more
else
cat OOP_$number.md | more
fi
