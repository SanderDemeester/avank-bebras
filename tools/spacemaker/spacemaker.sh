#!/usr/bin/env bash

temp=/tmp/spacemaker.temp

for file in $(find . -name '*.tex'); do

    if grep '\\begin{uc}' "$file"; then

        mv "$file" "$temp"
        sed -f spacemaker.sed < "$temp" > "$file"
    fi

done
