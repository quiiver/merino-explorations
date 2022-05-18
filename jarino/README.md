# Jarino

Merino but in Java

## Deploying
### running locally

```
mvn package && java -jar target/jarino-1.0.0.jar
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
k6 run --vus 200 --duration 10m -e HOST=java -e PROVIDERS=adm ./load-tests/test.js

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


running (10m00.0s), 000/200 VUs, 3090677 complete and 0 interrupted iterations
default ✓ [======================================] 200 VUs  10m0s

     data_received..................: 2.3 GB  3.8 MB/s
     data_sent......................: 370 MB  617 kB/s
     http_req_blocked...............: avg=9.36µs  min=0s      med=1µs     max=351.15ms p(90)=2µs     p(95)=2µs
     http_req_connecting............: avg=7.85µs  min=0s      med=0s      max=351.14ms p(90)=0s      p(95)=0s
     http_req_duration..............: avg=38.73ms min=29.48ms med=36.57ms max=3.99s    p(90)=46.39ms p(95)=50.31ms
       { expected_response:true }...: avg=38.73ms min=29.48ms med=36.57ms max=3.99s    p(90)=46.39ms p(95)=50.31ms
     http_req_failed................: 0.00%   ✓ 0          ✗ 3090677
     http_req_receiving.............: avg=89.32µs min=4µs     med=16µs    max=1.76s    p(90)=30µs    p(95)=81µs
     http_req_sending...............: avg=8.46µs  min=1µs     med=5µs     max=41.52ms  p(90)=9µs     p(95)=11µs
     http_req_tls_handshaking.......: avg=0s      min=0s      med=0s      max=0s       p(90)=0s      p(95)=0s
     http_req_waiting...............: avg=38.63ms min=29.45ms med=36.48ms max=3.99s    p(90)=46.29ms p(95)=50.22ms
     http_reqs......................: 3090677 5150.87279/s
     iteration_duration.............: avg=38.81ms min=29.52ms med=36.64ms max=4.34s    p(90)=46.46ms p(95)=50.39ms
     iterations.....................: 3090677 5150.87279/s
     vus............................: 188     min=188      max=200
     vus_max........................: 200     min=200      max=200

➜  merino-explorations git:(main) ✗

```

## AdM and Elasticsearch

```
k6 run --vus 200 --duration 10m -e HOST=java -e PROVIDERS=adm,wiki ./load-tests/test.js

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


running (10m00.2s), 000/200 VUs, 720632 complete and 0 interrupted iterations
default ✓ [======================================] 200 VUs  10m0s

     data_received..................: 689 MB 1.1 MB/s
     data_sent......................: 76 MB  127 kB/s
     http_req_blocked...............: avg=23.76µs  min=0s      med=1µs      max=186.73ms p(90)=3µs      p(95)=4µs
     http_req_connecting............: avg=21.68µs  min=0s      med=0s       max=186.7ms  p(90)=0s       p(95)=0s
     http_req_duration..............: avg=166.4ms  min=34.89ms med=167.2ms  max=2.54s    p(90)=221.96ms p(95)=249.73ms
       { expected_response:true }...: avg=166.4ms  min=34.89ms med=167.2ms  max=2.54s    p(90)=221.96ms p(95)=249.73ms
     http_req_failed................: 0.00%  ✓ 0           ✗ 720632
     http_req_receiving.............: avg=179.99µs min=5µs     med=25µs     max=1.48s    p(90)=67µs     p(95)=186µs
     http_req_sending...............: avg=11.72µs  min=1µs     med=8µs      max=16.67ms  p(90)=15µs     p(95)=19µs
     http_req_tls_handshaking.......: avg=0s       min=0s      med=0s       max=0s       p(90)=0s       p(95)=0s
     http_req_waiting...............: avg=166.21ms min=34.8ms  med=167.04ms max=2.54s    p(90)=221.8ms  p(95)=249.52ms
     http_reqs......................: 720632 1200.748723/s
     iteration_duration.............: avg=166.52ms min=34.99ms med=167.31ms max=2.73s    p(90)=222.07ms p(95)=249.84ms
     iterations.....................: 720632 1200.748723/s
     vus............................: 200    min=200       max=200
     vus_max........................: 200    min=200       max=200
```
