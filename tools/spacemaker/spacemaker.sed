#!/usr/bin/env sed
1N
N
s/	/    /g
s/\\\\/\n/g
s/ *\n/\n/
s/ *$//
s/\([^\n]\)\(\n *\\begin{uc\)/\1\n\2/
s/\( *\\end{uc[^\n]*\n\)\([^\n]\)/\1\n\2/
s/\([^\n]\)\(\n *\\extends\)/\1\n\2/
s/\( *\\extends[^\n]*\n\)\([^\n]\)/\1\n\2/
P
D
