#!/usr/bin/env python2.7

import subprocess
import os
import signal

null = open("NUL","w")
p1 = subprocess.Popen(["play", "run&"], stdout=null,stderr=null)
print 'poll =', p1.poll() 
subprocess.call(["kill", "-9", "%d" % p1.pid])


