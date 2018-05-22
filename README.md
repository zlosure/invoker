# Invoker

A user-friendly integrated development platform on GCP.

[![Build Status](https://travis-ci.org/zlosure/invoker.svg?branch=master)](https://travis-ci.org/zlosure/invoker)

### Before You Start
0. A Mac machine.
1. A Google Cloud Platform(GCP) project that's enabled [Cloud Build API](https://console.cloud.google.com/flows/enableapi?apiid=cloudbuild.googleapis.com).
2. A Google Kubernetes Engine(GKE) cluster that's enabled `Allow full access to all Cloud APIs` for node pools.

### Getting Started
1. Clone Invoker to your local laptop.
``` shell
mkdir -p ~/zlosure/local-files
git clone https://github.com/zlosure/invoker.git ~/zlosure/local-files/invoker
cd ~/zlosure/local-files/invoker
mkdir ./credentials
```

2. Create GKE service account as owner role and download the keyfile to `./credentials`

3. Follow the instructions by running

``` shell
bin/invoker -a init
```

4. You should get a URL for console at the end, from where you can interaction your cluster.

P.S. If your cluster admin already set up the cluster for you. You can directly run

``` shell
bin/invoker -a bootstrap -n <your-dev-ns> -t local

```

# Development Logs
1. [Jul. 13 2018] File sync plan is decided.
2. [Jul. 19 2018] Use `Docker for Mac` to offload the need to interact with xhyve directly.
3. [Aug. 1 2018] Able to pick up local changes and build docker image in real time from the cloud.
4. [Aug. 14 2018] Set up a Pedestal web service and able to run `kubectl` inside of it. Also, decide repo name `invoker`.
5. [Aug. 28 2018] Split personal-nfs-server and deployment per namespace and able to run basic k8s, helm, docker operations based on local codebase.
6. [Sep. 11 2018] De-couple deployment artifacts from projects by splitting Helm chart source code (as a GitHub repo) and chart repository (as a GCS bucket).
7. [Sep. 19 2018] Start up a new project and cluster within 10 mins and no big blocker!
8. [Sep. 21 2018] Replace scripts with Ansible.
9. [Oct. 9 2018] Package helm charts recursively with dependencies.
10. [Oct. 11 2018] Build docker image along side chart packaging.
11. [Oct. 16 2018] Automate project with dependencies helm install process(package charts, image build, and helm deploy).
12. [Oct. 18 2018] User onboarding.
13. [Oct. 31 2018] Invoker self-deploys to cluster as webservice, and installs Kafka/Zookeeper stack by a HTTP request.
14. [Nov. 12 2018] Introduce the beloved Cloud Build and move build process out of Invoker container.
15. [Nov. 26 2018] Install/Upgrade/Delete actions through CLI, and efficiency looks promising (Invoker upgrade takes 1.5 min).
16. [Nov. 29 2018] Install/Upgrade/Delete actions through HTTP request.
```
POST :invoker/invoke
Content-Type: application/json
{
  "action": "install",
  "target": "kafka",
  "namespace": "szhou"
}
```
17. [Dec. 5 2018] Split bootstrapping process for cluster and local environment.
18. [Dec. 14 2018] Chart role refactoring.
19. [Dec. 19 2018] Inspect role pulled out.
20. [Dec. 28 2018] Connect console (a reagent clojurescript project) with invoker.
21. [Jan. 10 2018] Automate configuration steps, move invoker/console image to docker hub, and start invoker/console during cluster bootstrap process.
22. [Jan. 18 2018] Create different chart packages based on charts branch name for the same chart version.
23. [Feb. 6 2018] Start to clean up and prepare a demo.
24. [Feb. 8 2018] New UI.

![console](https://github.com/zlosure/console/blob/master/resources/console-overview.png)
