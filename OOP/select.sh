export list=`ls OOP_*.md`

echo "================================================"
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
