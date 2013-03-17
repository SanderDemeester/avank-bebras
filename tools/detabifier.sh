#!/usr/bin/env bash

temp=/tmp/detabifier.temp

for file in $(find . -name '*.java' -or -name '*.tex' -or -name '*.html'); do

	cat "$file" > "$temp"
	sed -e 's/	/    /g' -e 's/  *$//' "$temp" > "$file"

done

