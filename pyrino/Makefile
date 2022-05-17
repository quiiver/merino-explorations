.PHONY: deploy
deploy:
	gcloud builds submit --tag us-central1-docker.pkg.dev/wstuckey-sandbox/pyrino-repo/pyrino
	kubectl apply -f deployment.yaml

.PHONY: start-dev
start-dev: venv
	$(VENV)/uvicorn app.server:app --reload

include Makefile.venv
