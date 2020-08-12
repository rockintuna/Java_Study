export FILENAMES=`ls *.md | grep -v readme.md`
export DIR=/Users/ijeong-in
for FILENAME in $FILENAMES
do
export NEWFILENAME=`echo "hexo_"${FILENAME}`

echo "---" > ${DIR}/target.out
echo "title: "`cat $FILENAME | grep '####' | sed 's/#### //g'` >> ${DIR}/target.out
echo "date: "`date '+%Y-%m-%d %H:%M:%S'` >> ${DIR}/target.out
echo "categories: " >> ${DIR}/target.out
echo " - Study" >> ${DIR}/target.out
echo " - Spring Framework" >> ${DIR}/target.out
echo "tags: spring" >> ${DIR}/target.out
echo "---" >> ${DIR}/target.out

cat $FILENAME >> ${DIR}/target.out

mv ${DIR}/target.out /Users/ijeong-in/Git_repo/hexoblog/source/_posts/${NEWFILENAME}
done
