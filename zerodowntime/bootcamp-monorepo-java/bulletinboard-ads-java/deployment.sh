# Made change to ads-header.js i.e <ui5-shellbar primary-title='Bulletin Board version 7' secondary-title='Advertisements'>
# This is reflected on the UI with version 7.
1) mvn package
2) docker build --platform linux/amd64 -t cc-ms-k8s-training.common.repositories.cloud.sap/bb-ads-i583822:v7 .
3)docker login -u "claude" -p "9qR5hbhm7Dzw6BNZcRFv" cc-ms-k8s-training.common.repositories.cloud.sap
4)docker push cc-ms-k8s-training.common.repositories.cloud.sap/bb-ads-i583822:v7
5)kubectl apply -f db.yaml
6)kubectl apply -f app.yaml
7)Now the ad service should be up and in its UI we can see the header as "Bulletin Board version 7 Advertisements"

#the 8th and 9th step are done in two other different terminals. This is done to monitor the changes.
8) kubectl get deployments --watch
9) kubectl get pods --watch

#Make again change to ads-header.js i.e <ui5-shellbar primary-title='Bulletin Board version 8' secondary-title='Advertisements'>
# This is reflected on the UI with version 8.
10) mvn package
11) docker build --platform linux/amd64 -t cc-ms-k8s-training.common.repositories.cloud.sap/bb-ads-i583822:v8 .
12)docker push cc-ms-k8s-training.common.repositories.cloud.sap/bb-ads-i583822:v8
13)kubectl apply -f app.yaml
14)Now the ad service should be up and in its UI we can see the header as "Bulletin Board version 8 Advertisements"