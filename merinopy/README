# Setup

Start by authorizing and setting your gcp project to `wstuckey-sandbox`:

```
gcloud auth login <ldap>@mozilla.com --update-adc
gcloud config set project wstuckey-sandbox
```

# Build
This deployment uses cloud build and gcr to manage the image so that it can be distinct from the main merinopy image.
In the merino-py repository run the following command to deploy a new image to GCR.

```
gcloud builds submit --tag us-central1-docker.pkg.dev/wstuckey-sandbox/pyrino-repo/merinopy
```

# Deploy
```
helm upgrade merinopy k8s/merinopy
```
