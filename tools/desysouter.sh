#!/usr/bin/env bash

temp=/tmp/desysouter.sh

for file in $(find . -name '*.java' -or -name '*.tex' -or -name '*.html'); do

	cat "$file" > "$temp"
	sed -e '/System.out.println/d' "$temp" > "$file"

done


