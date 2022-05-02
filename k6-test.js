import http from "k6/http";
import { SharedArray } from 'k6/data';

// This will export to HTML as filename "result.html" AND also stdout using the text summary
import { htmlReport } from "https://raw.githubusercontent.com/benc-uk/k6-reporter/main/dist/bundle.js";
import { textSummary } from "https://jslib.k6.io/k6-summary/0.0.1/index.js";

export function handleSummary(data) {
  return {
    "result.html": htmlReport(data),
    stdout: textSummary(data, { indent: " ", enableColors: true }),
  };
}

const data = new SharedArray('query names', function () {
  // All heavy work (opening and processing big files for example) should be done inside here.
  // This way it will happen only once and the result will be shared between all VUs, saving time and memory.
  const f = JSON.parse(open('./data/InstantSuggest_Queries_20220125.json'));
  return Object.keys(f.mapping); // f must be an array
});

// const REMOTE_URI = "https://pyrino-4vjg42nbaq-uc.a.run.app/search";
// const REMOTE_URI = "http://34.69.18.217/search";
const REMOTE_URI = "http://34.123.70.140/search";
const LOCAL_URI = "http://127.0.0.1:8080/search";
const QUERY = 'amaz'

export default function() {
  const uri = __ENV.REMOTE ? REMOTE_URI : LOCAL_URI;
  const query = data[Math.floor(Math.random() * data.length)];
  // const query = QUERY;
  let response = http.get(`${uri}?q=${encodeURIComponent(query)}`);
};
