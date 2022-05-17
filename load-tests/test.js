import http from "k6/http";
import { SharedArray } from 'k6/data';

const data = new SharedArray('query names', function () {
  return JSON.parse(open('./fixtures.json'));
});

const hosts = {
  "java": "34.123.70.140",
  "python": "34.69.18.217",
  "local": "127.0.0.1:8080"
}

const providers = __ENV.PROVIDERS || ""

export default function() {
  const host = hosts[__ENV.HOST] || hosts.local
  const query = data[Math.floor(Math.random() * data.length)];
  const uri = `http://${host}/search?providers=${providers}&q=${encodeURIComponent(query)}`; 
  http.get(uri);
};
