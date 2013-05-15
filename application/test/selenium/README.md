General
-------
These testcases can be run from the Firefox Selenium IDE.
To run test, open a folder and open the testsuite in the Selenium IDE, the correct
testcases will be loaded with the testsuite.

Requirements
------------
The app must run on 127.0.0.1:9000

Requirements for each testsuite
-------------------------------
database                logged in as an ADMINISTRATOR user
login			not logged in, user exists with id = test13 and password = test1, Run speed != fast
forgotpwd		not logged in, user exists with id = test13, no email address and no classes, language = Dutch
user-settings		logged in as user with id = test13, who has name = test and language = Dutch
