#!/usr/bin/env python2

from __future__ import division
import sys
import json
import requests
import urllib

# Fancy progress thingie
def update_progress(progress):
    sys.stderr.write('\r[{0}] {1}%'.format('#'*int(progress/10), progress))
    sys.stderr.flush()

# Taken from MS forum with some alterations
def translate(input, langcode_from, langcode_to):
    args = {
        'client_id': 'avank_translate',#your client id here
        'client_secret': '4HQCN4Q6v4S9mQziAv8ciUerDBg2mDRfOP7ljEkxBHQ',#your azure secret here
        'scope': 'http://api.microsofttranslator.com',
        'grant_type': 'client_credentials'
    }
    oauth_url = 'https://datamarket.accesscontrol.windows.net/v2/OAuth2-13'
    oauth_junk = json.loads(requests.post(oauth_url,data=urllib.urlencode(args)).content)
    translation_args = {
            'text': input,
            'to': langcode_to,
            'from': langcode_from
            }
    headers={'Authorization': 'Bearer '+oauth_junk['access_token']}
    translation_url = 'http://api.microsofttranslator.com/V2/Ajax.svc/Translate?'
    translation_result = requests.get(translation_url+urllib.urlencode(translation_args),headers=headers)
    translation = translation_result.content.replace("\"","")
    return translation[3:] # removes three weird bytes.
    
# Check CL arguments
if len(sys.argv) != 4:
    sys.stderr.write("Invalid number of arguments!\n Use: "+sys.argv[0]+" INPUT_FILE SOURCE_LANGUAGE_CODE TARGET_LANGUAGE_CODE\n")
    sys.exit(3)

# Open output file
outname = "messages."+sys.argv[3]
out = open(outname, "w")
sys.stderr.write("Writing to '"+outname+"'\n")

# Loop over the inputted file and translate that stuff to a new file!
with open(sys.argv[1], 'rU') as f:
    lines = f.readlines()
    total = len(lines)
    for i in range(total):
        line = lines[i]
        update_progress(i*100/total)
        
        if " = " in line:
            parts = line.rstrip('\n').split(" = ")
            text = ' = '.join(parts[1::]) # readd equal symbols if they were part of the translate message
            #trans = text
            trans = translate(text, sys.argv[2], sys.argv[3])
            out.write(parts[0]+" = "+trans+"\n")
        else:
            out.write(line),
    f.close()
    out.close()
    sys.stderr.write("\nDone!\n")
