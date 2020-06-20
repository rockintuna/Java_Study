cat *.md | grep '####' | sed 's/$/]/g' | awk '{print $0 "(springboot_1_"$2"md)  "}' | sed 's/#### /[/g'
