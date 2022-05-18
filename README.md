# Merino Explorations

This repo contains a couple prototypes of merino in different languages. The code in here is for experimentation and prototyping purpose and is not to be considered production ready.

## Load tests
There is a k6 load test script located in the `load-tests` directory.

### Running
Run a 10 minute load test against the python prototype using only the `adm` provider:
```
make load-test platform=python providers=adm
```

Results of tests runs from my local machine can be found within the README of each individual prototype:
* [Python Performance](https://github.com/quiiver/merino-explorations/tree/main/pyrino#performance)
* [Java Performance](https://github.com/quiiver/merino-explorations/tree/main/jarino#performance)
