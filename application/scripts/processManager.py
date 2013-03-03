#!/usr/bin/env python2.7

import subprocess
import os
import signal
import time

os.chdir("../")
null = open("NUL","w")
p1 = subprocess.Popen(["play", "run&"], stdout=null,stderr=null)
time.sleep(10)
print 'poll =', p1.poll() 
subprocess.call(["kill", "-9", "%d" % p1.pid])


