Khetthai Laksanakorn
kl2679
Cloud Computing HW1
2015-10-25

Deployment:
Fill in appropriate credentials at the top of UpdateStreams.java, TweetMapServer.java, TweetFetcher.java
Fill in google maps credentials in index.html at the bottom.
Fill in URL of ELB environment in index.html's host field, as well as the project name.
Bundle as .war and upload to Amazon ELB.
Setup ELB to allow websocket connections.

Notes: 
I haven't gotten the ELB configuration to allow ELB through, but the code currently works on local Tomcat test environment.
Though code for live updates from the db is included, currently uses a periodic refresh.