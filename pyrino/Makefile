deploy-gke:
	gcloud builds submit --tag us-central1-docker.pkg.dev/wstuckey-sandbox/pyrino-repo/pyrino
	kubectl apply -f deployment.yaml
