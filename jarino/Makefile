.PHONY: deploy
deploy: 
	gcloud builds submit --tag us-central1-docker.pkg.dev/wstuckey-sandbox/jarino-repo/jarino 
	kubectl rollout restart -f k8s/deployment.yaml

