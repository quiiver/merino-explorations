import http from "k6/http";
import { SharedArray } from 'k6/data';

const data = new SharedArray('query names', function () {
  return JSON.parse(open('./fixtures.json'));
});

const hosts = {
  "java": "http://34.123.70.140",
  "python": "http://34.67.8.144",
  "rust": "http://104.197.80.2",
  "local": "http://127.0.0.1:8000"
}

const providers = __ENV.PROVIDERS || ""
const path = "api/v1/suggest"

export default function() {
  const host = hosts[__ENV.HOST] || hosts.local
  const query = data[Math.floor(Math.random() * data.length)];
  const uri = `${host}/${path}?providers=${providers}&q=${encodeURIComponent(query)}`; 
  http.get(uri);
};
