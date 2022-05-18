load-test:
	k6 run --vus 200 --duration 10m -e HOST=$(platform) -e PROVIDERS=$(providers) ./load-tests/test.js

deploy:
	cd $(platform) && $(MAKE) deploy
