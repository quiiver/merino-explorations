# Pyrino

Merino prototype built in python

## Deploying
### running locally

```
./.venv/bin/uvicorn app.server:app --reload
```

### GKE Deploy
Deploy to `wstuckey-sandbox` gke cluster
```
make deploy
```

# Performance

We currently support two query providers (ad marketplace and wikipedia via elasticsearch). For load tests we locally run 200 concurrent requests for 10 minutes using the tool [k6](http://k6.io). Below are the results from 2 load tests, the first one only uses the AdM query provider and the second uses AdM and Elasticsearch.

## AdM only

```
k6 run --vus 200 --duration 10m -e HOST=python -e PROVIDERS=adm ./load-tests/test.js

          /\      |‾‾| /‾‾/   /‾‾/
     /\  /  \     |  |/  /   /  /
    /  \/    \    |     (   /   ‾‾\
   /          \   |  |\  \ |  (‾)  |
  / __________ \  |__| \__\ \_____/ .io

  execution: local
     script: ./load-tests/test.js
     output: -

  scenarios: (100.00%) 1 scenario, 200 max VUs, 10m30s max duration (incl. graceful stop):
           * default: 200 looping VUs for 10m0s (gracefulStop: 30s)


running (10m00.0s), 000/200 VUs, 3071333 complete and 0 interrupted iterations
default ✓ [======================================] 200 VUs  10m0s

     data_received..................: 2.3 GB  3.9 MB/s
     data_sent......................: 365 MB  608 kB/s
     http_req_blocked...............: avg=7.78µs  min=0s      med=1µs     max=167.68ms p(90)=2µs     p(95)=2µs
     http_req_connecting............: avg=6.24µs  min=0s      med=0s      max=167.66ms p(90)=0s      p(95)=0s
     http_req_duration..............: avg=38.98ms min=29.53ms med=36.52ms max=1.66s    p(90)=47.03ms p(95)=51.28ms
       { expected_response:true }...: avg=38.98ms min=29.53ms med=36.52ms max=1.66s    p(90)=47.03ms p(95)=51.28ms
     http_req_failed................: 0.00%   ✓ 0           ✗ 3071333
     http_req_receiving.............: avg=28.11µs min=4µs     med=15µs    max=40.37ms  p(90)=27µs    p(95)=40µs
     http_req_sending...............: avg=8.58µs  min=1µs     med=5µs     max=27.48ms  p(90)=9µs     p(95)=11µs
     http_req_tls_handshaking.......: avg=0s      min=0s      med=0s      max=0s       p(90)=0s      p(95)=0s
     http_req_waiting...............: avg=38.94ms min=29.51ms med=36.49ms max=1.66s    p(90)=46.99ms p(95)=51.24ms
     http_reqs......................: 3071333 5118.480203/s
     iteration_duration.............: avg=39.06ms min=29.59ms med=36.6ms  max=1.83s    p(90)=47.1ms  p(95)=51.35ms
     iterations.....................: 3071333 5118.480203/s
     vus............................: 190     min=190       max=200
     vus_max........................: 200     min=200       max=200
```

## AdM and Elasticsearch

```
k6 run --vus 200 --duration 10m -e HOST=python -e PROVIDERS=adm,wiki ./load-tests/test.js

          /\      |‾‾| /‾‾/   /‾‾/
     /\  /  \     |  |/  /   /  /
    /  \/    \    |     (   /   ‾‾\
   /          \   |  |\  \ |  (‾)  |
  / __________ \  |__| \__\ \_____/ .io

  execution: local
     script: ./load-tests/test.js
     output: -

  scenarios: (100.00%) 1 scenario, 200 max VUs, 10m30s max duration (incl. graceful stop):
           * default: 200 looping VUs for 10m0s (gracefulStop: 30s)


running (10m00.2s), 000/200 VUs, 758448 complete and 0 interrupted iterations
default ✓ [======================================] 200 VUs  10m0s

     data_received..................: 788 MB 1.3 MB/s
     data_sent......................: 80 MB  133 kB/s
     http_req_blocked...............: avg=23.82µs  min=0s      med=1µs      max=237.57ms p(90)=3µs      p(95)=4µs
     http_req_connecting............: avg=21.21µs  min=0s      med=0s       max=232.17ms p(90)=0s       p(95)=0s
     http_req_duration..............: avg=158.06ms min=34.12ms med=153.07ms max=2.44s    p(90)=225.83ms p(95)=253.1ms
       { expected_response:true }...: avg=158.06ms min=34.12ms med=153.07ms max=2.44s    p(90)=225.83ms p(95)=253.1ms
     http_req_failed................: 0.00%  ✓ 0           ✗ 758448
     http_req_receiving.............: avg=67.7µs   min=5µs     med=23µs     max=1.48s    p(90)=59µs     p(95)=91µs
     http_req_sending...............: avg=14.16µs  min=1µs     med=7µs      max=30.27ms  p(90)=15µs     p(95)=20µs
     http_req_tls_handshaking.......: avg=0s       min=0s      med=0s       max=0s       p(90)=0s       p(95)=0s
     http_req_waiting...............: avg=157.98ms min=34.08ms med=152.99ms max=2.44s    p(90)=225.73ms p(95)=253.01ms
     http_reqs......................: 758448 1263.711104/s
     iteration_duration.............: avg=158.21ms min=34.35ms med=153.21ms max=2.65s    p(90)=225.98ms p(95)=253.25ms
     iterations.....................: 758448 1263.711104/s
     vus............................: 200    min=200       max=200
     vus_max........................: 200    min=200       max=200
```
