#!/usr/bin/env python

from os import listdir, curdir, replace
from shutil import move
from os.path import isdir
from functools import reduce
from fileinput import FileInput
# ============================================================================ #
#           This script removes superfluous imports from java files.           #
# ============================================================================ #

def _extends(s, f, basef):
    s.extend(get_files(basef + "/" + f))
    return s

def get_files(basedir):
    if not isdir(basedir):
        if basedir.endswith(".java"):
            return [basedir]
        return []
    subs = reduce(lambda s, f: _extends(s, f, basedir), listdir(basedir), [])
    return subs

def get_superfluous_imports(javafile):
    imports = set()
    with open(javafile, 'r') as f:
        for line in open(javafile, 'r'):
            if line.startswith("import"):
                imports.add(line.split(".")[-1][0:-2])
            else:
                imports = imports.difference(
                        filter(lambda imp: line.find(imp) != -1, imports)
                )
    return imports

def remove_imports(javafile, imports):
    for line in FileInput(javafile, inplace=1):
        if not any(line.find(imp) != -1 for imp in imports):
            print(line, end='')

def main():
    for f in get_files(curdir):
        remove_imports(f, get_superfluous_imports(f))

if __name__ == "__main__":
    main()

