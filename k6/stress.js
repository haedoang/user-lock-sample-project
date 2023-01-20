import http from 'k6/http';
import { check } from 'k6';

const HOST = {
    PROTOCOL : "http",
    URL : "localhost",
    PORT : "8080"
}

// stress
export let options = {
    vus: 100,
    duration: '5s',
    thresholds: {
        http_req_duration: ['p(99)< 50'],
    },
};

const API = `${HOST.PROTOCOL}://${HOST.URL}:${HOST.PORT}/api/v1/events`;
const data = {
    'nickname' : '랄랄라4'
}

export default function () {
    console.log(data);
    let response = http.post(API, JSON.stringify(data), { headers : { 'Content-Type' : 'application/json'}});

    check(response, {
        'response successfully': (response) => response.status === 201,
        'debounce' : (response) => response.status === 400,
        'forbidden' : (response) => response.status === 403
    });
};