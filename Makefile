load-test:
	k6 run --vus 200 --duration 10m -e HOST=$(platform) ./load-tests/test.js

deploy-pyrino:
	cd pyrino && $(MAKE) deploy
