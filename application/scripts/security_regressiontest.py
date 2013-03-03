#!/usr/bin/env python2.7

import time
import urllib
import copy
from ConfigParser import SafeConfigParser
from pprint import pprint
from zapv2 import ZAPv2


target = 'http://127.0.0.1:<app port>'
zap_url = 'http://127.0.0.1:8080'
proxies = {'http': zap_url,
           'https': zap_url}

urllib.urlopen('http://zap/', proxies=proxies).getcode()
zap = ZAPv2(proxies={'http': 'http://127.0.0.1:8080', 'https': 'http://127.0.0.1:8080'})



print 'Accessing target %s' % target

zap.urlopen(target)


time.sleep(2)

print 'Spidering target %s' % target
zap.spider.scan(target)

time.sleep(2)

while int(zap.spider.status['status']) < 100:
    print 'Spider progress %: ' % zap.spider.status['status']
    time.sleep(5)

zap_alerts = copy.deepcopy(zap.core.alerts().get('alerts'))
print 'Spider completed'

print 'Start active scan'

zap.ascan.scan(target)
time.sleep(5)
while int(zap.ascan.status['status']) < 100:
    print 'Active scan process %s: ' % zap.ascan.status['status']
    time.sleep(5)
zap_alerts += copy.deepcopy(zap.core.alerts().get('alerts'))

pprint (zap_alerts)
