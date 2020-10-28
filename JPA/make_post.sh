export FILENAME=$1
export DIR=/Users/ijeong-in
export NEWFILENAME=`echo "hexo_"${FILENAME}`

echo "---" > ${DIR}/target.out
echo "title: "`cat $FILENAME | grep ^'## ' | sed 's/## //g'` >> ${DIR}/target.out
echo "date: "`date '+%Y-%m-%d %H:%M:%S'` >> ${DIR}/target.out
echo "categories: " >> ${DIR}/target.out
echo " - Study" >> ${DIR}/target.out
echo " - JPA" >> ${DIR}/target.out
echo "tags: " >> ${DIR}/target.out
echo " - JPA" >> ${DIR}/target.out
echo " - ORM" >> ${DIR}/target.out
echo " - Mapping" >> ${DIR}/target.out
echo " - Spring Data JPA" >> ${DIR}/target.out
echo "---" >> ${DIR}/target.out
echo "{% img img_class /img/jpa.png 340 160 "'jpa'" %}" >> ${DIR}/target.out

cat $FILENAME >> ${DIR}/target.out

mv ${DIR}/target.out /Users/ijeong-in/Git_repo/hexoblog/source/_posts/${NEWFILENAME}
