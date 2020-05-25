echo '#### 객체지향 프로그래밍' > readme.md
cat OOP* | grep '####' | sed 's/#### //g' | sed 's/$/](/g' | awk '{print $0"OOP_"$1"md)  "}' | sed 's/^/[/g' >> readme.md
